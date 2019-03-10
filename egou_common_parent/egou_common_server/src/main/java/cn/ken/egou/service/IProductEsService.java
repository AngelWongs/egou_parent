package cn.ken.egou.service;


import cn.ken.egou.doc.ProductDoc;
import cn.ken.egou.utils.PageList;

import java.util.List;
import java.util.Map;

public interface IProductEsService {

    //添加一个
    void addOne(ProductDoc productDoc);

    //批量添加
    void addBatch(List<ProductDoc> productDocList);

    //删除一个
    void deleteOne(Long id);

    //批量删除
    void deleteBatch(List<Long> ids);

    //查询一个
    ProductDoc findOne(Long id);

    PageList<ProductDoc> queryProducts(Map<String,Object> params);
}
