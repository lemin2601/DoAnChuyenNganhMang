package controller;

import bean.Message;
import conf.LamportStatus;
import conf.MessageStatus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Administrator on 10/25/2017.
 */
public class LamportManager implements Runnable {

    LamportStatus lamportStatus;
    private int currentClock = 0;
    Queue<Message> messageQueue;
    ArrayList<Message> messageReply;
    InterfServer myServer;
    HashMap<InterfServer, LamportStatus> stateServers;
    ArrayList<InterfServer> listServers;

    public LamportManager(RMIServer server) {
        this.myServer = this.myServer;
        this.listServers = server.getServers();
        this.stateServers = new HashMap<>();
        this.currentClock = 0;
        messageQueue = new LinkedList<Message>();
    }

    //get Message yêu c?u truy c?p mi?n g?ng v?i th?i gian current clock
    public synchronized long getTimeStamp() {
        return currentClock++;
    }

    //g?i message ??n server
    public boolean sendMessage(Message message, InterfServer server) {

        try {
            server.PushMessage(message);
        } catch (RemoteException e) {
            //c? g?ng g?i tin nh?n n?u không g?i ???c
            int countTry = 0;
            while (countTry < 3) {
                try {
                    server.PushMessage(message);
                } catch (RemoteException e1) {
                    countTry++;
                }
            }
        }
        return false;
    }

    //g?i yêu c?u truy c?p data ??n t?t c? client
    public boolean request() {
        Message message = new Message(myServer, MessageStatus.REQ, getTimeStamp());
        boolean result = false;
        for (InterfServer server : this.listServers) {
            LamportStatus lamportStatus = stateServers.get(server);
            if (lamportStatus != LamportStatus.WAITING_REPLY) {
                result = sendMessage(message,server);
                if(result) lamportStatus = LamportStatus.WAITING_REPLY;
            }
        }
        return  result;
    }

    // tr? l?i yêu c?u, cho phép truy c?p data
    public boolean reply() {
        Message message = new Message(myServer, MessageStatus.REP, getTimeStamp());
        boolean result = false;
        for (InterfServer server : this.listServers) {
            LamportStatus lamportStatus = stateServers.get(server);
            if (lamportStatus != LamportStatus.IN_CRITICAL_SECTION) {
                result = sendMessage(message,server);
                if(result) lamportStatus = LamportStatus.IN_CRITICAL_SECTION;
            }
        }
        return  result;
    }

    //g?i tin nh?n thoát kh?i mi?n g?ng ??n các server
    public boolean release() {
        // tr? l?i yêu c?u, cho phép truy c?p data
        Message message = new Message(myServer, MessageStatus.REL, getTimeStamp());
        boolean result = false;
        for (InterfServer server : this.listServers) {
            LamportStatus lamportStatus = stateServers.get(server);
            if (lamportStatus != LamportStatus.RELEASE) {
                result = sendMessage(message,server);
                if(result) lamportStatus = LamportStatus.RELEASE;
            }
        }
        return  result;
    }

    @Override
    public void run() {
        while (true) {
            this.receiveMessage();
        }
    }


    public void receiveMessage() {
//        for (InterfServer server:){
//
//        }
    }

    public void receiveMessage(Message message) {
        switch (message.getType()) {
            case REQ:
                //yêu c?u truy c?p mi?n g?ng
                this.messageQueue.add(message);
                processRequest();
                break;
            case REP:
                // nh?n ???c tr? l?i yêu c?u truy c?p mi?n g?ng
                break;
            case REL:
                // gi?i phóng truy c?p mi?n g?ng
                break;
        }
    }

    private void processRequest() {
//        if(this.lamportStatus)
    }
}
