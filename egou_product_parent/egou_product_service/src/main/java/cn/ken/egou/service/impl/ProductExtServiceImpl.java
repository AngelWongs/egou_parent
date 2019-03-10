package cn.ken.egou.service.impl;

import cn.ken.egou.domain.ProductExt;
import cn.ken.egou.domain.Sku;
import cn.ken.egou.mapper.ProductExtMapper;
import cn.ken.egou.mapper.SkuMapper;
import cn.ken.egou.service.IProductExtService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

/**
 * <p>
 * 商品扩展 服务实现类
 * </p>
 *
 * @author kentest
 * @since 2019-03-04
 */
@Service
public class ProductExtServiceImpl extends ServiceImpl<ProductExtMapper, ProductExt> implements IProductExtService {
    @Autowired
    private ProductExtMapper productExtMapper;
    @Autowired
    private SkuServiceImpl skuService;

    @Override
    public void saveByProductTypeId(Long productId, List displayProperties) {
        //先去数据库中查看有没有这个商品的扩展属性
        String displayPropertiesStr = JSONObject.toJSONString(displayProperties);
        //保存
        ProductExt productExt = new ProductExt();
        productExt.setProductId(productId);
        //设置显示属性
        productExt.setViewProperties(displayPropertiesStr);
        Wrapper waWrapper = new EntityWrapper();
        waWrapper.eq("productId", productId);
        productExtMapper.update(productExt, waWrapper);

    }

    /**
     * 保存suk信息到数据库
     *
     * @param productId                   根据product添加-->product_Ext表
     * @param selectAllSKUByProductTypeId sku组装数据
     * @param skuDatas                    sku数据
     * productId:61
     * selectAllSKUByProductTypeId:
     * [
     * {id=38, specName=颜色, productTypeId=197, type=true, value=null, skuValue=[红, 蓝]},
     * {id=39, specName=内存, productTypeId=197, type=true, value=null, skuValue=[4g, 6g]},
     * {id=40, specName=版本, productTypeId=197, type=true, value=null, skuValue=[1]}
     * ]
     * skuDatas:
     * [
     * {颜色=红, 内存=4g, 版本=1, price=4, availableStock=1},
     * {颜色=红, 内存=6g, 版本=1, price=6, availableStock=1},
     * {颜色=蓝, 内存=4g, 版本=1, price=4, availableStock=1},
     * {颜色=蓝, 内存=6g, 版本=1, price=6, availableStock=1}
     * ]
     */
    @Override
    public void saveAllSKUByProductId(String productId, List<Map<String, Object>> selectAllSKUByProductTypeId, List<Map<String, Object>> skuDatas) {
//        selectAllSKUByProductTypeId//直接保存到product_Ext的skuProperties中
        //更新值保存值到productExt的skuProperties
        ProductExt productExt = new ProductExt();
        productExt.setSkuProperties(JSONObject.toJSONString(selectAllSKUByProductTypeId));
        Wrapper<ProductExt> productExtWrapper = new EntityWrapper<>();
        productExtWrapper.eq("productId", productId);
        productExtMapper.update(productExt, productExtWrapper);
        for (Map<String, Object> skuData : skuDatas) {
            //保存到sku表-->new一个sku对象对其中字段赋值
            Sku sku = new Sku();
            sku.setProductId(Long.valueOf(productId));
            Date currentDate = new Date();
            sku.setCreateTime(currentDate.getTime());
            //skuData:{颜色=红, 内存=4g, 版本=1, price=4, availableStock=1}
            Set<Map.Entry<String, Object>> skuEntries = skuData.entrySet();
            //用于装sku表的skuProperties属性
            List<Map<String, Object>> otherList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : skuEntries) {
                HashMap<String, Object> otherHashMap = new HashMap<>();
                String key = entry.getKey();
                Object value = entry.getValue();
                //设置价格
                if ("price".equals(key)) {
                    sku.setPrice(Integer.valueOf(entry.getValue().toString()));
                } else if ("availableStock".equals(key)) {
                    //设置库存
                    sku.setAvailableStock(Integer.valueOf(value.toString()));
                } else {
                    //其他属性设置 后面遍历
                    otherHashMap.put(key, value);
                    otherList.add(otherHashMap);
                }
            }
            //============skuProperties设置开始============
            // otherList:[{"颜色":"yellow"},{"尺寸":"26"}]
            //最终的skuProperties的值: [{"id":33,"key":"颜色","value":"yellow"}]
            List<Map<String, Object>> skuList = new ArrayList<>();
            System.out.println("otherList===" + otherList);
            //遍历其他属性
            for (Map<String, Object> om : otherList) {
                // {"颜色":"yellow"}
                Map<String, Object> mm = new HashMap<>();
                Set<Map.Entry<String, Object>> otherEntries = om.entrySet();
                String properKey = "";
                for (Map.Entry<String, Object> entry : otherEntries) {
                    properKey = entry.getKey();
                    mm.put("key", properKey);
                    mm.put("value", entry.getValue());
                }
                Long propertyId = getPropId(properKey, selectAllSKUByProductTypeId);
                mm.put("id", propertyId);
                /*
                    skuList组装出来:
                    [{"id":38,"value":"蓝","key":"颜色"},{"id":39,"value":"4G","key":"内存"},{"id":40,"value":"1","key":"版本"}]
                */
                skuList.add(mm);
            }
            //设置SkuProperties:
            sku.setSkuProperties(JSONArray.toJSONString(skuList));
            //============skuProperties设置结束============

            //============skuIndex设置开始============组装唯一标识
            //
            StringBuffer sb = new StringBuffer();
            for (Map<String, Object> om : skuList) {
                // om:{"id":38,"value":"蓝","key":"颜色"}
                //获取属性id-->颜色的id=38
                Object proId = om.get("id");
                //获取 value -->蓝色
                Object value = om.get("value");
                Integer index = getIndex(proId, value, selectAllSKUByProductTypeId);
                System.out.println("idnex==" + index);
                sb.append(index).append("_");
            }
            // sb 1_2_4_
            //去掉最后一个_
            String sbStr = sb.toString();
            sbStr = sbStr.substring(0, sb.lastIndexOf("_"));
            sku.setSkuIndex(sbStr);
            System.out.println("============skuIndex设置结束============");

            //sku的保存:在前面都是在构造这个sku的各个字段值
            skuService.insert(sku);
        }

    }


    /**
     * {"id":38,"value":"蓝","key":"颜色"}
     * @param proId         属性的id   38
     * @param value         属性的value-->当前sku的颜色的值-->判定索引    蓝色
     * @param skuProperties list
     * @return
     */
    private Integer getIndex(Object proId, Object value, List<Map<String, Object>> skuProperties) {
        for (Map<String, Object> skuProperty : skuProperties) {
            //
            //[{"id":38,"specName":"颜色","productTypeId":197,"type":true,"skuValue":["蓝","红"]},
            // {"id":39,"specName":"内存","productTypeId":197,"type":true,"skuValue":["4G","6G"]},
            // {"id":40,"specName":"版本","productTypeId":197,"type":true,"skuValue":["1"]}]

            //循环获取skuProperties(上面)--的值{"id":38,"specName":"颜色","productTypeId":197,"type":true,"skuValue":["蓝","红"]}
            Long id = Long.valueOf(skuProperty.get("id").toString());
            //转换proId为Long
            Long pro = Long.valueOf(proId.toString());
            //是颜色属性-->下次循环还有内存/版本
            if (id.longValue() == pro.longValue()) {
                List<String> skuValues = (List<String>) skuProperty.get("skuValue");
                int index = 0;
                for (String skuValue : skuValues) {
                    if (skuValue.equals(value.toString())) {
                        //返回蓝色在["蓝","红"]的坐标
                        return index;
                    }
                    index++;
                }

            }
        }
        return null;
    }

    /**
     * 根据属性的key获取这个属性的id
     *
     * @param properKey
     * @param skuProperties
     * @return
     */
    private Long getPropId(String properKey, List<Map<String, Object>> skuProperties) {
        for (Map<String, Object> skuProperty : skuProperties) {
            String specName = (String) skuProperty.get("specName");
            if (specName.equals(properKey)) {
                return Long.valueOf(skuProperty.get("id").toString());
            }
        }
        return null;
    }



}
