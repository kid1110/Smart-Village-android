package com.example.smartvillage.result;

public enum Status {

    PassWordError(-401,"密码或者用户名错误"),
    GetUserInfoError(-402,"获取用户信息失败"),
    AnoterDeviceHasLogin(-411,"另一台设备已经登录"),
    JwtError(-410,"token验证失败"),
    SendMessage(0,"请求websocket通信"),
    SendWebSocketList(2,"传送列表"),
    SendWebSocketMsg(3,"传送信息"),
    Success(1,"成功");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    Status(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
