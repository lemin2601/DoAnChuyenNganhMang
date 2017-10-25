package test;

/**
 * Created by Administrator on 10/21/2017.
 */
public class Message {
    private String msg;

    public Message(String str){
        this.msg=str;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String str) {
        this.msg=str;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Message a  = new Message("a");
        Message b = a;
        System.out.println(b.toString());
        a.setMsg("b");
        System.out.println(b.toString());
    }


}