package cn.ken.egou.service.impl;


import cn.ken.egou.doc.ProductDoc;
import cn.ken.egou.repository.ProductRepository;
import cn.ken.egou.service.IProductEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductEsServiceImpl implements IProductEsService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public void addOne(ProductDoc productDoc) {
        productRepository.save(productDoc);
    }

    @Override
    public void addBatch(List<ProductDoc> productDocList) {
        productRepository.saveAll(productDocList);
    }

    @Override
    public void deleteOne(Long id) {
        productRepository.deleteById(id);

    }

    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            productRepository.deleteById(id);
        }

    }

    @Override
    public ProductDoc findOne(Long id) {
        return productRepository.findById(id).get();
    }
}
