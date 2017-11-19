package controller;

import bean.Ticket;
import model.ModelTicket;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/25/2017.
 */
public class DataManager {
    ModelTicket modelTicket;


    public DataManager() {
        modelTicket = new ModelTicket();
    }

    public int updateTicket(Ticket ticket)  {
        return modelTicket.updateTicket(ticket);
    }

    public ArrayList<Ticket> getList(){
        return modelTicket.getList();
    }

}
