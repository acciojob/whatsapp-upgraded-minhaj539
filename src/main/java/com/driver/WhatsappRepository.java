package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {


    Map<String,User> userDb;
    Map<String,Group> groupDb;

    List<Message> messageDb;

    int groupCnt;
    int msgCnt;

    public WhatsappRepository(){
        userDb=new HashMap<>();
        groupDb=new HashMap<>();
        messageDb=new ArrayList<>();
        groupCnt=0;
        msgCnt=0;
    }
    public String createUser(String name, String mobile) throws Exception {
        if(userDb.containsKey(mobile)) throw new Exception("User already exists");
      User user=new User(name,mobile);
      userDb.put(mobile,user);
      return "SUCCESS";

    }

    public Group createGroup(List<User> users) {
        String grpName;
        if(users.size()==2){
            grpName=users.get(1).getName();
        }
        else{
            groupCnt++;
            grpName="group "+groupCnt;
        }
        Group group=new Group(grpName,users.size());
        group.setAdmin(users.get(0).getName());
        groupDb.put(grpName,group);
        return group;

    }

    public int createMessage(String content) {
        msgCnt++;
        Message message=new Message(msgCnt,content);
        message.setTimestamp(new Date());
        messageDb.add(message);
        return msgCnt;
    }

    public int sendMessage(Message messagex, User senderx, Group groupx) throws Exception{
        int msgId=messagex.getId();
        String senderMob= senderx.getMobile();
        String groupName= groupx.getName();
        if (!groupDb.containsKey(groupName)) throw new Exception("Group does not exist");

        Message message=messageDb.get(msgId);
        User user=userDb.get(senderMob);
        Group group=groupDb.get(groupName);



            List<User> userList=groupDb.get(groupName).getUserList();
            if(userList==null || !userList.contains(user))  throw new Exception("You are not allowed to send message");

            List<Message> userMessageList=user.getMessageList();
            userMessageList.add(message);
            user.setMessageList(userMessageList);
            userDb.put(user.getName(),user);

            List<Message> messageList=groupDb.get(user).getMessageList();
            messageList.add(message);
            group.setMessageList(messageList);
            groupDb.put(groupName,group);
            return messageList.size();


      //  return 0;

    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
      String grpName=group.getName();
      if(!groupDb.containsKey(grpName)) throw new Exception("group not exist");
      String grpAdmin=group.getAdmin();
      if(!approver.getName().equals(grpAdmin)) throw new Exception("not an admin");
      List<User> userList=groupDb.get(grpName).getUserList();
      if(!userList.contains(user)) throw new Exception("user not exist");
      if(user.getName().equals(grpAdmin)) throw new Exception("user is admin");

      String newAdmin=user.getName();
      group.setAdmin(newAdmin);
      groupDb.remove(approver.getName());
      groupDb.put(newAdmin,group);


        return "SUCCESS";
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
        //return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return null;
    }
}

