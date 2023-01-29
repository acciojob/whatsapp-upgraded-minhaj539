package com.driver;

import java.util.List;

public class Group {
    private String name;

    private String admin;
    private int numberOfParticipants;


    public Group() {
    }

    public Group(String grpName,int numberOfParticipants){
        this.name=grpName;
        this.numberOfParticipants=numberOfParticipants;
    }


    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
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
