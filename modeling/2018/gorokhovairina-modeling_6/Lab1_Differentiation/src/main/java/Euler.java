import static java.lang.Math.abs;
import static java.lang.Math.round;

public class Euler {
    double h;
    double x0;
    int n;
    double[][] implicitTable;
    double[][] explicitTable;

    Euler(double h, int n, double a) {
        this.h = h;
        this.x0 = a;
        this.n = n;
        implicitTable = new double[n][2];
        explicitTable = new double[n][2];

        for (int i = 0; i < n; ++i) {
            implicitTable[i][0] = a;
            explicitTable[i][0] = a;
            a += h;
        }

        DoImplicitMethod();
        DoExpicitMethod();

    /*
        for (double[] tt : implicitTable) {
            System.out.println(tt[0] + "   " + tt[1]);
        }

        for (double[] tt : explicitTable) {
            System.out.println(tt[0] + "   " + tt[1]);
        }
    */

    }

    double getResult(double x, boolean isImplicit) {
        int ind = 0;
        double dif = abs(implicitTable[0][0] - x);
        for (int i = 1; i < n; i++){
            if (abs(implicitTable[i][0]-x) < dif)
            {
                dif = abs(implicitTable[i][0]-x);
                ind = i;
            }
        }

        if (isImplicit) {
            return implicitTable[ind][1];
        }
        else {
            return explicitTable[ind][1];
        }
    }

    private void DoImplicitMethod() {
        implicitTable[0][1] = 0;
        for (int i = 1; i < n; ++i)
            implicitTable[i][1] = implicitTable[i-1][1] + h*F(implicitTable[i-1][0], implicitTable[i-1][1]);
    }

    private void DoExpicitMethod() {
        explicitTable[0][1] = 0;
        for (int i = 1; i < n; ++i)
            explicitTable[i][1] = explicitTable[i-1][1] + h*(F(explicitTable[i-1][0], explicitTable[i-1][1])
                    + F(explicitTable[i][0], implicitTable[i][1]))/2;
    }

    private double F(double x, double y) {
        return x*x + y*y;
    }
}
