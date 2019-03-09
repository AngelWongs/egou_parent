package cn.ken.egou.controller;

import cn.ken.egou.query.SpecificationQuery;
import cn.ken.egou.service.ISpecificationService;
import cn.ken.egou.domain.Specification;
import cn.ken.egou.utils.AjaxResult;
import cn.ken.egou.utils.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    private ISpecificationService specificationService;

    /**
    * 保存和修改公用的
    * @param specification  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Specification specification){
        try {
            if(specification.getId()!=null){
                specificationService.updateById(specification);
            }else{
                specificationService.insert(specification);
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
            specificationService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMsg("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Specification get(@PathVariable("id")Long id)
    {
        return specificationService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Specification> list(){

        return specificationService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Specification> json(@RequestBody SpecificationQuery query)
    {
        Page<Specification> page = new Page<Specification>(query.getPage(),query.getRows());
            page = specificationService.selectPage(page);
            return new PageList<Specification>(page.getTotal(),page.getRecords());
    }

    /**
     * 查询当前产品类型的所有显示属性
     * @param productTypeId
     * @return
     */
    @RequestMapping(value = "/selectAllSpecificationByProductTypeId/{product_type_id}/{productId}",method = RequestMethod.GET)
    public List<Specification> selectAllByProducttypeId(@PathVariable("product_type_id") Long productTypeId,
                                                        @PathVariable("productId") Long productId){
        //1为显示属性
        List<Specification> specificationList = specificationService.selectAllByProducttypeId(productTypeId,productId,1L);
        return specificationList;
    }


    @RequestMapping(value = "/selectAllSKUSpecificationByProductTypeId/{product_type_id}",method = RequestMethod.GET)
    public List<Specification> selectAllSKUByProductTypeId(@PathVariable("product_type_id") Long productTypeId){
        //2为sku属性
        List<Specification> specificationList = specificationService.selectAllSKUByProductTypeId(productTypeId,2L);
        return specificationList;
    }

}
