package cn.ken.egou.service;

import cn.ken.egou.domain.Specification;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author kentest
 * @since 2019-03-05
 */
public interface ISpecificationService extends IService<Specification> {
    public List<Specification> selectAllByProducttypeId(Long product_type_id,Long productId,Long type);

    List<Specification> selectAllSKUByProductTypeId(Long productTypeId, Long type);
}
