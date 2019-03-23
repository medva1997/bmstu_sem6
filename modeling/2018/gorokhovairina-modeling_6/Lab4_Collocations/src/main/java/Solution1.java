import java.util.ArrayList;

public class Solution1 {

    public Solution1() {
        prepareData();
        double[][] matrix = prepareToSweep();
        Data.U = sweep(matrix,Data.n);
    }

    private double[] sweep(double[][] matrix, int n) {
        double[] u = new double[n];
        double[] ksi = new double[n];
        double[] teta = new double[n];

        ksi[1] = -matrix[0][1]/matrix[0][0]; // M1/K1
        teta[1] = matrix[0][3]/matrix[0][0]; // P1/K1

        for (int i = 2; i < n; i++) {
            double f = -matrix[i-1][1] - matrix[i-1][0]*ksi[i-1];
            ksi[i] = matrix[i-1][2]/f;
            teta[i] = (matrix[i-1][0]*teta[i-1] - matrix[i-1][3])/f;
        }

        u[n-1] = (matrix[n-1][3] - matrix[n-1][1]*teta[n-1])/
                (matrix[n-1][2] + matrix[n-1][1]*ksi[n-1]);

        for (int i = n-1, i1 = n-1; --i >= 0; --i1)
            u[i] = ksi[i-1]*u[i-1] + teta[i-1];

        return u;
     }

    private double[][] prepareToSweep() {
        double[][] matrix = new double[Data.n][4];
        matrix[0][0] = -1;
        matrix[0][1] = 1;
        int n = (Data.n-1)*2;
        for (int i = 2, j = 1; j<n; i+=2,j++) {
            int i1 = i-1;
            int i2 = i+1;
            matrix[j][0] = Data.z.get(i-1)/
                    (Data.k.get(i-1)*(Data.z.get(i)-Data.z.get(i-2)));
            matrix[j][2] = Data.z.get(i+1)/
                    (Data.k.get(i+1)*(Data.z.get(i+2) - Data.z.get(i)));
            matrix[j][1] = -matrix[j][0] - matrix[j][2] -
                    3*Data.R2*Data.k.get(i)*Data.z.get(i)*
                            (Data.z.get(i+1) - Data.z.get(i-1));
            matrix[j][3] = -3*Data.R2*Data.k.get(i)*Up(Data.T.get(i))*
                    Data.z.get(i)*(Data.z.get(i+1)-Data.z.get(i-1));
        }
        matrix[Data.n-1][1] = - tildaP(Data.x.get(n))/
                (3*Data.k.get(n)*Data.R*Data.h);
        matrix[Data.n-1][2] = -matrix[Data.n-1][1] + Data.m/2.;
        return matrix;
    }

    private void prepareData() {
        Data.a = Math.PI/2. - Data.sigma;
        Data.a1 = 1/Data.a;
        Data.ymax = Math.tan(Data.a);

        Data.R2 = Data.R*Data.R;
        Data.h2 = Data.h/2;

        Data.n = (int)Math.floor((Data.xN-Data.x0)/Data.h+0.5) + 1;
        Data.x = new ArrayList<>();
        Data.z = new ArrayList<>();
        Data.T = new ArrayList<>();
        Data.k = new ArrayList<>();

        int n = (Data.n-1)*2;
        for (int i = 0; i < n; i++) {
            Data.x.add(Data.x0+i*Data.h2);
            Data.z.add(z(Data.x.get(i)));
            Data.T.add(T(Data.z.get(i)));
            Data.k.add(k(Data.T.get(i)));
        }
        Data.x.add(Data.x0+n*Data.h2);
        Data.z.add(z(Data.x.get(n)));
        Data.T.add(T(Data.z.get(n)));
        Data.k.add(k(Data.k.get(n)));
    }

    private double z(double x) {
        return Data.a1*Math.atan(x*Data.ymax);
    }

    private double T(double z) {
        return Data.T0 + (Data.Tw- Data.T0)*Math.pow(z, Data.p);
    }

    private double k(double T) {
        return Data.k0*Math.pow(T/2000.,2);
    }

    private double Up(double T) {
        return 6.1679*1e-19*Math.pow(Data.nu,3)/
                (Math.exp(4.799*1e4*Data.nu/T)-1.);
    }

    private double tildaP(double x) {
        return Data.a/Data.ymax*(1 + x*x*Data.ymax*Data.ymax);
    }
}
