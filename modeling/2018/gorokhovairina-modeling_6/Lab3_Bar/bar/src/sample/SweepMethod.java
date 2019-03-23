package sample;


public class SweepMethod {
    private float k0, m0, n0;
    private float kN, mN, nN;
    private float[] aN, bN, cN,dN;
    private float[] yN;
    private float a,b;
    private int MAX_ITERATIONS = 100;

    public SweepMethod(int n) {
        b = Constants.barL*Constants.alphaN/(Constants.alphaN-Constants.alpha0);
        a = -Constants.alpha0*b;

        solve(n);
    }

    private void solve(int n) {
        int k = 0;

        float h = Constants.barL/n;
        float quadH = h*h;
        float[] array_T = new float[n+1];
        float[] array_Tprev = new float[n+1];

        for (int i = 0; i < n+1; i++) {
            array_T[i] = Constants.tempEnv;
        }

        aN = new float[n+1];
        bN = new float[n+1];
        cN = new float[n+1];
        dN = new float[n+1];

        do {
            k0 = xi1_2(array_T[0], array_T[1]) + quadH/8 *p1_2(0.0f,h)+ quadH/4*p(0.0f);
            m0 = - (xi1_2(array_T[0], array_T[1]) - quadH/8*p1_2(0.0f,h));
            n0 = h*Constants.F0 + quadH/4*(f1_2(0.0f,h) + f(0.0f));

            for (int i = 1; i < n; i++) {
                aN[i] = xi1_2(array_T[i], array_T[i-1]);
                cN[i] = xi1_2(array_T[i+1], array_T[i]);
                bN[i] = (aN[i] + cN[i] + p(i*h)*quadH);
                dN[i] = f(i*h)*quadH;
            }

            kN = xi1_2(array_T[n], array_T[n-1]) - quadH/8*p1_2(Constants.barL, Constants.barL-h);
            mN = -(xi1_2(array_T[n], array_T[n-1]) + p(Constants.barL)*quadH/4 + quadH/16*p1_2(Constants.barL, Constants.barL-h));
            nN = -(h*alpha(Constants.barL)*(array_T[n]-Constants.tempEnv) + quadH/4*(f1_2(Constants.barL, Constants.barL-h) + f(Constants.barL)));

            array_Tprev = array_T;
            array_T = sweep();
            k++;

        }
        while (check(array_Tprev, array_T) && k <= MAX_ITERATIONS);

        for (int i = 0; i < n+1; i++) {
            yN = array_T;
        }
    }

    private boolean check(float[] current, float[] next) {
        int size = Math.min(current.length, next.length);
        float max = Math.abs((current[0]-next[0])/current[0]);
        for (int i = 0; i < size; i++) {
            float tmp = Math.abs((current[i]-next[i])/current[i]);
            if (tmp > max)
                max = tmp;
        }
        return (max >= 10e-3);
    }

    private float[] sweep() {
        int n = aN.length;

        float[] ksi = new float[n];
        float[] eta = new float[n];

        ksi[0] = -m0/k0;
        eta[0] = n0/k0;

        for (int i = 1; i < n-1; i++) {
            float znam = bN[i] - aN[i]*ksi[i-1];
            ksi[i] = cN[i]/znam;
            eta[i] = (dN[i]+aN[i]*eta[i-1])/znam;
        }

        float[] res = new float[n];
        res[n-1] = (nN - mN*eta[n-2])/(kN + mN*ksi[n-2]);
        for (int i = n-2; i >= 0; i--)
            res[i] = ksi[i]*res[i+1] + eta[i];

        return res;
    }

    public float[] getYn() {
        return yN;
    }

    private float alpha(float x) {
        return a/(x-b);
    }

    private float p(float x) {
        return 2* alpha(x)/Constants.barR;
    }

    private float p1_2(float x1, float x2) {
        return p((x1+x2)/2);
    }

    private float f(float x) {
        return 2* alpha(x)*Constants.tempEnv/Constants.barR;
    }

    private float f1_2(float x1, float x2) {
        return f((x1+x2)/2);
    }

    private float lambda(float T) {
        return (float)(Constants.k0*Math.pow(T/Constants.teta, Constants.p));
    }

    private float xi1_2(float T1, float T2) {
        return 2*lambda(T2)*lambda(T1)/(lambda(T2)+lambda(T1));
    }

}
