package controller;

import bean.Message;
import bean.Ticket;
import conf.MessageStatus;
import model.ModelTicket;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Administrator on 11/12/2017.
 */
public class ThreadAccessData implements  Runnable  {
    LamportManager lamportManager;
    InterfDatabase interfDatabase;
    Ticket ticket;
    Message message;

    public ThreadAccessData(InterfDatabase interfDatabase,LamportManager lamportManager, Ticket ticket) {
        this.interfDatabase = interfDatabase;
        this.lamportManager = lamportManager;
        this.ticket = ticket;
        this.message = new Message(this.lamportManager.getMyServer(),MessageStatus.REQ,this.lamportManager.TimeStamp());
    }

    @Override
    public void run() {
        //1. send request
        sendRequest();
        //2. wait reply and access data
        waitReply();
        accessData();
        //3. send release and clear before leave
        sendRelease();
        //4.updateUI();
    }

    private void sendRelease() {
        //clear needToSendReply;
        this.lamportManager.queueToEnter.remove(this.message);
        this.lamportManager.sendReplyToAll();
        //send request
        Message message = new Message(this.lamportManager.getMyServer(), MessageStatus.REL,this.lamportManager.TimeStamp());
        Thread thread = new Thread(new ThreadSendMessage(this.lamportManager.getListServers(),message));
        thread.start();
    }

    private void accessData() {
        try {
            interfDatabase.UpdateTicket(ticket);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void waitReply() {
        WaitObject waitObject = new WaitObject();
        while (true){
            ArrayList<InterfServer> listServers = this.lamportManager.getListServers();
            HashMap<InterfServer, Boolean> needToReceiveReply = this.lamportManager.needToReceiveReply;
            boolean checkReply = false;
            for(InterfServer interfServer:listServers){
                checkReply = needToReceiveReply.get(interfServer);
                if(checkReply){
                    //hadn't receive Reply from other server
                    break;
                }
            }
            if(checkReply) waitObject.waitOK();
            else return;
        }
    }

    private void sendRequest() {
        //clear needToSendReply;
        HashMap<InterfServer, Boolean> needToReceiveReply = this.lamportManager.needToReceiveReply;
        for(InterfServer interfServer:this.lamportManager.getListServers()){
            needToReceiveReply.put(interfServer,true);
        }

        //send request
        //add to queue and sort
        this.lamportManager.queueToEnter.add(message);
        Collections.sort(this.lamportManager.queueToEnter);
        //send message
        Thread thread = new Thread(new ThreadSendMessage(this.lamportManager.getListServers(),message));
        thread.start();
    }

    private  class ThreadSendMessage implements  Runnable{
        ArrayList<InterfServer> listServers;
        Message message;

        public ThreadSendMessage(ArrayList<InterfServer> listServers, Message message) {
            this.listServers = listServers;
            this.message = message;
        }

        @Override
        public void run() {
            for(InterfServer server:listServers){
                try {
                    server.PushMessage(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
