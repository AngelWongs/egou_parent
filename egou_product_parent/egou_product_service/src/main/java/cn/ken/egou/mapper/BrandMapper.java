package cn.ken.egou.mapper;

import cn.ken.egou.domain.Brand;
import cn.ken.egou.query.BrandQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
public interface BrandMapper extends BaseMapper<Brand> {
    /**
     *  根据条件查询所有数据
     * @return
     */
    public List<Brand> selectAllBrand(BrandQuery query);

    public Long selectAllBrandCount(BrandQuery query);


}
