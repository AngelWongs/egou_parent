package cn.ken.egou.client;

import cn.ken.egou.doc.ProductDoc;
import cn.ken.egou.utils.AjaxResult;
import cn.ken.egou.utils.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductEsFactory implements FallbackFactory<ProductEsClient> {
    @Override
    public ProductEsClient create(Throwable throwable) {
        return new ProductEsClient() {

            @Override
            public AjaxResult addOne(ProductDoc productDoc) {
                return null;
            }

            @Override
            public AjaxResult addBatch(List<ProductDoc> productDocList) {
                return null;
            }

            @Override
            public AjaxResult deleteOne(Long id) {
                return null;
            }

            @Override
            public AjaxResult deleteBatch(List<Long> ids) {
                return null;
            }

            @Override
            public AjaxResult findOne(Long id) {
                return null;
            }

            @Override
            public PageList<ProductDoc> queryProducts(Map<String, Object> params) {
                return null;
            }
        };
    }
}
