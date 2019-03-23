import java.util.ArrayList;

public class Differential extends Constants {
    private static double I;
    private static double U;
    private static double t;
    private static double Rp;

    private static ArrayList<Double> resI = new ArrayList<Double>();
    private static ArrayList<Double> resU = new ArrayList<Double>();
    private static ArrayList<Double> resRp = new ArrayList<Double>();

    public static void RungeKutta(double h, double n) {
        I = I0;
        U = Uc0;
        t = h;
        double k1, k2, k3, k4;
        double m1, m2, m3, m4;

        for (int i = 0; i < n; i++) {
            k1 = F_I_Uc(I, U);
            m1 = G_I(I);
            k2 = F_I_Uc(I + h / 2 * k1, U + h / 2 * m1);
            m2 = G_I(I + h / 2 * k1);
            k3 = F_I_Uc(I + h / 2 * k2, U + h / 2 * m2);
            m3 = G_I(I + h / 2 * k2);
            k4 = F_I_Uc(I + h * k3, U + h * m3);
            m4 = G_I(I + h * k3);

            I += h / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
            resI.add(I);

            resU.add(U);
            U += h / 6 * (m1 + 2 * m2 + 2 * m3 + m4);

            GetInfoString(i);
        }
    }

    private static double F_I_Uc(double I, double Uc) {
        try {
            Rp = RpSearch.GetRp(I);
            resRp.add(Rp);
            return (Uc - (Rk + Rp) * I) / Lk;
        }
        catch (Exception e) {
            GetGraphFormat();

            //hardcore mode on
            //im sorry

            System.exit(0);
            return 0;
        }
    }
    private static double G_I(double I) {
        return -I/Ck;
    }

    private static void GetInfoString(int n) {
        System.out.printf("%8.2e | %10.3f | %10.3f | %10.3f\n", t*n, I, U, Rp);
    }

    private static void GetGraphFormat() {
        for (int i = 0; i < resI.size(); i++)
            System.out.printf("(%.5f;%.3f)\n", t*i, (resI.get(i)));

        //System.out.print("\n\n");
        //for (int i = 0; i < resU.size(); i++)
        //    System.out.printf("(%.5f;%.3f)\n", t*i, (resU.get(i)));

        System.out.print("\n\n");
        for (int i = 0; i < resRp.size(); i++)
            System.out.printf("(%.5f;%.3f)\n", t*i, (resRp.get(i)));
    }
}
