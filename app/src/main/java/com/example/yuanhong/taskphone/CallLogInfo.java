package com.example.yuanhong.taskphone;

public class CallLogInfo {
    public String number;
    public long date;
    public int type;
    public CallLogInfo(String number, long date, int type) {
        super();
        this.number = number;
        this.date = date;
        this.type = type;
    }
    public CallLogInfo() {
        super();
    }
}
