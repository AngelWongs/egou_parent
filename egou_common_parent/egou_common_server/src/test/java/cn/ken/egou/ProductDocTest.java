package cn.ken.egou;

import cn.ken.egou.doc.ProductDoc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonServiceApplication.class)
public class ProductDocTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void testCreatEs(){
        //创建索引  做映射
        elasticsearchTemplate.createIndex(ProductDoc.class);
        //做映射
        elasticsearchTemplate.putMapping(ProductDoc.class);

    }

}