package cn.ken.egou.service.impl;

import cn.ken.egou.domain.Product;
import cn.ken.egou.mapper.ProductMapper;
import cn.ken.egou.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
