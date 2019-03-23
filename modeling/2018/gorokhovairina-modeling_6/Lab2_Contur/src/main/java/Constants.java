public class Constants {
    static double Rk = 0.2;        //сопротивление
    static double Lk = 60e-6;      //индуктивность
    static double Ck = 150e-6;     //емкость конденсатора
    static double R = 0.35;        //радиус трубки, фигурирует в верхней границе интеграла
    static double p0 = 0.5;
    static double Tstart = 300;    //p0 и Tstart используются в уравнении для нахождения давления p
    static double Tw = 2000;
    static double le = 12;         //расстояние между электродами лампы
    static double Uc0 = 1500;      //напряжение на конденсаторе в начальный момент времени t = 0
    static double I0 = 0;          //сила тока в цепи в начальный момент времени t = 0

    static double table_n[][];
    static double table_n_log[][];
    static double table_o[][];
    static double table_o_log[][];
    static double table_I_T_n[][];
}
