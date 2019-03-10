package cn.ken.egou.service;

import cn.ken.egou.domain.ProductType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
public interface IProductTypeService extends IService<ProductType> {
    public List<ProductType> treeData();

    List<Map<String, Object>> getCrumbs(Long productTypeId);
}
