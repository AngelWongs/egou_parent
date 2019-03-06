package cn.ken.egou.service.impl;

import cn.ken.egou.domain.ProductExt;
import cn.ken.egou.mapper.ProductExtMapper;
import cn.ken.egou.service.IProductExtService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品扩展 服务实现类
 * </p>
 *
 * @author kentest
 * @since 2019-03-04
 */
@Service
public class ProductExtServiceImpl extends ServiceImpl<ProductExtMapper, ProductExt> implements IProductExtService {
    @Autowired
    private ProductExtMapper peoProductExtMapper;
    @Override
    public void saveByProductTypeId(Long productId, List displayProperties) {
        //先去数据库中查看有没有这个商品的扩展属性

        //有修改

        //没有添加
        String displayPropertiesStr = JSONObject.toJSONString(displayProperties);
        //保存
        ProductExt productExt = new ProductExt();
        productExt.setProductId(productId);
        //设置显示属性
        productExt.setViewProperties(displayPropertiesStr);
        Wrapper waWrapper =  new EntityWrapper();
        waWrapper.eq("productId", productId);
        peoProductExtMapper.update(productExt,waWrapper);

    }
}
