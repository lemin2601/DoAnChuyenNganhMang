package bean;

import conf.MessageStatus;
import controller.InterfServer;

import java.io.Serializable;

/**
 * Created by Administrator on 10/21/2017.
 */
public class Message implements Serializable {
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
}
