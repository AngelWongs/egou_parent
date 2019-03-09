package cn.ken.egou.controller;

import cn.ken.egou.query.ProductQuery;
import cn.ken.egou.service.IProductService;
import cn.ken.egou.domain.Product;
import cn.ken.egou.utils.AjaxResult;
import cn.ken.egou.utils.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;

    /**
    * 保存和修改公用的
    * @param product  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product, MultipartFile file){
        try {
            if(product.getId()!=null){
                productService.updateById(product);
            }else{
                productService.insert(product);
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
            productService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id")Long id)
    {
        return productService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Product> list(){

        return productService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query)
    {
//        Page<Product> page = new Page<Product>(query.getPage(),query.getRows());
//            page = productService.selectPage(page);
//            return new PageList<Product>(page.getTotal(),page.getRecords());
        return productService.selectPageList(query);
    }

    /**
     * 上下架的操作
     *
     * @param map ids:操作的id   1,2,3    ;  optType:1 上架请求   2下架请求
     * @return
     */
    @RequestMapping(value = "/productSale", method = RequestMethod.POST)
    public AjaxResult productSale(@RequestBody Map<String, Object> map) {
        Object ids1 = map.get("ids");
        Object optType = map.get("optType");
        if (ids1 != null && optType != null) {
            try {
                String ids = ids1.toString();
                Long opt = Long.valueOf(optType.toString());
                if (opt == 1) {
                    productService.onSale(ids,opt);
                } else if (opt == 2) {
                    productService.offSale(ids,opt);
                }

                return AjaxResult.me().setSuccess(true).setMsg("上下架成功");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return AjaxResult.me().setSuccess(false).setMsg("上下架失败");
            }

        } else {
            return AjaxResult.me().setSuccess(false).setMsg("请传入正确请求参数");
        }

    }
}
