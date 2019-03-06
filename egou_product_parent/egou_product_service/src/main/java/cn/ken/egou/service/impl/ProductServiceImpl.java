package cn.ken.egou.service.impl;

import cn.ken.egou.domain.Product;
import cn.ken.egou.domain.ProductExt;
import cn.ken.egou.mapper.ProductExtMapper;
import cn.ken.egou.mapper.ProductMapper;
import cn.ken.egou.query.ProductQuery;
import cn.ken.egou.service.IProductExtService;
import cn.ken.egou.service.IProductService;
import cn.ken.egou.utils.PageList;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private IProductExtService productExtService;
    @Override
    public PageList<Product> selectPageList(ProductQuery query) {
        List<Product> productList = productMapper.selectPageList(query);
        Long aLong = productMapper.selectPageListCount();
        long productListSize = productList.size();
        if (productListSize > 0){
            //有数据返回
            PageList<Product> productPageList = new PageList<>(aLong.longValue(), productList);
            return productPageList;
        }
        return null;
    }

    @Override
    public boolean insert(Product entity) {
        //本身添加
        boolean insert = super.insert(entity);
        //添加商品扩展
        ProductExt productExt = entity.getProductExt();
        productExt.setProductId(entity.getId());
        productExtService.insert(productExt);
        return false;
    }

    @Override
    public boolean updateById(Product entity) {
        boolean b = super.updateById(entity);
        //添加商品扩展
        ProductExt productExt = entity.getProductExt();
        productExt.setProductId(entity.getId());
        boolean productExtB = false;
        if (productExt.getId()!=null){
            productExtB = productExtService.updateById(productExt);
        }
        return b&&productExtB;
    }
}
