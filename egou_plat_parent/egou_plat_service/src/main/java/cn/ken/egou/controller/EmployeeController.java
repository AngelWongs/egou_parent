package cn.ken.egou.controller;

import cn.ken.egou.domain.Employee;
import cn.ken.egou.utils.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){
        if ("admin".equals(employee.getUserName()) && "admin".equals(employee.getPassword())){
            return AjaxResult.me().setSuccess(true).setMsg("登录成功");
        }
        return AjaxResult.me().setSuccess(false).setMsg("登录失败");
    }
    @RequestMapping("/test")
    public Employee test(){
        return new Employee(1L,"ken","test");
    }
}
