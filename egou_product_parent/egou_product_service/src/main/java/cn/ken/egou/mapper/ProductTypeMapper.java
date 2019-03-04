package cn.ken.egou.mapper;

import cn.ken.egou.domain.Brand;
import cn.ken.egou.domain.ProductType;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品目录 Mapper 接口
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
public interface ProductTypeMapper extends BaseMapper<ProductType> {
    /**
     *根据pid查出父亲
     * @param pid
     */
    ProductType getProductTypeByPid(Long pid);
}
