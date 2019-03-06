package cn.ken.egou.service.impl;

import cn.ken.egou.domain.ProductExt;
import cn.ken.egou.domain.Specification;
import cn.ken.egou.mapper.SpecificationMapper;
import cn.ken.egou.service.IProductExtService;
import cn.ken.egou.service.ISpecificationService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author kentest
 * @since 2019-03-05
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements ISpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private IProductExtService productExtService;
    @Override
    public List<Specification> selectAllByProducttypeId(Long product_type_id,Long productId) {
        //数据库中没有显示属性值,有就是编辑,需要返回显示值信息
        //没有显示信息值不需要回显
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("productId", productId);
        ProductExt productExt = productExtService.selectOne(wrapper);
        if (productExt!=null && productExt.getViewProperties()!=null && !productExt.getViewProperties().isEmpty()){
            String viewProperties = productExt.getViewProperties();
            return JSONObject.parseArray(viewProperties, Specification.class);
        }
        return specificationMapper.selectAllByProducttypeId(product_type_id);
    }
}
