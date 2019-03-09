package cn.ken.egou.service;

import cn.ken.egou.domain.ProductExt;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品扩展 服务类
 * </p>
 *
 * @author kentest
 * @since 2019-03-04
 */
public interface IProductExtService extends IService<ProductExt> {
    public void saveByProductTypeId (Long productId, List displayProperties);

    void saveAllSKUByProductId(String productId,List<Map<String,Object>> selectAllSKUByProductTypeId,List<Map<String,Object>> skuDatas);
}
