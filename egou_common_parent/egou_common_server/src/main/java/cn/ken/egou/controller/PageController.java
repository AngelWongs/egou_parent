package cn.ken.egou.controller;

import cn.ken.egou.client.PageStaticClient;
import cn.ken.egou.constant.GlobalConstant;
import cn.ken.egou.utils.VelocityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/common")
public class PageController implements PageStaticClient {

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @Override
    public void getPageStatic(@RequestBody Map<String, Object> map) {
        //逻辑实现:
        Object model = map.get(GlobalConstant.PAGE_MODE);
        String templateFilePathAndName = (String) map.get(GlobalConstant.PAGE_TEMPLATE);
        String targetFilePathAndName = (String) map.get(GlobalConstant.PAGE_TEMPLATE_HTML);
        System.out.println("model==="+model);
        System.out.println("templateFilePathAndName==="+templateFilePathAndName);
        System.out.println("targetFilePathAndName==="+targetFilePathAndName);

        VelocityUtils.staticByTemplate(model, templateFilePathAndName, targetFilePathAndName);
    }
}