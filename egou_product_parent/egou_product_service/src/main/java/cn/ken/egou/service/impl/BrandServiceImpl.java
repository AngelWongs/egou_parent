package cn.ken.egou.service.impl;

import cn.ken.egou.domain.Brand;
import cn.ken.egou.mapper.BrandMapper;
import cn.ken.egou.mapper.ProductTypeMapper;
import cn.ken.egou.query.BrandQuery;
import cn.ken.egou.service.IBrandService;
import cn.ken.egou.utils.PageList;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public PageList<Brand> selectBrandPageList(BrandQuery query) {
        //柑橘条件查询所有数据-->分页在mapper.xml中
        List<Brand> brands = brandMapper.selectAllBrand(query);
        //根据数据查所有总数据条数
        Long aLong = brandMapper.selectAllBrandCount(query);
        long brandSize = brands.size();
        if (brandSize > 0) {
            //有数据返回
            PageList<Brand> brandPageList = new PageList<>(aLong.longValue(), brands);
            return brandPageList;
        }
        return null;
    }

    /**
     * 递归查出所有父亲
     *
     * @param brand
     */
    @Override
    public int[] getProductTypeAllPid(Brand brand) {
//        brandMapper.getProductTypeByPid(brand);
//        pidList.clear();
//        pidList.add(brand.getProductType().getId());
//        getProductTypeAllPidData(brand.getProductType());
//        Collections.reverse(pidList);
//        return null;
        //优化下拉树 使用 path 字段
        String s = productTypeMapper.productTypePidEcho(brand.getProductType().getId());
        String[] split = s.substring(1).split("\\.");
        int[] ints = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Integer.parseInt(split[i]);
        }
//        Arrays.
        return ints;
    }


//    private static List<Long> pidList = new ArrayList<>();

//    private String getProductTypeAllPidData(ProductType productType){
////和数据库交互,得到当前节点记录 -->递归(回显下拉树)--优化
//        ProductType productType1 = productTypeMapper.selectById(productType.getPid());
//        if(productType1 != null){
//            String configName = productType1.getName()+"->";
//            pidList.add(productType1.getId());
//            //参数patrolConfigEntity.getConfigParentId()表示当前节点的父节点ID
//            String returnConfigName = getProductTypeAllPidData(productType1);
//            return configName+returnConfigName;
//        }else{
//            return "";
//        }
//
//    }
//    public String getProductTypeAllPidData(ProductType productType){
////和数据库交互,得到当前节点记录
//        ProductType productType1 = productTypeMapper.selectById(productType.getPid());
//        if(productType1 != null){
//            String configName = productType1.getName()+"->";
//            pidList.add(productType1.getId());
//            //参数patrolConfigEntity.getConfigParentId()表示当前节点的父节点ID
//            String returnConfigName = getProductTypeAllPidData(productType1);
//            return configName+returnConfigName;
//        }else{
//            return "";
//        }
//    }

    /**
     * 跟据productTypeId拿到下面的所有品牌
     * @param productTypeId
     * @return
     */
    @Override
    public List<Brand> getBrandsByProducTypeId(Long productTypeId) {
        EntityWrapper<Brand> brandEntityWrapper = new EntityWrapper<>();
        return brandMapper.selectList(brandEntityWrapper.eq("product_type_id", productTypeId));
    }
}
