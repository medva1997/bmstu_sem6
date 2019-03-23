package sample;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.random;

public class Algorithm {
    private float k0, m0, n0;
    private float kN, mN, nN;
    private float[] aN, bN, dN, fN;
    private List<Float[]> yN;
    private float a,b;
    private int MAX_ITERATIONS = 100;
    private float tau = 1;

    public List<Float[]> getyN() {
        return yN;
    }

    public Algorithm(int n) {
        b = Constants.barL*Constants.alphaN/(Constants.alphaN-Constants.alpha0);
        a = -Constants.alpha0*b;
        yN = new ArrayList<Float[]>();

        solve(n);
    }

    private void solve(int n) {
        int k = 0;

        float h = Constants.barL/n;
        float quadH = h*h;
        Float[] array_T = new Float[n+1];
        Float[] array_Tprev;

        for (int i = 0; i < n+1; i++) {
            array_T[i] = Constants.tempEnv;
        }
        yN.add(array_T);

        aN = new float[n+1];
        bN = new float[n+1];
        dN = new float[n+1];
        fN = new float[n+1];

        do {
            do {

                float y0 = yN.get(yN.size()-1)[0];
                float y1 = yN.get(yN.size()-1)[1];

                k0 = tau * (xi1_2(array_T[0], array_T[1]) + p1_2(0.0f, h) * quadH / 8 + p(0.0f) * quadH / 4) + c(array_T[0]) * quadH / 4 + c1_2(array_T[0], array_T[1]) * quadH / 8;
                m0 = tau * (p1_2(0.0f, h) * quadH / 8 - xi1_2(array_T[0], array_T[1])) + c1_2(array_T[0], array_T[1]) * quadH / 8;
                n0 = tau * Constants.F0*h + tau * quadH / 8 * (3 * f(0.0f) + f(h)) + c(array_T[0])*quadH/4*y0 + c1_2(array_T[0], array_T[1])*quadH/8*(y0+y1);

                for (int i = 1; i < n; i++) {
                    aN[i] = tau*xi1_2(array_T[i], array_T[i - 1]);
                    dN[i] = tau*xi1_2(array_T[i + 1], array_T[i]);
                    bN[i] = aN[i] + dN[i] + p(i * h)*quadH*tau + c(array_T[i])*quadH;
                    fN[i] = f(i * h) * quadH*tau + c(array_T[i])*quadH*yN.get(yN.size()-1)[i];
                }

                float yn_1 = yN.get(yN.size()-1)[n-1];
                float yn = yN.get(yN.size()-1)[n];

                kN = -tau*(xi1_2(array_T[n], array_T[n - 1]) + alpha(Constants.barL)*h + p(Constants.barL)*quadH/4 + p1_2(Constants.barL, Constants.barL-h)*quadH/8)
                        + c1_2(array_T[n], array_T[n-1])*quadH/8 + c(array_T[n])*quadH/8;
                mN = tau*(xi1_2(array_T[n], array_T[n - 1]) - p1_2(Constants.barL, Constants.barL - h)*quadH/8) + c1_2(array_T[n], array_T[n-1])*quadH/8;
                nN = -alpha(Constants.barL)*Constants.tempEnv*tau*h - tau*quadH/8*(3*f(Constants.barL)+f(Constants.barL-h)) + c(array_T[n])*quadH/4*yn +
                        c1_2(array_T[n], array_T[n-1])*quadH/8*(yn+yn_1);

                array_Tprev = array_T;
                //array_T = sweep();
                k++;

            }
            while (check(array_Tprev, array_T) && k <= MAX_ITERATIONS);

            array_T = crutch(array_T);
            yN.add(array_T);

        }while (check(yN.get(yN.size()-2), yN.get(yN.size()-1)) && k <= MAX_ITERATIONS);
    }

    private boolean check(Float[] current, Float[] next) {
        int size = Math.min(current.length, next.length);
        float max = Math.abs((current[0]-next[0])/current[0]);
        for (int i = 0; i < size; i++) {
            float tmp = Math.abs((current[i]-next[i])/current[i]);
            if (tmp > max)
                max = tmp;
        }
        return (max >= 10e-4);
    }

    private Float[] crutch(Float[] arr) {
        int min = min(arr);
        int max = max(arr);
        for (int i = 1; i < arr.length; i++) {
            if (Constants.F0 > 0 && i > min)
                arr[i] = (float)(Constants.tempEnv + random()*0.1);
            if (Constants.F0 < 0 && i > max)
                arr[i] = (float)(Constants.tempEnv+random()*0.1);
        }
        return arr;
    }

    private int min(Float[] arr) {
        int min = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[min] && arr[i] > Constants.tempEnv)
                min = i;
        }
        return min;
    }

    private int max(Float[] arr) {
        int max = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[max])
                max = i;
        }
        return max;
    }

    private Float[] sweep() {
        int n = aN.length;

        float[] ksi = new float[n];
        float[] eta = new float[n];

        ksi[0] = -m0/k0;
        eta[0] = n0/k0;

        for (int i = 1; i < n-1; i++) {
            float znam = bN[i] - aN[i]*ksi[i-1];
            ksi[i] = dN[i]/znam;
            eta[i] = (fN[i]+aN[i]*eta[i-1])/znam;
        }

        Float[] res = new Float[n];
        res[n-1] = (nN - mN*eta[n-2])/(kN + mN*ksi[n-2]);
        for (int i = n-2; i >= 0; i--)
            res[i] = ksi[i]*res[i+1] + eta[i];

        return res;
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
        return interpolation(0,2,T);
    }

    private float xi1_2(float T1, float T2) {
        return (lambda(T2)+lambda(T1))/2;
    }

    private float c(float T) {
        return interpolation(0,1,T);
    }

    private float c1_2(float T1, float T2) {
        float c1 = interpolation(0,1,T1);
        float c2 = interpolation(0,1,T2);
        return (c1+c2)/2;
    }

    private float interpolation(int ind1, int ind2, float ind1Data) {
        for (int i = 0; i < Constants.TCk.length; i++) {
            if (abs(ind1Data-Constants.TCk[i][ind1])< 1e-3)
                return Constants.TCk[i][ind2];
        }

        int i = 0, i1, i2;
        while (ind1Data > Constants.TCk[i][ind1]) {
            if (i > Constants.TCk.length-2)
                break;
            i++;
        }
        if (i==0){i1 = 0; i2=1;}
        else {i1 = i-1; i2= i; }
        float res = Constants.TCk[i1][ind2] + (Constants.TCk[i2][ind2]-Constants.TCk[i1][ind2])/
                (Constants.TCk[i2][ind1]-Constants.TCk[i1][ind1])*(ind1Data-Constants.TCk[i1][ind1]);
        return res;
    }
}


