import java.lang.reflect.Type;

import static java.lang.Math.pow;

public class Pikar {
    private int approximation;
    private double[][] matrixCoefs;
    private int[][] matrixPower;

    Pikar (int app){
        approximation = app;
        int m = countOfTerms(app);
        matrixCoefs = new double[approximation][m];
        matrixPower = new int[approximation][m];
        matrixCoefs[0][0] = 1./3;
        matrixPower[0][0] = 3;
        for (int i = 1; i < approximation; ++i) {
            int count = countOfTerms(i);
            matrixCoefs[i][0] = 1./3;
            matrixPower[i][0] = 3;
            for (int j = 1; j <= count; j++) {
                matrixPower[i][j] = matrixPower[i-1][j-1] * 2 +1;
                matrixCoefs[i][j] = pow(matrixCoefs[i-1][j-1], 2) / matrixPower[i][j];
            }
            int j = count+1;
            for (int M = 0; M < count -1; ++M)
                for (int N = M+1; N < count; ++N) {
                    matrixPower[i][j] = matrixPower[i - 1][M] + matrixPower[i - 1][N] + 1;
                    matrixCoefs[i][j] = matrixCoefs[i-1][M] * matrixCoefs[i-1][N] * 2 / matrixPower[i][j];
                    j++;
                }
        }
        //printMatrix(matrixPower);
        //printMatrix(matrixCoefs);
    }

    double getResult(double x) {
        double res = 0;
        for (int i = 0; i < countOfTerms(approximation); ++i)
            res += matrixCoefs[approximation-1][i] * pow(x, matrixPower[approximation-1][i]);
        return res;
    }

    private int countOfTerms(int app) {
        int count = 0;
        for (int i = 0; i < app; ++i) {
            //арифметическая прогрессия
            int c = (count+1)*count/2;
            count = c+1;
        }
        return count;
    }

    void printMatrix(int matrix[][]) {
        for (int[] aMatrix : matrix) {
            for (int anAMatrix : aMatrix) {
                System.out.print(anAMatrix + " ");
            }
            System.out.println();
        }
    }

    void printMatrix(double matrix[][]) {
        for (double[] aMatrix : matrix) {
            for (double anAMatrix : aMatrix) {
                System.out.print(anAMatrix + " ");
            }
            System.out.println();
        }
    }
}
