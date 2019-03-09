package cn.ken.egou.service.impl;

import cn.ken.egou.ProductServerApplication;
import cn.ken.egou.domain.Brand;
import cn.ken.egou.domain.ProductType;
import cn.ken.egou.mapper.BrandMapper;
import cn.ken.egou.query.BrandQuery;
import cn.ken.egou.service.IProductTypeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServerApplication.class)
public class BrandServiceImplTest {
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private IProductTypeService productTypes;
    @Test
    public void testSelectAllBrand(){
        BrandQuery brandQuery = new BrandQuery();
        List<Brand> list = brandMapper.selectAllBrand(brandQuery);
        for (Brand brand : list) {
            System.out.println(brand);
        }
    }
    @Test
    public void testAllProductType(){
        EntityWrapper<ProductType> productTypeEntityWrapper = new EntityWrapper<>();
        List<ProductType> productTypes = this.productTypes.selectList(null);
        for (ProductType type : productTypes) {
            System.out.println(type);
        }
    }
}