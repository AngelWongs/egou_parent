package cn.ken.egou.service.impl;

import cn.ken.egou.domain.ProductType;
import cn.ken.egou.mapper.ProductTypeMapper;
import cn.ken.egou.service.IProductTypeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

    /**
     * @return
     */
    @Override
    public List<ProductType> treeData() {
        // 要得到name和儿子

//        return treeDataRecursion(0L);
        return treeDataLoop();
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
     * @return
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

}
