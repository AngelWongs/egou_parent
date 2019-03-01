package cn.ken.egou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;


@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.ken.egou.mapper")
@EnableFeignClients(basePackages = "cn.ken.egou.client") //扫描本地feign的接口
public class ProductServerApplication {
    public static void main(String[] args){
        SpringApplication.run(ProductServerApplication.class);
    }
}
