import static java.lang.Math.abs;

class RungeKutt {
    private int order;
    private double h;
    private double x0;
    private int n;
    private double[][] table;

    RungeKutt(int order, double x0, double h, int n) throws Exception {
        this.order = order;
        this.h = h;
        this.n = n;
        this.x0 = x0;
        table = new double[n][2];
        for (int i = 0; i < n; ++i) {
            table[i][0] = x0;
            x0 += h;
        }

        if (order == 2)
            rk2();
        else if (order == 4)
            rk4();
        else
            throw new Exception("I don't know how to do it yet.\n ");
    }

    private void rk4() {
        table[0][1] = 0;
        for (int i = 1; i < n; ++i) {
            double xn = table[i-1][0];
            double yn = table[i-1][1];

            double k1 = F(xn, yn);
            double k2 = F(xn + h/2, yn + h/2*k1);
            double k3 = F(xn + h/2, yn + h/2*k2);
            double k4 = F(xn + h, yn + h*k3);

            table[i][1] = yn + h/6*(k1 + 2*k2 + 2*k3 + k4);
        }
    }
    private void rk2() {
        table[0][1] = 0;
        for (int i = 1; i < n; ++i) {
            double xn = table[i-1][0];
            double yn = table[i-1][1];

            double k1 = F(xn, yn);
            double k2 = F(xn + h/2, yn + h/2*k1);

            table[i][1] = yn + h*k2;
        }
    }

    double getResult(double x) {
        int ind = 0;
        double dif = abs(table[0][0] - x);
        for (int i = 1; i < n; i++) {
            if (abs(table[i][0] - x) < dif) {
                dif = abs(table[i][0] - x);
                ind = i;
            }
        }

        return table[ind][1];
    }


    private double F(double x, double y) {
        return x*x + y*y;
    }
}
