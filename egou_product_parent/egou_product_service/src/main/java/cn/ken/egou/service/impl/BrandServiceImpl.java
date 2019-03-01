package cn.ken.egou.service.impl;

import cn.ken.egou.domain.Brand;
import cn.ken.egou.mapper.BrandMapper;
import cn.ken.egou.query.BrandQuery;
import cn.ken.egou.service.IBrandService;
import cn.ken.egou.utils.PageList;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageList<Brand> selectBrandPageList(BrandQuery query) {
        //柑橘条件查询所有数据-->分页在mapper.xml中
        List<Brand> brands = brandMapper.selectAllBrand(query);
        //根据数据查所有总数据条数
        Long aLong = brandMapper.selectAllBrandCount(query);
        long brandSize = brands.size();
        if (brandSize > 0){
            //有数据返回
            PageList<Brand> brandPageList = new PageList<>(aLong.longValue(), brands);
            return brandPageList;
        }
        return null;
    }

}
