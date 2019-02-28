package cn.ken.egou.service.impl;

import cn.ken.egou.domain.ProductType;
import cn.ken.egou.mapper.ProductTypeMapper;
import cn.ken.egou.service.IProductTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

}
