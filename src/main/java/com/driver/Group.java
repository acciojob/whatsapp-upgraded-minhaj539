package com.driver;

import java.util.List;

public class Group {
    private String name;

    private String admin;
    private int numberOfParticipants;

    List<User> userList;
    List<Message> messageList;

    public Group() {
    }

    public Group(String grpName,int numberOfParticipants){
        this.name=grpName;
        this.numberOfParticipants=numberOfParticipants;
    }
    public Group(List<User> userList) {
        this.userList = userList;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }





    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }



    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }
}
