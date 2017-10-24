package tmp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServer extends UnicastRemoteObject implements InterfServer {

    static ArrayList<InterfClient> clients;

    public RMIServer() throws RemoteException {
        super();
    }

    @Override
    public Matrix execute(Matrix a, Matrix b, String opera) throws RemoteException {
        System.out.println("\nHave request .... ");
        if (clients.isEmpty()) return null;
        if (opera.equalsIgnoreCase("+")) {
            if (a.getNumCol() != b.getNumCol() || a.getNumRow() != b.getNumRow()) {
                return null;
            }
            return processAdd(a, b);
        } else if (opera.equalsIgnoreCase("x")) {
            if (a.getNumCol() != b.getNumRow()) {
                return null;
            }
            return processMulti(a, b);
        }

        int[][] c = new int[a.getNumRow()][a.getNumCol()];
        for (int i = 0; i < a.getNumRow(); i++) {
            for (int j = 0; j < a.getNumCol(); j++) {
                c[i][j] = a.getValue(i, j) + b.getValue(i, j);
            }
        }
        return new Matrix(c);
    }

    @Override
    public boolean register(InterfClient client) {
        try {
            System.out.println("\nAdd Client :<<" + client.getIP() + ">>");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return clients.add(client);
    }

    @Override
    public boolean unRegister(InterfClient client) {
        try {
            System.out.println("\nRemove Client :<<" + client.getIP() + ">>");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return clients.remove(client);
    }

    @Override
    public void checkConnect() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            clients = new ArrayList<>();
            String ip = Lib.getMyIp();
            if (ip == null) return;
            System.setProperty("java.rmi.server.hostname", Lib.getMyIp());
            //đăng ký dịch vụ
            Registry r = LocateRegistry.createRegistry(8850);
            InterfServer server;
            server = new RMIServer();
            r.rebind("SERVER", server);
            System.out.println("ClientWork at:" + Lib.getMyIp() + " port: 8850");

            Registry r_forClient = LocateRegistry.createRegistry(8808);
            InterfServer server_forClient;
            server_forClient = new RMIServer();
            r_forClient.rebind("SERVER_FOR_CLIENT", server_forClient);
            System.out.println("ClientCalculator at:" + Lib.getMyIp() + " port: 8808");

            //kiểm tra kết nối với client 
            checkConnection();
        } catch (RemoteException ex) {
            System.out.println("system encrupt");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static void checkConnection() {
        while (true) {
            System.out.printf("\r%60s\r", "");
            System.out.print("Connected IP: ");
            try {
                for (InterfClient client : clients) {
                    boolean isConnected = true;
                    try {
                        System.out.print(client.getIP() + " | ");
                    } catch (RemoteException ex) {
                        isConnected = false;
                    }
                    if (!isConnected) {
                        System.out.println("\nRemove Client some client");
                        clients.remove(client);
                    }
                }
            } catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < System.currentTimeMillis() / 1000 % 5; i++) {
                System.out.print(".");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String execute() throws RemoteException {
        String ip = "List client:";
        for (InterfClient client : clients) {
            ip += client.getIP() + " | ";
        }
        return ip;
    }

    private Matrix processAdd(Matrix a, Matrix b) {
        int numOfClient = clients.size();//số client join
        if (numOfClient > a.getNumRow()) {
            numOfClient = a.getNumRow();
        }
        Thread[] threads = new Thread[numOfClient]; //2 thread
        MatrixAdd[] matrixAdds = new MatrixAdd[numOfClient]; // 2 matrix add          
        int numOfRowSub = (int) Math.ceil(((double) a.getNumRow() / (double) numOfClient)); // 8/3 = 3         
        //thực hiện việc chia matrix
        for (int i = 0; i < numOfClient; i++) {
            Matrix tmpA = Lib.SubMatrixByRow(a, i, numOfRowSub);
            Matrix tmpB = Lib.SubMatrixByRow(b, i, numOfRowSub);
            matrixAdds[i] = new MatrixAdd(clients.get(i), tmpA, tmpB);// chia ma trạn 
            threads[i] = new Thread(matrixAdds[i]);
            threads[i].start();
        }
        Matrix result = new Matrix(a.getNumRow(), a.getNumCol());
        for (int i = 0; i < numOfClient; i++) {
            try {
                threads[i].join();
                //nối matrix lại gửi trả client
                Matrix tmp = matrixAdds[i].result;
                System.out.println("Result by client: " + i + "\n" + tmp.toString());
                for (int x = 0; x < tmp.getNumRow(); x++) {
                    for (int y = 0; y < tmp.getNumCol(); y++) {
                        result.setValue(i * numOfRowSub + x, y, tmp.getValue(x, y));
                    }
                }
            } catch (InterruptedException ex) {
                //Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    private Matrix processMulti(Matrix a, Matrix b) {
        // chia ma trạn thành những ma trận nhân nhỏ, và gửi cho client
        // ở đây ta chia theo cột của ma trận a (cũng như hàng của ma trận b)
        int numOfClient = clients.size();//số client join
        if (numOfClient > a.getNumRow()) {
            numOfClient = a.getNumRow();
        }
        Thread[] threads = new Thread[numOfClient]; //Create thread process send and receive data with client
        MatrixMulti[] matrixMultis = new MatrixMulti[numOfClient];
        int numOfRowSub = (int) Math.ceil(((double) a.getNumRow() / (double) numOfClient)); // 8/3 = 3         
        //thực hiện việc chia matrix
        for (int i = 0; i < numOfClient; i++) {
            Matrix tmpA = Lib.SubMatrixByRow(a, i, numOfRowSub);
            matrixMultis[i] = new MatrixMulti(clients.get(i), tmpA, b);// chia ma trạn 
            threads[i] = new Thread(matrixMultis[i]);
            threads[i].start();
        }
        Matrix result = new Matrix(a.getNumRow(), b.getNumCol());
        for (int i = 0; i < numOfClient; i++) {
            try {
                threads[i].join();
                //nối matrix lại gửi trả client
                Matrix tmp = matrixMultis[i].result;
                System.out.println("Result by client: " + i + "\n" + tmp.toString());
                for (int x = 0; x < tmp.getNumRow(); x++) {
                    for (int y = 0; y < tmp.getNumCol(); y++) {
                        result.setValue(i * numOfRowSub + x, y, tmp.getValue(x, y));
                    }
                }
            } catch (InterruptedException ex) {
                //Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

}
