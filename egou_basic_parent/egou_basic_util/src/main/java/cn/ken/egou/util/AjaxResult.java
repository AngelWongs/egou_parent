package cn.ken.egou.util;

public class AjaxResult {
    private Boolean success=true;
    private String msg = "";
    private Object object;

    public static AjaxResult me(){
        return new AjaxResult();
    }

    public AjaxResult() {
    }

    public AjaxResult(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public AjaxResult setObject(Object object) {
        this.object = object;
        return this;
    }
}
