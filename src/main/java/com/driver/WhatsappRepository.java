package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {


    Map<String,User> userDb=new HashMap<>();
    Map<String,Group> groupDb=new HashMap<>();

    List<Message> messageDb=new ArrayList<>();
    int groupCnt=0,msgCnt=0;
    public String createUser(String name, String mobile) {

        User user=new User(name,mobile);
        userDb.put(name,user);
        return "created";
    }

    public Group createGroup(List<User> users) {
        Group group=new Group();
        group.setNumberOfParticipants(users.size());
        if(users.size()==2) {
            group.setName(users.get(1).getName());

        }
        if (users.size() > 2) {
            groupCnt++;
            group.setName("group" + groupCnt);


        }
        group.setAdmin(users.get(0).getName());
        group.setUserList(users);
        groupDb.put(group.getName(), group);
        return group;
    }

    public int createMessage(String content) {
        msgCnt++;
        Message message=new Message();
        message.setId(msgCnt);
        message.setContent(content);
        message.setTimestamp(new Date());
        messageDb.add(message);
        return messageDb.size();
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
               String grpName= group.getName();
            if (!groupDb.containsKey(grpName)) throw new Exception("group not exist");


            List<User> userList=groupDb.get(grpName).getUserList();
            if(!userList.contains(sender))  throw new Exception("user not exist in group");
            List<Message> userMessageList=sender.getMessageList();
            userMessageList.add(message);
            sender.setMessageList(userMessageList);
            userDb.put(sender.getName(),sender);
            List<Message> messageList=groupDb.get(grpName).getMessageList();
            messageList.add(message);
            group.setMessageList(messageList);
            groupDb.put(grpName,group);
            return messageList.size();

    }

    public String changeAdmin(User approver, User user, Group group) {


        return null;
    }

    public int removeUser(User user) throws Exception {

        for(Group group:groupDb.values()){
            List<User> userList=group.getUserList();
            for(User userx:userList){
                if(user.equals(userx)){
                    if(user.getName().equals(group.getAdmin())) throw new Exception("cant remove admin");
                    List<Message> messageList = group.getMessageList();
                    for(Message message:user.getMessageList()) {
                        if(messageList.contains(message)){
                            messageList.remove(message);
                        }
                        if(messageDb.contains(message)){
                            messageDb.remove(message);
                        }
                    }
                    group.setMessageList(messageList);
                    userList.remove(user);
                    group.setUserList(userList);
                    groupDb.put(group.getName(), group);
                    return group.getMessageList().size()+group.getUserList().size()+messageDb.size();
                    //the updated number of users in the group + the updated number of messages in group + the updated number of overall messages
                }
            }
        }
        throw new Exception("user not found");
    }

    public String findMessage(Date start, Date end, int k) {
        return null;
    }
}

