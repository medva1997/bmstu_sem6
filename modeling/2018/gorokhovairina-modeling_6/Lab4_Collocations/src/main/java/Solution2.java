public class Solution2 {

    public Solution2() {
        int n = (Data.n-1)*2;
        Data.w = Data.m*0.5*3*Data.R*Data.k.get(n)/tildaP(Data.x.get(n));
        double dots[] = new double[10];
        for (int i= 0; i < dots.length; i++)
            dots[i] = Data.x.get(0) + i*1/11.;

        double[][] matrix = fillMatrix(dots);
    }

    private double[][] fillMatrix(double[] dots) {
        int n = dots.length;
        double[][] matrix = new double[n][n+1];
        for (int i = 0; i < n; i++)
            fillRow(matrix[i], dots[i], n);
    }

    private void fillRow(double[] row, double x, int n) {
        Data.zz = z(x);
        Data.TT = T(Data.zz);
        Data.kk = k(Data.TT);
        Data.pp = tildaP(x);
        Data.fi = Data.pp*((1 + Data.zz*2*Data.a*Data.ymax*x)* Data.kk
                - Data.zz*Data.p*2*Data.k0/(4*1e6)*Data.TT*(Data.Tw-Data.T0))/
                (3.*Data.R2*Data.zz*Data.kk*Data.kk);
    }

    private double tildaP(double x) {
        return Data.a/Data.ymax*(1 + x*x*Data.ymax*Data.ymax);
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

    private double[] gauss(double[][] matrix1, int n) {
        double[] answ = new double[n];
        double[][] matrix = matrix1;
        for (int i = 0; i < n; i++) {
            int j = 0;
            while (matrix[i][j] == 0) {
                j++;
                if (j > n)
                    return answ;
            }

            answ[j] = i;
            for (int k = 0; k < n; k++) {
                if (k == i)
                    continue;

                double t = matrix[k][j] / matrix[i][j];
                matrix[k][j] = 0;
                for (int l = j+1; l <= n; l++)
                    matrix[k][l] -= matrix[i][l]*t;
            }
        }

        for (int i = 0; i < n; i++)
            answ[i] = matrix[(int) answ[i]][n]/matrix[(int) answ[i]][i];

        return answ;
    }

}
