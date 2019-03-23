import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.Math.log;


public class Main extends Constants{

    private static double[][] ReadFromFile(String path, int m, int n) {
        double matrix[][] = new double[m][n];
        try {
            Scanner sc = new Scanner(new File(path));
            for (int i = 0; i < m; ++i)
                for (int j = 0; j < n; ++j)
                    matrix[i][j] = Double.valueOf(sc.next());
            sc.close();
            System.out.println("File " + path + " successfully read.");
            return matrix;
        } catch (FileNotFoundException e) {
            System.out.println("File " + path + " does not found.");
            return matrix;
        } catch (InputMismatchException e) {
            System.out.println("File " + path + " is incorrect.");
            return matrix;
        }
    }

    private static double[][] LogTable(double table[][]) {
        int m = table.length;
        int n = table[0].length;
        double[][] logTable = new double[m][n];
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j) {
                if (j == 0)
                    logTable[i][j] = table[i][j];
                else
                    logTable[i][j] = log(table[i][j]);
            }
        return logTable;
    }

    static void PrintTable(double[][] matrix) {
        int n = matrix[0].length;
        for (double[] aMatrix : matrix) {
            for (int j = 0; j < n; ++j)
                System.out.printf("%10.3e ", aMatrix[j]);
            System.out.print("\n");
        }
    }


    public static void main (String[] args) {

        Constants.table_n = ReadFromFile("tab_T_P_n", 19, 4);
        //Constants.table_n_log = LogTable(table_n);

        Constants.table_o = ReadFromFile("tab_T_P_б", 19, 4);
        Constants.table_o_log = LogTable(table_o);

        Constants.table_I_T_n = ReadFromFile("tab_I_T0_n", 9, 3);

        System.out.printf("%8s | %10s | %10s | %10s \n","t", "I", "U", "Rp");
        Differential.RungeKutta(1e-5, 70);

    }
}
