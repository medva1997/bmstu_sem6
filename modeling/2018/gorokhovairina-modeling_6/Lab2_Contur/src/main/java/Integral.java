import static java.lang.Math.pow;

public class Integral extends Constants {

    static final int NODES = 41;

    static double Simpson(double p, double[][] T_array, double[][] table, boolean isLog) {
        double res = 0;
        for (int i = 0; i < NODES; i++) {
            if (i == 0 || i == (NODES-1))
                res += GetFunction(T_array[1][i], p, T_array[0][i], table, isLog);

            else if (i%2 == 1)
                res += 4 * GetFunction(T_array[1][i], p, T_array[0][i], table, isLog);

            else
                res += 2 * GetFunction(T_array[1][i], p, T_array[0][i], table, isLog);

        }
        res *= (T_array[0][1]-T_array[0][0])/3;
        return res;
    }

    private static double GetFunction(double T, double p, double r, double[][] table, boolean isLog) {
        double res = Interpolation.TwoDimensionalInterpolation(T, p, table, isLog);
        res *= r;
        return res;
    }


    static double[][] getTArray(double I, double a, double b) {
        double[][] array = new double[2][41];
        double h = (b-a)/40;
        double T0_I = Interpolation.LinealInterpolation(table_I_T_n, 0, I, 1);
        double n_I = Interpolation.LinealInterpolation(table_I_T_n, 0, I, 2);
        for (int i = 0; i < 41; ++i) {
            array[0][i] = a;
            array[1][i] = (Tw-T0_I)*pow((a/R),n_I) + T0_I;
            a+=h;
        }
        return array;
    }
}
