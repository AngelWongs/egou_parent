package cn.ken.egou.service;

import cn.ken.egou.doc.ProductDoc;
import cn.ken.egou.domain.Product;
import cn.ken.egou.query.ProductQuery;
import cn.ken.egou.utils.PageList;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

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
    /**
     * 上架
     * @param ids  1,2,3
     * @param opt 1
     */
    void onSale(String ids, Long opt);

    /**
     * 下架
     * @param ids  1,2,3
     * @param opt 2
     */
    void offSale(String ids, Long opt);

    PageList<ProductDoc> queryProductFromEs(Map<String, Object> parmas);
}
