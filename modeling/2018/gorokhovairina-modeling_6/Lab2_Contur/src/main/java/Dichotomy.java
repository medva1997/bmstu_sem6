
public class Dichotomy extends Constants {

    private static double eps = 10e-2;

    private static double F(double p, double[][] T_r) {

        return 2/R/R * Integral.Simpson(p, T_r, table_n, false) - p0 * 7242/Tstart;
    }

    static double GetDichotomy(double I, double a_int, double b_int) {
        double a, b, c, h;
        a = 1;
        h = 1;
        double[][] T_r = Integral.getTArray(I, a_int, b_int);
        while (F(a, T_r)*F(a+h, T_r) > 0)
            a+=h;

        b = a+h;
        while (2*(b-a)/(b+a) > eps) {
            c = (a+b)/2;

            if (F(c, T_r) >= 0)
                b = c;
            else
                a = c;
        }
        return (a+b)/2;
    }
}
