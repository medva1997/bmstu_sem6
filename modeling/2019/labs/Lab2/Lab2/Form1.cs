using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.DataVisualization.Charting;
using System.Xml.Serialization;

namespace Lab2
{
    public partial class Form1 : Form
    {
        private double _R; //радиус трубки, фигурирует в верхней границе интеграла
        private double _Tw;
        private double _Ck; //емкость конденсатора
        private double _Lk; //индуктивность
        private double _Rk; //сопротивление
        private double _U0; //напряжение на конденсаторе в начальный момент времени t = 0
        private double _I0; //сила тока в цепи в начальный момент времени t = 0
        private double _Le; //расстояние между электродами лампы

        private double _T0;
        private double _m;

        private double _Rp;
        //static double p0 = 0.5;
        //static double Tstart = 300;    //p0 и Tstart используются в уравнении для нахождения давления p


        static List<double[]> table_I_T_m;
        static List<double[]> table_o;
        static double[,] table_I_T_m_log;
        static double[,] table_o_log;

        static double[,] table_I_T_m_notlog;
        static double[,] table_o_notlog;

        private static double TOLERANCE = 0.000001;

        public Form1()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Линейная интерполяция  https://ru.wikipedia.org/wiki/%D0%9B%D0%B8%D0%BD%D0%B5%D0%B9%D0%BD%D0%B0%D1%8F_%D0%B8%D0%BD%D1%82%D0%B5%D1%80%D0%BF%D0%BE%D0%BB%D1%8F%D1%86%D0%B8%D1%8F
        /// </summary>
        /// <param name="table">Таблица значений x f(x)</param>
        /// <param name="x">Значение x для которого мы хотим найти f(x)</param>
        /// <param name="xColIndex">Номер столбца с иксами</param>
        /// <param name="fColIndex">Номер столбца с f(x)</param>
        /// <returns></returns>
        static double LinealInterpolation(double[,] table, double x,  int xColIndex=0,int fColIndex=1)
        {
     
            double fx0, fx1, x0, x1;
            int n = table.GetLength(0);

            if (x < table[0, xColIndex])
            {
                x0 = table[0, xColIndex];
                x1 = table[1, xColIndex];
                fx0 = table[0, fColIndex];
                fx1 = table[1, fColIndex];
            }
            else if (x > table[n - 1, xColIndex])
            {
                x0 = table[n - 2, xColIndex];
                x1 = table[n - 1, xColIndex];
                fx0 = table[n - 2, fColIndex];
                fx1 = table[n - 1, fColIndex];
            }
            else
            {
                int i = 0;
                while (x >= table[i, xColIndex])
                {
                    if (Math.Abs(x - table[i, xColIndex]) < TOLERANCE)
                        return table[i, fColIndex];
                    i++;
                }

                x0 = table[i - 1, xColIndex];
                x1 = table[i, xColIndex];
                fx0 = table[i - 1, fColIndex];
                fx1 = table[i, fColIndex];
            }

            return fx0 + (fx1 - fx0) * (x - x0) / (x1 - x0);
        }


        static double LinealInterpolationLOGGGGGGG(double[,] table, double x, int xColIndex = 0, int fColIndex = 1)
        {
            x = Math.Log(x);
            double fx0, fx1, x0, x1;
            int n = table.GetLength(0);

            if (x < table[0, xColIndex])
            {
                x0 = table[0, xColIndex];
                x1 = table[1, xColIndex];
                fx0 = table[0, fColIndex];
                fx1 = table[1, fColIndex];
            }
            else if (x > table[n - 1, xColIndex])
            {
                x0 = table[n - 2, xColIndex];
                x1 = table[n - 1, xColIndex];
                fx0 = table[n - 2, fColIndex];
                fx1 = table[n - 1, fColIndex];
            }
            else
            {
                int i = 0;
                while (x >= table[i, xColIndex])
                {
                    if (Math.Abs(x - table[i, xColIndex]) < TOLERANCE)
                        return table[i, fColIndex];
                    i++;
                }

                x0 = table[i - 1, xColIndex];
                x1 = table[i, xColIndex];
                fx0 = table[i - 1, fColIndex];
                fx1 = table[i, fColIndex];
            }
            var res= Math.Exp(fx0 + (fx1 - fx0) * (x - x0) / (x1 - x0));
            return res;
        }


        private void FileReader(ref List<double[]> lst, string file)
        {
            lst = new List<double[]>();

            using (StreamReader fs = new StreamReader(file))
            {
                while (true)
                {
                    // Читаем строку из файла во временную переменную.
                    string temp = fs.ReadLine();

                    // Если достигнут конец файла, прерываем считывание.
                    if (temp == null) break;

                    // Пишем считанную строку в итоговую переменную.

                    var arr = temp.Split('\t');
                    double[] tmp = new double[arr.Length];
                    for (int i = 0; i < arr.Length; i++)
                    {
                        tmp[i] = Convert.ToDouble(arr[i].Replace('.', ','));
                    }

                    lst.Add(tmp);
                }
            }
        }

        private static double[,] LogTable(List<double[]> lst)
        {
            int m = lst.Count;
            int n = lst[0].Length;
            double[,] logTable = new double[m, n];
            for (int i = 0; i < m; ++i)
            {
                for (int j = 0; j < n; ++j)
                {
                
                  logTable[i, j] = Math.Log(lst[i][j]);
                }
            }

            return logTable;
        }

        private static double[,] CopyArray(List<double[]> lst)
        {
            int m = lst.Count;
            int n = lst[0].Length;
            double[,] logTable = new double[m, n];
            for (int i = 0; i < m; ++i)
            {
                for (int j = 0; j < n; ++j)
                {
                  logTable[i, j] = lst[i][j];
                }
            }

            return logTable;
        }

        private void buttonGO_Click(object sender, EventArgs e)
        {
            _R = Convert.ToDouble(textBoxR.Text.Replace('.', ','));
            _Tw = Convert.ToDouble(textBoxTw.Text.Replace('.', ','));
            _Ck = Convert.ToDouble(textBoxCk.Text.Replace('.', ',')) * Math.Pow(10,-6);
            _Lk = Convert.ToDouble(textBoxLk.Text.Replace('.', ',')) * Math.Pow(10, -6);
            _Rk = Convert.ToDouble(textBoxRk.Text.Replace('.', ','));
            _U0 = Convert.ToDouble(textBoxU0.Text.Replace('.', ','));
            _I0 = Convert.ToDouble(textBoxI0.Text.Replace('.', ','));
           
            _Le = Convert.ToDouble(textBoxLe.Text.Replace('.', ','));

            FileReader(ref table_I_T_m,
                @"C:\Users\medva.LAPTOP-U0TLEODB\Desktop\bmstu_sem6\modeling\2019\labs\Lab2\Lab2\tab_I_T0_m.txt");
            FileReader(ref table_o,
                @"C:\Users\medva.LAPTOP-U0TLEODB\Desktop\bmstu_sem6\modeling\2019\labs\Lab2\Lab2\tab_T_d.txt");

            table_o_log = LogTable(table_o);
            table_I_T_m_log = LogTable(table_I_T_m);
            table_o_notlog = CopyArray(table_o);
            table_I_T_m_notlog = CopyArray(table_I_T_m);

            //Ищем из первой тблицы T0 и m
            _T0 = LinealInterpolation(table_I_T_m_notlog, _I0);
            _m = LinealInterpolation(table_I_T_m_notlog, _I0,0,2);

            // Integring(Integral, 0, 1);
            //return;
            //Формула 2

            this.chart1.Series.Clear();
            this.chart2.Series.Clear();
            this.chart3.Series.Clear();
            this.chart4.Series.Clear();

            
            Series series = this.chart1.Series.Add("Total Income");
            series.ChartType = SeriesChartType.Spline;

            Series series2 = this.chart2.Series.Add("dI");
            series2.ChartType = SeriesChartType.Spline;
            Series series3 = this.chart3.Series.Add("dU");
            series3.ChartType = SeriesChartType.Spline;
            Series series4 = this.chart4.Series.Add("Rp");
            series4.ChartType = SeriesChartType.Spline;

            double curentU = _U0;
            double curentI = _I0;
            _Rp = Integring(Integral, 0, 1);


            for (double t = 0; t < 0.0003; t+=0.000001)
            {
                _I0 = curentI;
                _U0 = curentU;
                _T0 = LinealInterpolation(table_I_T_m_notlog, _I0);
                _m = LinealInterpolation(table_I_T_m_notlog, _I0, 0, 2);
                var tttt = Integring(Integral, 0, 1);
                _Rp = _Le / (2 * Math.PI * _R * _R * tttt);
                series.Points.AddXY(t, _Rp);
                Runge4(t, curentI, curentU, funcI, funcU, out curentI, out curentU);
                series2.Points.AddXY(t, curentI);
                series3.Points.AddXY(t, curentU);
                series4.Points.AddXY(t, curentI *_Rp);
         
            }

        }



        public double funcI(double t, double i, double u)
        {
            return (u - (_Rp + _Rk) * i) / _Lk;
        }

        public double funcU(double t, double i, double u)
        {
            return - i / _Ck;
        }

        private  void Runge4(double x, double y, double z, Func<double, double, double, double> function,
                                        Func<double, double, double, double> function2,out double k, out double m)
        {
            double H = Math.Pow(10,-6);
            double k1 = function(x, y,z);
            double m1 = function2(x, y,z);

            _I0 = y + H * k1 / 2;
            _U0 = z + H * m1 / 2;
            _T0 = LinealInterpolation(table_I_T_m_notlog, _I0);
            _m = LinealInterpolation(table_I_T_m_notlog, _I0, 0, 2);
            var tttt = Integring(Integral, 0, 1);
            _Rp = _Le / (2 * Math.PI * _R * _R * tttt);

            double k2 = function(x + H / 2, y + H * k1 / 2, z + H * m1 / 2);
            double m2 = function2(x + H / 2, y + H * k2 / 2, z + H * m1 / 2);

            _I0 = y + H * k2 / 2;
            _U0 = z + H * m2 / 2;
            _T0 = LinealInterpolation(table_I_T_m_notlog, _I0);
            _m = LinealInterpolation(table_I_T_m_notlog, _I0, 0, 2);
            tttt = Integring(Integral, 0, 1);
            _Rp = _Le / (2 * Math.PI * _R * _R * tttt);


            double k3 = function(x + H / 2, y + H * k2 / 2, z + H * m2 / 2);
            double m3 = function2(x + H / 2, y + H * k2 / 2, z + H * m2 / 2);

            _I0 = y + H * k3;
            _U0 = z + H * m3;
            _T0 = LinealInterpolation(table_I_T_m_notlog, _I0);
            _m = LinealInterpolation(table_I_T_m_notlog, _I0, 0, 2);

            tttt = Integring(Integral, 0, 1);
            _Rp = _Le / (2 * Math.PI * _R * _R * tttt);

            double k4 = function(x + H, y + H * k3, z + H * m3);
            double m4 = function2(x + H, y + H * k3, z + H * m3);

            k = y + H / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
            m = z + H / 6 * (m1 + 2 * m2 + 2 * m3 + m4);
        }



        public double Integral(double z)
        {
           var tmp= LinealInterpolationLOGGGGGGG(table_o_log, GetTZ(z))*z;
           return tmp;
        }

        public double Integring(Func<double,double> f, double a, double b) //Интегрирование методом трапеций (аргументы: экземпляр делегата интегрируемой ф-ции, нижний предел интегрирования, верхний предел)
        {
            double I = 0;
            string str = "";
            double H = (b - a) / 40;

            for (double i = a; i < b; i = i + H )
            {
                var test= ((f(i) + f(i + H)) / 2) *((i + H) - i);

                I += test;
               str += f(i)+" \n";
            }

            //MessageBox.Show(str);
            
            return I;
        }
        private double sympon(double a, double b)
        {
            int n = 41;
            double h = (b - a) / (n - 1);
            double integr = 0;
            for (int i = 0; i < n; ++i)
            {
                double vall = LinealInterpolation(table_I_T_m_log, GetTZ(a))*a;
                double valm = 4*LinealInterpolation(table_I_T_m_log, GetTZ((a + h + a)/2));
                double valr = LinealInterpolation(table_I_T_m_log, GetTZ(a+h));

                var res= (a + h + a) / 6 * (vall + valm + valr);
                if (!double.IsNaN(res) && !double.IsInfinity(res))
                {
                    integr += res;

                }
                
                a += h;
            }

            return integr;
        }

        private double GetTZ(double Z)
        {
            return _T0 + (_Tw - _T0) * Math.Pow(Z, _m);
        }

    }
}