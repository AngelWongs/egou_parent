package cn.ken.egou.controller;

import cn.ken.egou.query.BrandQuery;
import cn.ken.egou.service.IBrandService;
import cn.ken.egou.domain.Brand;
import cn.ken.egou.utils.AjaxResult;
import cn.ken.egou.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    public IBrandService brandService;

    /**
     * 保存和修改公用的
     *
     * @param brand 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Brand brand) {
        try {
            /**
             * 修改
             */
            if (brand.getId() != null) {
                brandService.updateById(brand);
            } else {
                /**
                 * 新增
                 */
                brandService.insert(brand);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            brandService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Brand get(@PathVariable("id") Long id) {
        return brandService.selectById(id);
    }


    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Brand> list() {

        return brandService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Brand> json(@RequestBody BrandQuery query) {
        /**需要一个rows总数据条数这里写死为10 数据也需要多表查询*/
//        Page<Brand> page = new Page<Brand>(query.getPage(), /*query.getRows()*/10);
//        page = brandService.selectPage(page);
//        brandService.selectBrandPageList(query);
//        return new PageList<Brand>(page.getTotal(), page.getRecords());
        return brandService.selectBrandPageList(query);
    }

    @RequestMapping(value = "/getProductTypeAllPid",method = RequestMethod.POST)
    public AjaxResult getProductTypeAllPid(@RequestBody Brand brand){
        //getProductTypeAllPid()方法
//        List<Long> productTypeAllPid = null;
//        try {
//            productTypeAllPid = brandService.getProductTypeAllPid(brand);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return AjaxResult.me().setSuccess(false).setMsg("获取pid失败").setObject(productTypeAllPid);
//        }
//        System.out.println(productTypeAllPid);
//        return AjaxResult.me().setObject(productTypeAllPid);
        //getProductTypeAllPid()方法
        int[] productTypeAllPid = null;
        String string = null;
        try {
            productTypeAllPid = brandService.getProductTypeAllPid(brand);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("获取pid失败").setObject(productTypeAllPid);
        }
        System.out.println(Arrays.toString(productTypeAllPid));
        return AjaxResult.me().setObject(productTypeAllPid);
    }

    @RequestMapping(value = "/brands/{productTypeId}",method = RequestMethod.GET)
    public List<Brand> getBrandsByProducTypeId(@PathVariable("productTypeId") Long productTypeId){

        return brandService.getBrandsByProducTypeId(productTypeId);
    }
}
