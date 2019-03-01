package cn.ken.egou.controller;

import cn.ken.egou.client.RedisClient;
import cn.ken.egou.utils.RedisUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("common")
public class RedisClientController implements RedisClient {

    @RequestMapping(value = "redis", method = RequestMethod.POST)
    @Override
    public void set(@RequestParam("key")String key, @RequestParam("value")String value) {
        RedisUtils.set(key, value);
    }

    @RequestMapping(value = "redis/{key}", method = RequestMethod.GET)
    @Override
    public String get(@PathVariable("key") String key) {
        return RedisUtils.get(key);
    }
}
