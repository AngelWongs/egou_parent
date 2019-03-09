package cn.ken.egou.repository;


import cn.ken.egou.doc.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductDoc, Long> {
}
