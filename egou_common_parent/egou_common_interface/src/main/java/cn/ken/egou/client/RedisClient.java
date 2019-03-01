package cn.ken.egou.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "COMMON-PROVIDER",fallbackFactory = RedisClientFallbackFactory.class)
@RequestMapping("common")
public interface RedisClient {
    @RequestMapping(value = "redis",method = RequestMethod.POST)
    void set(@RequestParam("key") String key, @RequestParam("value") String value);

    @RequestMapping(value = "redis/{key}",method = RequestMethod.GET)
    String get(@RequestParam("key") String key);
}
