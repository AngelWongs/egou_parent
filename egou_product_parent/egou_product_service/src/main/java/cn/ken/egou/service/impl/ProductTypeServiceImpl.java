package cn.ken.egou.service.impl;

import cn.ken.egou.client.PageStaticClient;
import cn.ken.egou.client.RedisClient;
import cn.ken.egou.constant.GlobalConstant;
import cn.ken.egou.domain.ProductType;
import cn.ken.egou.mapper.ProductTypeMapper;
import cn.ken.egou.service.IProductTypeService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private PageStaticClient pageStaticClient;
    /**
     * @return
     */
    @Override
    public List<ProductType> treeData() {
        // 要得到name和儿子

//        return treeDataRecursion(0L);
//        return treeDataLoop();
        //先去redis查询是否有数据
         String jsonArrStr = redisClient.get(GlobalConstant.EGOU_PRODUCT_PROVIDER);
         //判断在redis中能不能根据这个key拿到有用数据
         if (jsonArrStr==null || StringUtils.isEmpty(jsonArrStr)){
             //没有数据->取数据库查->存到redis->返回数据(数据库中获取treeData)
             List<ProductType> productTypeList = treeDataLoop();
             jsonArrStr = JSONObject.toJSONString(productTypeList);
             redisClient.set(GlobalConstant.EGOU_PRODUCT_PROVIDER, jsonArrStr);
             System.out.println("-----------db------------");
         }
        //有数据直接从redis返回数据
        System.out.println("-----------redis------------");
        List<ProductType> productTypeList = JSONObject.parseArray(jsonArrStr, ProductType.class);
        return productTypeList;
    }

    /**
     * 使用循环方式:
     *   我们期望发送一条sql,把所有的子子孙孙的结构搞出来,但是搞不出来的;
     *   但是我们可以发送一条sql:把所有的数据拿回来,存在内存中,我可以写代码组装他的结构(在内存中完成的).
     * @return
     */
    private List<ProductType> treeDataLoop() {
//        //1:获取所有的数据:
//        List<ProductType> allProductType = productTypeMapper.selectList(null);
//
//        //2:用于存在每一个对象和他的一个标识的 Long:id
//        Map<Long,ProductType> map=new HashMap<>();
//        //将所有数据以id为key,以自身对象为value谁知道map中
//        for (ProductType productType : allProductType) {
//            map.put(productType.getId(), productType);
//        }
//
//        //最终想要的结果:
//        List<ProductType> result = new ArrayList<>();
//        //3:遍历
//        for (ProductType productType : allProductType) {
//            //组装结构: productType:每一个对象:
//            Long pid = productType.getPid();
//            if(pid==0){
//                result.add(productType);
//            }else{
//                // 找自己的老子,把自己添加到老子的儿子中-->得到老子
//                ProductType parent=map.get(pid);// where id =pid
////               //我老子的儿子
////                List<ProductType> children = parent.getChildren();
////                //把我自己放入老子的儿子中
////                children.add(productType);-->老子去添加自己的儿子
//                parent.getChildren().add(productType);
//            }
//        }
//        return result;
        List<ProductType> productTypes = productTypeMapper.selectList(null);
        HashMap<Long, ProductType> productTypeHashMap = new HashMap<>();
        for (ProductType productType : productTypes) {
            productTypeHashMap.put(productType.getId(), productType);
        }
        List<ProductType> result = new ArrayList<>();
        for (ProductType productType : productTypes) {
            Long pid = productType.getPid();
            if (pid==0){
                result.add(productType);
            }else{
                ProductType parent = productTypeHashMap.get(pid);
                parent.getChildren().add(productType);
            }
        }
        return removeEmptyChildren(result);
    }
    private List<ProductType> removeEmptyChildren(List<ProductType> result){
        if (result.size()<=0 || result==null){
            return null;
        }
        for (ProductType productType : result) {
            removeEmptyChildren(productType.getChildren());
        }
        return result;
    }
    /**
     *
     * 查询无限极的树装数据:
     select * from t_product_type where pid= ?????

     先得到一级目录:
     得到0的儿子;
     遍历这个目录:
     分别的他的儿子:
     遍历这个儿子目录的儿子
     ....
     递归的遍历下去,只到没有儿子就返回.

     treeDataRecursion:就是获取儿子:谁的儿子?

     递归:性能很差的,每次都要发送sql,会发送多条sql:怎么优化??????
     ====>问题是发了很多条sql才导致性能差,我发一条把所有的数据都拿回就好了
     * @return 树结构
     */
    private List<ProductType> treeDataRecursion(Long pid) {
        //0   每次都要发送sql
        List<ProductType> children = getAllChildren(pid); //1 2
        //出口
        if (children == null || children.size()<1){
            return null;
        }
        for (ProductType productType : children) {
            //1 3 4 自己调用自己
            List<ProductType> children1 = treeDataRecursion(productType.getId());
            productType.setChildren(children1);
        }
        return children;
    }

    /**
     * 查询指定pid的儿子
     * @param pid
     * @return
     */
    private List<ProductType> getAllChildren(Long pid){
        Wrapper wrapper = new EntityWrapper<ProductType>();
        wrapper.eq("pid", pid);
        return productTypeMapper.selectList(wrapper);
    }

    /**
     * 插入数据时更新静态页面
     * @param entity
     * @return
     */
    @Override
    public boolean insert(ProductType entity) {
        //修改:本身数据的修改不会变;修改完后,重新生成模板:
        //1:数据修改:
        boolean b = super.insert(entity);

        //2:模板的生成:此时此时,这个是模板的消费者:消费模板的提供者:
        //这个是java后台内部的服务的消费:feign/ribbon(采纳feign)
        //feign:注入模板接口,调用

        //逻辑实现:
        //2.1:先生成改变数据的html页面:productType
        Map<String,Object> mapProductType=new HashMap<>();
        List<ProductType> productTypes = treeDataLoop();
        mapProductType.put(GlobalConstant.PAGE_MODE, productTypes);//这里页面需要的是所有的产品类型数据
        //哪一个模板
        mapProductType.put(GlobalConstant.PAGE_TEMPLATE, "G:\\devlop\\Java\\javacode\\javaEE\\egou_parent\\egou_common_parent\\egou_common_interface\\src\\main\\resources\\template\\product.type.vm");
        //根据模板生成的页面的地址:
        mapProductType.put(GlobalConstant.PAGE_TEMPLATE_HTML, "G:\\devlop\\Java\\javacode\\javaEE\\egou_parent\\egou_common_parent\\egou_common_interface\\src\\main\\resources\\template\\product.type.vm.html");

        pageStaticClient.getPageStatic(mapProductType);

//        //2.2:再生成home的html页面:
//        Map<String,Object> mapHome=new HashMap<>();
//        //数据:$model.staticRoot
//        Map<String,String> staticRootMap=new HashMap<>();
//        staticRootMap.put("staticRoot", "G:\\devlop\\Java\\javacode\\javaEE\\egou_parent\\egou_common_parent\\egou_common_interface\\src\\main\\resources\\");
//        mapHome.put(GlobalConstant.PAGE_MODE, staticRootMap);//这里页面需要的是目录的根路径
//        //哪一个模板
//        mapHome.put(GlobalConstant.PAGE_TEMPLATE, "G:\\devlop\\Java\\javacode\\javaEE\\egou_parent\\egou_common_parent\\egou_common_interface\\src\\main\\resources\\template\\home.vm");
//        //根据模板生成的页面的地址:
//        mapHome.put(GlobalConstant.PAGE_TEMPLATE_HTML, "G:\\devlop\\Java\\javacode\\javaEE\\egou_parent\\egou_common_parent\\egou_common_interface\\src\\main\\resources\\template\\home.html");
//
//        pageStaticClient.getPageStatic(mapHome);

        return b;
    }
}
