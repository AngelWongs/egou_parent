package cn.ken.egou.mapper;

import cn.ken.egou.domain.Specification;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品属性 Mapper 接口
 * </p>
 *
 * @author kentest
 * @since 2019-03-05
 */
public interface SpecificationMapper extends BaseMapper<Specification> {
    public List<Specification> selectAllByProducttypeId(Long product_type_id);
}
