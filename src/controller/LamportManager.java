package controller;

import bean.Message;
import bean.Ticket;
import conf.LamportStatus;
import conf.MessageStatus;

import java.rmi.RemoteException;
import java.util.*;

public class LamportManager {

    private LamportStatus lamportStatus;
    private long currentClock = 0;

    private InterfServer myServer;

    private ArrayList<InterfServer> listServers;

    HashMap<InterfServer, Boolean> needToSendReply;
    HashMap<InterfServer, Boolean> needToReceiveRelease;
    HashMap<InterfServer, Boolean> needToReceiveReply;

    ArrayList<Message> queueToEnter;

    public LamportManager(InterfServer server) {
        this.myServer = server;
        init();
    }

    private void init() {
        this.currentClock = 0;
        this.listServers = new ArrayList<>();
        this.queueToEnter = new ArrayList<>();

        this.needToReceiveRelease = new HashMap<>();
        this.needToSendReply = new HashMap<>();
        this.needToReceiveReply = new HashMap<>();

        for (InterfServer server : this.listServers) {
            this.needToReceiveRelease.put(server, false);
            this.needToSendReply.put(server, false);
            this.needToReceiveReply.put(server, false);
        }
    }



    public InterfServer getMyServer() {
        return myServer;
    }

    public boolean addNewServer(InterfServer server) {
        this.needToReceiveRelease.put(server,false);
        this.needToSendReply.put(server, false);
        this.needToReceiveReply.put(server, false);
        return this.listServers.add(server);
    }

    public boolean removeServer(InterfServer server) {
        this.needToReceiveRelease.remove(server);
        this.needToSendReply.remove(server);
        this.needToReceiveReply.remove(server);
        return this.listServers.remove(server);
    }

    public ArrayList<InterfServer> getListServers() {
        return this.listServers;
    }

    public void setListServers(ArrayList<InterfServer> servers) {
        this.listServers = servers;
    }

    //get Message yêu c?u truy c?p mi?n g?ng v?i th?i gian current clock
    public synchronized long TimeStamp() {
        return currentClock++;
    }

    public boolean addReplyAccess(Message message) {
        //check if have request to access.
        updateTimestamp(message);
        return this.needToReceiveReply.put(message.getFrom(), false);
    }

    private void updateTimestamp(Message message) {
        if(this.currentClock < message.getTimestamp()){
            this.currentClock = message.getTimestamp();
        }
    }

    public boolean addReleaseAccess(Message message) {
        //remove from queue to enter.
        updateTimestamp(message);
        return !this.needToReceiveRelease.put(message.getFrom(),false);
    }

    public boolean addRequestAccess(Message message) {
        updateTimestamp(message);
        boolean result = this.queueToEnter.add(message);
        this.needToSendReply.put(message.getFrom(), true);
        Collections.sort(this.queueToEnter);
        sendReplyToAll();
        return result;
    }

    public  void sendReplyToAll(){
        ArrayList<Message> queueToEnter = this.queueToEnter;
        try {
            queueToEnter.forEach(message -> {
                if (message.getFrom() == this.getMyServer()) {
                    return;
                }
                InterfServer from = message.getFrom();
                Boolean aBoolean = this.needToSendReply.get(from);
                if (aBoolean) {
                    //send reply
                    Message message1 = new Message(this.getMyServer(), MessageStatus.REP, this.TimeStamp());
                    try {
                        aBoolean = from.PushMessage(message1);
                        this.needToSendReply.put(from, false);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (ConcurrentModificationException e) {
        }

    }
    private class ThreadMessageProcess implements Runnable {
        private LamportManager lamportManager;
        public ThreadMessageProcess(LamportManager lamportManager) {
            this.lamportManager = lamportManager;
        }

        /***
         * ki?m tra x? lý ph?n h?i request ???c g?i ??n.
         */
        @Override
        public void run() {
            while (true) {
                // 1. ki?m tra trong queue có yêu c?u.
                //2. s?p x?p theo th?i gian timestamp t?ng d?n.
                //3. g?i reply tr? v? client
                //      n?u theo timestamp yêu c?u < timestamp mình không yêu c?u truy c?p database ==> reply
                //      add yêu c?u nh?n release t? client ?ó
                //      remove request ra kh?i queue
                ArrayList<Message> queueToEnter = this.lamportManager.queueToEnter;
                try {
                    queueToEnter.forEach(message -> {
                        if (message.getFrom() == this.lamportManager.getMyServer()) {
                            return;
                        }
                        InterfServer from = message.getFrom();
                        Boolean aBoolean = this.lamportManager.needToSendReply.get(from);
                        if (aBoolean) {
                            //send reply
                            Message message1 = new Message(this.lamportManager.getMyServer(), MessageStatus.REP, this.lamportManager.TimeStamp());
                            try {
                                aBoolean = from.PushMessage(message1);
                                this.lamportManager.needToSendReply.put(from, false);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (ConcurrentModificationException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
//
//        try {
//            LamportManager lamportManager = new LamportManager(new RMIServer());
//            lamportManager.sendRequest();
//
//
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        HashMap<String, Boolean> key;
        key = new HashMap<>();
        key.put("a", new Boolean(null));

        Boolean a = key.get("a");
        System.out.println(a.booleanValue());

        a = true;
        key.put("a", a);
        System.out.println(a.booleanValue());
        System.out.println(key.get("a").booleanValue());
    }

}
