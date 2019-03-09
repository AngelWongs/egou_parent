package cn.ken.egou;

import cn.ken.egou.domain.Brand;
import cn.ken.egou.domain.ProductType;
import cn.ken.egou.mapper.ProductTypeMapper;
import cn.ken.egou.service.impl.BrandServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;


/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServerApplication.class)
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Test
    public void test() {
        ProductType productTypeByPid = productTypeMapper.getProductTypeByPid(1L);
    }

    @Autowired
    private BrandServiceImpl brandService;

    @Test
    public void testGetProductTypeAllPid() {
        Brand brand = new Brand();
        ProductType productType = new ProductType();
        productType.setPid(3L);
        brand.setProductType(productType);
        int[] productTypeAllPid = brandService.getProductTypeAllPid(brand);
        System.out.println(Arrays.toString(productTypeAllPid));
//        ProductType productType = new ProductType();
//        productType.setPid(3L);
//        System.out.println(brandService.getProductTypeAllPidData(productType));
//        System.out.println(BrandServiceImpl.pidList);
    }

}
