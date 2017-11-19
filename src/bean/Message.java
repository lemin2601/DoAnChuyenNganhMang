package bean;

import conf.MessageStatus;
import controller.InterfServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Comparator;

/**
 * Created by Administrator on 10/21/2017.
 */
public class Message implements Serializable,Comparable {
    private static final long serialVersionUID = 1L;


    InterfServer from;
    MessageStatus type;
    long timestamp;

    public Message(InterfServer from,MessageStatus type, long timestamp) {
        this.from = from;
        this.type = type;
        this.timestamp = timestamp;
    }

    public InterfServer getFrom() {
        return from;
    }

    public MessageStatus getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }



    @Override
    public int compareTo(Object o) {
        Message m1= this;
        Message m2= (Message) o;
        if(m1.timestamp > m2.timestamp){
            return 1;
        }else if(m1.timestamp < m2.timestamp){
            return -1;
        }
        try {
            if(m1.getFrom().getID() < m2.getFrom().getID()){
                return -1;
            }else if(m1.getFrom().getID() > m2.getFrom().getID()){
                return 1;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
}
