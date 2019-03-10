package cn.ken.egou.controller;

import cn.ken.egou.query.ProductExtQuery;
import cn.ken.egou.service.IProductExtService;
import cn.ken.egou.domain.ProductExt;
import cn.ken.egou.utils.AjaxResult;
import cn.ken.egou.utils.PageList;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productExt")
public class ProductExtController {
    @Autowired
    public IProductExtService productExtService;

    /**
    * 保存和修改公用的
    * @param productExt  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ProductExt productExt){
        try {
            if(productExt.getId()!=null){
                productExtService.updateById(productExt);
            }else{
                productExtService.insert(productExt);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            productExtService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ProductExt get(@PathVariable("id")Long id)
    {
        return productExtService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ProductExt> list(){

        return productExtService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ProductExt> json(@RequestBody ProductExtQuery query)
    {
        Page<ProductExt> page = new Page<ProductExt>(query.getPage(),query.getRows());
            page = productExtService.selectPage(page);
            return new PageList<ProductExt>(page.getTotal(),page.getRecords());
    }

    /**
     * 根据productid添加/修改显示属性
     * @param
     */
    @RequestMapping(value = "/saveSpecificationByProductTypeId",method = RequestMethod.POST)
    public AjaxResult saveByProductTypeId(@RequestBody Map map){
        //获取productId
        String productId = map.get("productId").toString();
        //获取去显示属性信息(list)
        List displayProperties = (List) map.get("specificationByProductTypeId");
        if (productId!=null && !productId.isEmpty() && displayProperties!=null){
            try {
                productExtService.saveByProductTypeId(new Long(productId),displayProperties);
                return AjaxResult.me().setSuccess(true).setMsg("操作OK");
            }catch (Exception e){

            }
        }
        return AjaxResult.me().setSuccess(false).setMsg("操作false");
    }
    //保存sku属性到到数据库  sku表/product_ext
    /**
    productId:61
    selectAllSKUByProductTypeId:
     [
         {id=38, specName=颜色, productTypeId=197, type=true, value=null, skuValue=[红, 蓝]},
         {id=39, specName=内存, productTypeId=197, type=true, value=null, skuValue=[4g, 6g]},
         {id=40, specName=版本, productTypeId=197, type=true, value=null, skuValue=[1]}
     ]
    skuDatas:
     [
         {颜色=红, 内存=4g, 版本=1, price=4, availableStock=1},
         {颜色=红, 内存=6g, 版本=1, price=6, availableStock=1},
         {颜色=蓝, 内存=4g, 版本=1, price=4, availableStock=1},
         {颜色=蓝, 内存=6g, 版本=1, price=6, availableStock=1}
     ]

     */
    @RequestMapping(value = "/saveAllSKUSpecificationByProductTypeId",method = RequestMethod.POST)
    public void saveAllSKUSpecificationByProductTypeId(@RequestBody Map map){
        //获取productId
        String productId = map.get("productId").toString();
        System.out.println("productId:"+ productId);
        //获取去skudata属性信息(list)
        List<Map<String,Object>> selectAllSKUByProductTypeId = (List<Map<String,Object>>) map.get("selectAllSKUByProductTypeId");
//        String selectAllSKUByProductTypeIdJson = JSONObject.toJSONString(selectAllSKUByProductTypeId);
        System.out.println("selectAllSKUByProductTypeId:"+selectAllSKUByProductTypeId);
        List<Map<String,Object>> skuDatas = (List<Map<String, Object>>) map.get("skuDatas");
//        String skuDatasJson = JSONObject.toJSONString(skuDatas);
        System.out.println("skuDatas:"+skuDatas);
        productExtService.saveAllSKUByProductId(productId,selectAllSKUByProductTypeId,skuDatas);
    }


}
