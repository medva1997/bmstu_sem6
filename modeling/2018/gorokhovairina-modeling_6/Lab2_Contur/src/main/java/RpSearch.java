
public class RpSearch extends Constants{
    static double GetRp(double I) {

        double res = le/(2*Math.PI*GetIntegralRp(I, 0, R));
        return res;
    }

    private static double GetIntegralRp(double I, double a, double b) {
        double[][] T_r = Integral.getTArray(I, a, b);
        double p = Dichotomy.GetDichotomy(I, 0, R);
        System.out.println(p);
        return Integral.Simpson(p, T_r, table_o_log, true);
    }


}
