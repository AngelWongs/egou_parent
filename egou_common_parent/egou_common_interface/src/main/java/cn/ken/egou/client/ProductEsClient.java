package cn.ken.egou.client;

import cn.ken.egou.doc.ProductDoc;
import cn.ken.egou.utils.AjaxResult;
import cn.ken.egou.utils.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(value = "COMMON-PROVIDER",fallbackFactory = ProductEsFactory.class) //表示对哪一个服务进行处理
@RequestMapping("/common/es")
public interface ProductEsClient {
    //添加一个
    @RequestMapping(value = "/productdoc", method = RequestMethod.POST)
    AjaxResult addOne(@RequestBody ProductDoc productDoc);

    //批量添加
    @RequestMapping(value = "/productdocs", method = RequestMethod.POST)
    AjaxResult addBatch(@RequestBody List<ProductDoc> productDocList);

    //删除一个
    @RequestMapping(value = "/productdoc/{id}", method = RequestMethod.DELETE)
    AjaxResult deleteOne(@PathVariable("id") Long id );

    //批量删除
    @RequestMapping(value = "/productdocs", method = RequestMethod.DELETE)
    AjaxResult deleteBatch(@RequestBody List<Long> ids );

    //查询一个
    @RequestMapping(value = "/productdoc/{id}", method = RequestMethod.GET)
    AjaxResult findOne(@PathVariable("id") Long id  );

    //高级查询
    @RequestMapping(value = "/queryProducts", method = RequestMethod.POST)
    PageList<ProductDoc> queryProducts(@RequestBody Map<String,Object> params);

}
