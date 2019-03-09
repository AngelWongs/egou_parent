package cn.ken.egou.service.impl;

import cn.ken.egou.client.ProductEsClient;
import cn.ken.egou.doc.ProductDoc;
import cn.ken.egou.domain.Product;
import cn.ken.egou.domain.ProductExt;
import cn.ken.egou.mapper.ProductExtMapper;
import cn.ken.egou.mapper.ProductMapper;
import cn.ken.egou.query.ProductQuery;
import cn.ken.egou.service.IProductExtService;
import cn.ken.egou.service.IProductService;
import cn.ken.egou.utils.PageList;
import cn.ken.egou.utils.StrUtils;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author kentest
 * @since 2019-02-27
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private IProductExtService productExtService;

    @Autowired
    private ProductEsClient productEsClient;

    /**
     * 分页
     * @param query
     * @return
     */
    @Override
    public PageList<Product> selectPageList(ProductQuery query) {
        List<Product> productList = productMapper.selectPageList(query);
        Long aLong = productMapper.selectPageListCount();
        long productListSize = productList.size();
        if (productListSize > 0){
            //有数据返回
            PageList<Product> productPageList = new PageList<>(aLong.longValue(), productList);
            return productPageList;
        }
        return null;
    }

    /**
     * 组合插入数据
     * @param entity
     * @return
     */
    @Override
    public boolean insert(Product entity) {
        //本身添加
        boolean insert = super.insert(entity);
        //添加商品扩展
        ProductExt productExt = entity.getProductExt();
        productExt.setProductId(entity.getId());
        productExtService.insert(productExt);
        return false;
    }

    /**
     * 组合扩展表更新数据
     * @param entity
     * @return
     */
    @Override
    public boolean updateById(Product entity) {
        boolean b = super.updateById(entity);
        //添加商品扩展
        ProductExt productExt = entity.getProductExt();
        productExt.setProductId(entity.getId());
        boolean productExtB = false;
        if (productExt.getId()!=null){
            productExtB = productExtService.updateById(productExt);
        }
        return b&&productExtB;
    }

    /**
     * 上下架
     * @param ids  1,2,3    前台勾选的列数据的id
     * @param opt  1    操作类型 1为上架 2为下架
     */
    @Override
    public void onSale(String ids, Long opt) {
        List<Long> idlist = StrUtils.splitStr2LongArr(ids);
        //1:根据id修改数据库的状态和上架时间 批量修改
        updateOnSaleBatch(idlist);
        //2:查询出数据库的数据  where id in (1,2,3)
        List<Product> productListDb =  productMapper.selectBatchIds(idlist);
        //3:把数据库的数据转换成:ProductDoc
        List<ProductDoc> list = getProductDocList(productListDb);
        //4:调用es的服务,上架
        productEsClient.addBatch(list);

    }

    /**
     * 批量更新商品状态为上架
     * @param idlist
     */
    private void updateOnSaleBatch(List<Long> idlist) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", idlist);
        Date date = new Date();
        params.put("onSaleTime", date.getTime());
        productMapper.updateOnSaleBatch(params);
    }

    /**
     * 批量更新商品状态为下架
     * @param idlist
     */
    private void updateOffSaleBatch(List<Long> idlist) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", idlist);
        Date date = new Date();
        params.put("offSaleTime", date.getTime());
        productMapper.updateOffSaleBatch(params);
    }

    /**
     * 更新到ES的数据
     * @param productListDb
     * @return
     */
    private List<ProductDoc> getProductDocList(List<Product> productListDb) {
        List<ProductDoc> list = new ArrayList<>();
        for (Product product : productListDb) {
            ProductDoc doc = productToProductDoc(product);
            list.add(doc);
        }

        return list;
    }

    /**
     * 把一个product对象装换成一个productDoc对象
     *
     * @param product
     * @return
     */
    private ProductDoc productToProductDoc(Product product) {
        ProductDoc doc = new ProductDoc();
        doc.setId(product.getId());
        doc.setAll(product.getName() + " " + product.getSubName());
        doc.setBrandId(product.getBrandId());
        doc.setCommentCount(product.getCommentCount());
        doc.setMaxPrice(product.getMaxPrice());
        if (!StringUtils.isEmpty(product.getMedias())) {
            // List<String>
            String[] strings = StrUtils.splitStr2StrArr(product.getMedias());
            doc.setMedias(Arrays.asList(strings));//
        }

        doc.setMinPrice(product.getMinPrice());
        doc.setOnSaleTime(product.getOnSaleTime());
        doc.setProductTypeId(product.getProductTypeId());
        doc.setSaleCount(product.getSaleCount());
        doc.setViewCount(product.getViewCount());
        //ext表:
//        ProductExt ext = new ProductExt();
//        ext.setProductId(product.getId());
        // 关联查询: select * from t_product p left join t_product_ext e on p.id = e.productId where  id in (1,2,3)
//        ProductExt productExt = productExtMapper.selectOne(ext);
        Wrapper<ProductExt> ext = new EntityWrapper<ProductExt>();
        ext.eq("productId", product.getId());
        ProductExt productExt = productExtService.selectOne(ext);
        if(!StringUtils.isEmpty(productExt.getSkuProperties())){
            // productExt.getSkuProperties()
            // [{"id":33,"specName":"颜色","type":2,"productTypeId":9,"skuValues":["yellow","blank"]},
            // {"id":34,"specName":"尺寸","type":2,"productTypeId":9,"skuValues":["18","8"]}]
            String skuProperties = productExt.getSkuProperties();
            List<Map> maps= JSONArray.parseArray(skuProperties, Map.class);
            doc.setSkuProperties(maps);
        }

        if(!StringUtils.isEmpty(productExt.getViewProperties())){
            String viewProperties = productExt.getViewProperties();
            List<Map> maps= JSONArray.parseArray(viewProperties, Map.class);
            doc.setViewProperties(maps);
        }


        return doc;
    }

    @Override
    public void offSale(String ids, Long opt) {
        //1:根据id修改数据库的状态和下架时间
        List<Long> idlist = StrUtils.splitStr2LongArr(ids);
        updateOffSaleBatch(idlist);
        //4:调用es的服务,传入各个id,删除
        productEsClient.deleteBatch(idlist);

    }
}
