package bean;

import conf.TicketStatus;

import java.io.Serializable;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    private int num;
    private TicketStatus state;

    public int getNum() {
        return num;
    }

    public void setState(TicketStatus state) {
        this.state = state;
    }

    public TicketStatus getState() {
        return state;
    }

    public Ticket(int num, TicketStatus state) {
        this.state = state;
        this.num = num;
    }

    @Override
    public String toString() {
        return "["+num+" ("+state+")]";
    }
}
