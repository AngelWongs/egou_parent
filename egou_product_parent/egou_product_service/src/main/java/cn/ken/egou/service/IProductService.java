package cn.ken.egou.service;

import cn.ken.egou.domain.Product;
import cn.ken.egou.query.ProductQuery;
import cn.ken.egou.utils.PageList;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
public interface IProductService extends IService<Product> {
    public PageList<Product> selectPageList(ProductQuery query);
}
