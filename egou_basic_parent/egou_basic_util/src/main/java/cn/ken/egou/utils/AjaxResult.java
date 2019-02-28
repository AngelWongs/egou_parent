package cn.ken.egou.utils;

/**
 * @Author: wenbing
 * @Date: 2019/2/22 15:07
 * @Version 1.0
 */
public class AjaxResult {
    private boolean success = true;
    private String msg = "操作成功";

    private Object object;//对象值:供我们在返回前台的时候，可以返回一个对象


    public static AjaxResult me() {
        return new AjaxResult();
    }

    public boolean isSuccess() {
        return success;
    }


    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Object getObject() {
        return object;
    }

    public AjaxResult setObject(Object object) {
        this.object = object;
        return this;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                '}';
    }

    public static void main(String[] args) {
        //链式编程
        AjaxResult ajaxResult = AjaxResult.me().setSuccess(true).setMsg("sd").setObject("ssdf");
        System.out.println(ajaxResult);
    }
}
