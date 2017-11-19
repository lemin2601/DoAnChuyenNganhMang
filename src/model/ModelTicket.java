package model;

import bean.Ticket;
import conf.TicketStatus;

import java.sql.*;
import java.util.ArrayList;

public class ModelTicket {
    private Connection conn;

    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;
    public ModelTicket(){
    }
    public ArrayList<Ticket> getList(){
        ArrayList<Ticket> alItem = new ArrayList<>();

        conn = DBConnection.getConnection();
        String sql = "SELECT * FROM Ticket";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                Ticket item = new Ticket(rs.getInt("num"), (rs.getInt("state")==1?TicketStatus.BOUGHT:TicketStatus.FREE));
                alItem.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return alItem;
    }
    public int updateTicket(Ticket ticket){
        conn = DBConnection.getConnection();
        int i = 0;
        String sql = "UPDATE Ticket SET state ="+(ticket.getState()==TicketStatus.BOUGHT?1:0)+" WHERE num="+ticket.getNum();
        try {
            st = conn.createStatement();
            i = st.executeUpdate(sql);

        } catch (SQLException ex) {
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return i;
    }

    public static void main(String[] args) {
        ModelTicket modelTicket = new ModelTicket();
        for (Ticket ticket : modelTicket.getList()) {
            ticket.setState(TicketStatus.BOUGHT);
            System.out.println(modelTicket.updateTicket(ticket));
//            System.out.println(ticket.getNum() +" " +ticket.getState());
        }
        for (Ticket ticket : modelTicket.getList()) {
            System.out.println(ticket.getNum() +" " +ticket.getState());
        }
    }
}
