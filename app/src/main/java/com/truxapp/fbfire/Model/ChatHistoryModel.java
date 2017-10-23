package com.truxapp.fbfire.Model;

import java.io.Serializable;

public class ChatHistoryModel implements Serializable {
    private String message;
    private String msgfrom;
    private String createtime;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgfrom() {
        return msgfrom;
    }

    public void setMsgfrom(String msgfrom) {
        this.msgfrom = msgfrom;
    }
}
