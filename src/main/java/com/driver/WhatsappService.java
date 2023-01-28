package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {
    public String createUser(String name, String mobile) {
        return null;
    }

    public Group createGroup(List<User> users) {
        return null;
    }

    public int createMessage(String content) {
        return 0;
    }

    public int sendMessage(Message message, User sender, Group group) {
        return 0;
    }

    public String changeAdmin(User approver, User user, Group group) {
        return null;
    }

    public int removeUser(User user) {
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return null;
    }
}
