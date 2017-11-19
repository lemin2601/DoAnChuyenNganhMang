package tmp;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixMulti implements Runnable, Task<Matrix>, Serializable {

    InterfClient client;
    Matrix a, b, result;
    boolean isDone;

    public MatrixMulti(InterfClient client, Matrix a, Matrix b) {
        this.client = client;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        try {
            //yêu cầu thực hiện ở client;
            result = client.execute(this);
        } catch (RemoteException ex) {
            Logger.getLogger(MatrixAdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Matrix execute() {
        //thực hiện Công Matrix a+b duoc thuc hien o client;
        if (a.getNumCol() != b.getNumRow()) {
            return null;
        }
        int[][] c = new int[a.getNumRow()][b.getNumCol()];

        for (int i = 0; i < a.getNumRow(); i++) {
            for (int j = 0; j < b.getNumCol(); j++) {
                int tmp = 0;
                for (int x = 0; x < a.getNumCol(); x++) {
                    tmp += a.getValue(i, x) * b.getValue(x, j);
                }
                c[i][j] = tmp;
            }
        }
         isDone = true;
        return new Matrix(c);
    }

    void setClient(InterfClient get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
