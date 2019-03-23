import static java.lang.Math.exp;

public class Interpolation extends Constants {
    static double LinealInterpolation(double[][] table, int col1, double value_col1, int col2) {
        double I0, I1, x0, x1;
        int n = table.length;
        if (value_col1 < table[0][col1]) {
            I0 = table[0][col1];
            I1 = table[1][col1];
            x0 = table[0][col2];
            x1 = table[1][col2];
        }
        else if (value_col1 > table[n-1][col1]) {
            I0 = table[n-2][col1];
            I1 = table[n-1][col1];
            x0 = table[n-2][col2];
            x1 = table[n-1][col2];
        }
        else {
            int i = 0;
            while (value_col1 >= table[i][col1]) {
                if (value_col1 == table[i][col1])
                    return table[i][col2];
                i++;
            }
            I0 = table[i-1][col1];
            I1 = table[i][col1];
            x0 = table[i-1][col2];
            x1 = table[i][col2];
        }

        return x0 + (x1-x0)*(value_col1 - I0)/(I1 - I0);
    }

    static double TwoDimensionalInterpolation(double value_T, double value_p, double[][] table, boolean isLog) {
        double x0, x1, res_x, p0 = 0.0, p1 = 0.0;
        int col = -1, col1 = 0, col0 = 0;
        switch ((int)(value_p)) {
            case 5:
                col = 1;
                break;
            case 15:
                col = 2;
                break;
            case 25:
                col = 3;
                break;
            default:
            {
                if (value_p < 15) {
                    col0 = 1; col1 = 2;
                    p0 = 5.0; p1 = 15.0;
                }
                else {
                    col0 = 2; col1 = 3;
                    p0 = 15.0; p1 = 25.0;
                }

            }
        }
        if (col == -1) {
            x0 = LinealInterpolation(table, 0, value_T, col0);
            x1 = LinealInterpolation(table, 0, value_T, col1);
            res_x = isLog ? exp(x0) + (exp(x1) - exp(x0))*(value_p - p0)/(p1-p0)
                          : x0 + (x1 -x0)*(value_p - p0)/(p1 - p0);

        }
        else //if p = 5 | 15 | 25
            res_x = isLog ? exp(LinealInterpolation(table, 0, value_T, col))
                    : LinealInterpolation(table, 0, value_T, col);

        return res_x;

    }
}
