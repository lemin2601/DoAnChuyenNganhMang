package tmp;

import java.io.Serializable;

public class Matrix implements Serializable {

    private static final long serialVersionUID = 1L;
    int[][] a;

    public Matrix(int[][] a) {
        this.a = a;
    }

    Matrix(int numRow, int numCol) {
        a = new int[numRow][numCol];
    }

    public int getNumCol() {
        return a[0].length;
    }

    public int getNumRow() {
        return a.length;
    }

    public int[][] getValue() {
        return a;
    }

    public int getValue(int i, int j) {
        return a[i][j];
    }

    public void setValue(int[][] value) {
        this.a = value;
    }

    public void setValue(int i, int j, int value) {
        this.a[i][j] = value;
    }

    @Override
    public String toString() {
        String result = "";
        for (int[] anA : a) {
            for (int j = 0; j < a[0].length; j++) {
                result += String.format("%3s", anA[j]) + "  ";
            }
            result += "\n";
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(Lib.RandomMatrix(16, 16).toString());
    }
    static Matrix ReadFile(String path){
        
        return null;
    }
}
