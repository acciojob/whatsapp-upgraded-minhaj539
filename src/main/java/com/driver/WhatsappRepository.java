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
            grpName="Group "+groupCnt;
        }
        Group group=new Group(grpName,users.size());
        group.setAdmin(users.get(0).getName());
        group.setUserList(users);
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
            userDb.put(user.getMobile(),user);

            for(int i=0;i<userList.size();i++){
                if(userList.get(i).equals(user)){
                    userList.remove(i);
                    break;
                }
            }
            userList.add(user);
            group.setUserList(userList);


          //  List<Message> messageList=groupDb.get(user).getMessageList();
        List<Message> messageList=groupDb.get(groupName).getMessageList();
            messageList.add(message);
            group.setMessageList(messageList);
            groupDb.put(groupName,group);
            return messageList.size();





    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
      String grpName=group.getName();
      if(!groupDb.containsKey(grpName)) throw new Exception("Group does not exist");
      String grpAdmin=group.getAdmin();
      if(!approver.getName().equals(grpAdmin)) throw new Exception("Approver does not have rights");
      List<User> userList=groupDb.get(grpName).getUserList();
      if(!userList.contains(user)) throw new Exception("User is not a participant");
      //if(user.getName().equals(grpAdmin)) throw new Exception("user is admin");

      String newAdmin=user.getName();
      group.setAdmin(newAdmin);
      groupDb.remove(approver.getName());
      groupDb.put(newAdmin,group);


        return "SUCCESS";
    }

    public int removeUser(User user) throws Exception {


        int cnt=0;
        boolean isPresent=false;
        for(Group group:groupDb.values()){
            List<User> userList=group.getUserList();
            for(User userInList:userList){
                if(user.equals(userInList)){
                    isPresent=true;
                    if(userInList.getName().equals(group.getAdmin())){
                        throw new Exception("Cannot remove admin");
                    }
                    else{
                        List<Message> userMessages=user.getMessageList();
                        List<Message> groupMessages=group.getMessageList();
                        for(int i=0;i<messageDb.size();i++){
                            for(int j=0;j<userMessages.size();j++)
                            if(messageDb.get(i).equals(userMessages.get(j))){
                                messageDb.remove(i);
                            }
                        }
                        for(int i=0;i<groupMessages.size();i++){

                            for(int j=0;j<userMessages.size();j++)
                                if(groupMessages.get(i).equals(userMessages.get(j))){
                                    groupMessages.remove(i);
                                }
                        }
                        group.setMessageList(groupMessages);

                        for(int i=0;i<userList.size();i++){
                            if(userList.get(i).equals(user))
                                userList.remove(i);
                            group.setUserList(userList);
                            groupDb.put(group.getName(), group);
                        }
                      cnt=group.getUserList().size()+group.getMessageList().size()+messageDb.size();
                        break;
                    }
                }
            }
        }
        if(isPresent==false) throw new Exception("User not found");
        return cnt;
    }

    public String findMessage(Date start, Date end, int k) {
        return null;
    }
}

