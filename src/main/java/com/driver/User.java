package com.driver;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String mobile;

    private List<Message> messageList;


    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
        this.messageList=new ArrayList<>();
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
