package tmp;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixAdd implements Runnable, Task<Matrix>, Serializable {

    InterfClient client;
    Matrix a, b, result;

    boolean isDone = false;

    public MatrixAdd(InterfClient client, Matrix a, Matrix b) {
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
        if (a.getNumCol() != b.getNumCol() || a.getNumRow() != b.getNumRow()) {
            return null;
        }
        int[][] c = new int[a.getNumRow()][a.getNumCol()];
        for (int i = 0; i < a.getNumRow(); i++) {
            for (int j = 0; j < a.getNumCol(); j++) {
                c[i][j] = a.getValue(i, j) + b.getValue(i, j);
            }
        }
        isDone = true;
        return new Matrix(c);
    }

    public void setClient(InterfClient client) {
        this.client = client;
    }


}
