package cn.ken.egou.mapper;

import cn.ken.egou.domain.Product;
import cn.ken.egou.query.ProductQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 多表查询
     * @return
     */
    public List<Product> selectPageList(ProductQuery query);
    public Long selectPageListCount();

    //ids 和上架时间
    void updateOnSaleBatch(Map<String,Object> params);

    void updateOffSaleBatch(Map<String, Object> params);
}
