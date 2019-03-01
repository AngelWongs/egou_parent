package cn.ken.egou.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FastJsonUtilsTest {

    @Test
    public void textToJson() {
        User ken = new User("ken", 3L);
        /**
         * 对象转json
         */
        String string = JSONObject.toJSONString(ken);
        System.out.println(string);
        /**
         * json转对象
         */
        User user = JSONObject.parseObject(string, User.class);
        System.out.println(user);
        /**
         * list转json
         */
        List<User> userList = Arrays.asList(new User("name",1L),new User("ken", 2L));
        String string1 = JSONObject.toJSONString(userList);
        System.out.println(string1);
        /**
         * json转list
         */
        List<User> userList1 = JSONObject.parseArray(string1, User.class);
        System.out.println(userList1);


    }
}