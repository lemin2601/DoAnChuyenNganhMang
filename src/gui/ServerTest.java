package gui;

import bean.Ticket;
import conf.LamportStatus;
import conf.MessageStatus;
import conf.TicketStatus;
import controller.DataManager;
import controller.InterfServer;
import controller.LamportManager;
import controller.RMIServer;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/12/2017.
 */
public class ServerTest {
    public static void main(String[] args) throws RemoteException {
        DataManager dataManager = new DataManager();
        while (true){
            ArrayList<Ticket> list = dataManager.getList();
            System.out.println(list.toString());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

       /* RMIServer interfServer = new RMIServer();
        LamportManager lamportManager = new LamportManager(interfServer);
        Ticket ticket = new Ticket(1, TicketStatus.BOUGHT);
//        boolean b = lamportManager.updateTicket(ticket);
*/
    }
}
