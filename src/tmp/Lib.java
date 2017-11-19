package tmp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

public class Lib {

    public static String getMyIp() {
        try {
            Enumeration<NetworkInterface> NICs = NetworkInterface.getNetworkInterfaces();
            while (NICs.hasMoreElements() == true) {
                NetworkInterface NIC = NICs.nextElement();
                Enumeration<InetAddress> IPs = NIC.getInetAddresses();
                while (IPs.hasMoreElements() == true) {
                    InetAddress IP = IPs.nextElement();
                    if (IP instanceof java.net.Inet4Address) {
                        if (IP.toString().startsWith("/10")) {
                            return IP.toString().substring(1);
                        }
                    }
                }
            }
        } catch (SocketException e4) {
            e4.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Lib.getMyIp());
    }

    public static Matrix RandomMatrix(int i, int j) {

        int[][] result = generateMatrix(i, j);
        return new Matrix(result);
    }

    public static int[][] generateMatrix(int m, int n) {
        byte min = -10;
        byte max = 10;
        int[][] array = new int[m][n];
        Random random = new Random();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = random.nextInt(max + 1 - min) + min;
            }
        }
        return array;
    }

    public static Matrix SubMatrixByRow(Matrix a, int rowStart, int numOfRow) {
        //check maxx matrix
        Matrix result;
        if (a.getNumRow() >= (rowStart * numOfRow + numOfRow)) {
            result = new Matrix(numOfRow, a.getNumCol());

        } else {
            result = new Matrix(a.getNumRow() - numOfRow * rowStart, a.getNumCol());
        }
        for (int i = 0; i < result.getNumRow(); i++) {
            for (int j = 0; j < result.getNumCol(); j++) {
                result.setValue(i, j, a.getValue(rowStart * numOfRow + i, j));
            }
        }
        return result;
    }

    public static Matrix SubMatrixByCol(Matrix a, int colStart, int numOfCol) {
        //check maxx matrix
        Matrix result;
        if (a.getNumCol() >= (colStart * numOfCol + numOfCol)) {
            result = new Matrix(a.getNumRow(), numOfCol);

        } else {
            result = new Matrix(a.getNumRow(), a.getNumCol() - numOfCol * colStart);
        }
        for (int i = 0; i < result.getNumRow(); i++) {
            for (int j = 0; j < result.getNumCol(); j++) {
                result.setValue(i, j, a.getValue(i, colStart * numOfCol + j));
            }
        }
        return result;
    }
}
