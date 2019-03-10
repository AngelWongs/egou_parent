package cn.ken.egou.controller;

import cn.ken.egou.client.ProductEsClient;
import cn.ken.egou.doc.ProductDoc;
import cn.ken.egou.service.IProductEsService;
import cn.ken.egou.utils.AjaxResult;
import cn.ken.egou.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//可以不实现接口:实现的目的:只是起一个约束的问题
@RestController
@RequestMapping("/common/es")
public class ProductEsController implements ProductEsClient {

    @Autowired
    private IProductEsService productEsService;


    @RequestMapping(value = "/productdoc", method = RequestMethod.POST)
    @Override
    public AjaxResult addOne(@RequestBody ProductDoc productDoc) {
        try {
            productEsService.addOne(productDoc);
            return AjaxResult.me().setMsg("上架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("上架失败");
        }
    }

    @RequestMapping(value = "/productdocs", method = RequestMethod.POST)
    @Override
    public AjaxResult addBatch(@RequestBody List<ProductDoc> productDocList) {
        try {
            productEsService.addBatch(productDocList);
            return AjaxResult.me().setMsg("上架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("上架失败");
        }
    }


    @RequestMapping(value = "/productdoc/{id}", method = RequestMethod.DELETE)
    @Override
    public AjaxResult deleteOne(@PathVariable("id") Long id) {
        try {
            productEsService.deleteOne(id);
            return AjaxResult.me().setMsg("下架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("下架失败");
        }
    }

    @RequestMapping(value = "/productdocs", method = RequestMethod.DELETE)
    @Override
    public AjaxResult deleteBatch(@RequestBody List<Long> ids) {
        try {
            productEsService.deleteBatch(ids);
            return AjaxResult.me().setMsg("下架成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("下架失败");
        }
    }

    @RequestMapping(value = "/productdoc/{id}", method = RequestMethod.GET)
    @Override
    public AjaxResult findOne(@PathVariable("id") Long id) {
        try {
            ProductDoc one = productEsService.findOne(id);
            return AjaxResult.me().setObject(one);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setObject(null).setMsg("查询失败");
        }
    }
    //高级查询  /common/es/queryProducts  /common/es/queryProducts
    @RequestMapping(value = "/queryProducts", method = RequestMethod.POST)
    @Override
    public PageList<ProductDoc> queryProducts(@RequestBody Map<String, Object> params) {
        //
        return productEsService.queryProducts(params);
    }
}

