package cn.ken.egou.service;

import cn.ken.egou.domain.Brand;
import cn.ken.egou.query.BrandQuery;
import cn.ken.egou.utils.PageList;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
public interface IBrandService extends IService<Brand> {
    public PageList<Brand> selectBrandPageList(BrandQuery query);
    public List<Long> getProductTypeAllPid(Brand brand);

}
