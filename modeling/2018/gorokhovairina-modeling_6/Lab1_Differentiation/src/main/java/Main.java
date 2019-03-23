/*
u'(x) = x*x + u*u
u(0) = 0
- Пикар 3
- Пикар 4
- Пикар ...
- Метод ломанных (явный)
- Метод ломанных (неявный)
- Рунге-Кутт 2
- Рунге-Кутт 4


 */

public class Main {
    public static void main(String[] args) throws Exception {
        double a = 0;
        double h = 0.01;
        int n = 101;

        Pikar p3 = new Pikar(3);
        Pikar p4 = new Pikar(4);
        Pikar p5 = new Pikar(5);
        Pikar p6 = new Pikar(6);

        Euler eu = new Euler(h, n, a);

        RungeKutt rk4 = new RungeKutt(4, a, h, n);
        RungeKutt rk2 = new RungeKutt(2, a, h, n);

        System.out.printf("%10s | %12s | %12s | %12s | %12s | %12s | %12s | %12s | %12s \n",
                "X", "Pikar(3)", "Pikar(4)", "Pikar(5)","Pikar(6)", "Euler(imp)", "Euler(exp)", "RungeKutt(2)", "RungeKutt(4)");
        for (int i = 0; i < 140; i++) System.out.print("-");
        System.out.print("\n");

        for (int i = 0; i < n; ++i) {
            System.out.printf("%10.4f | %12.3e | %12.3e | %12.3e | %12.3e | %12.3e | %12.3e | %12.5e | %12.3e \n",a,
                    p3.getResult(a),p4.getResult(a),p5.getResult(a),p6.getResult(a),
                    eu.getResult(a, true), eu.getResult(a, false),
                    rk2.getResult(a), rk4.getResult(a));
            a+=h;
        }
    }
}
