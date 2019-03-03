using System;
using System.Security.Cryptography.X509Certificates;
using static System.Console;

namespace ModLaba1
{
    class Program
    {
        /// <summary>
        /// Чудо функция
        /// </summary>
        /// <param name="x"></param>
        /// <param name="u"></param>
        /// <returns></returns>
        static double Function(double x, double u)
        {
            return Math.Pow(x, 2) + Math.Pow(u, 2);
        }

        static double Picar3(double x)
        {
            double y = Math.Pow(x, 3)/3;
            y += Math.Pow(x, 7) / 63;
            y += 2 * Math.Pow(x, 11) / (3 * 693);
            y += Math.Pow(x, 15) / (3 * 19845);
            return y;
        }

        static double Picar4(double x)
        {
            double y=Math.Pow(x, 31)/109876902975;
            y+=4*Math.Pow(x, 27)/3341878155;
            y += 662 * Math.Pow(x, 23) / 10438212015;
            y += 82 * Math.Pow(x, 19) / 37328445;
            y += 13 * Math.Pow(x, 15) / 218295;
            y += 2 * Math.Pow(x, 11) / 2079;
            y += Math.Pow(x, 7) / 63;
            y += Math.Pow(x, 3) / 3;
            return y;
        }

        
        /// <summary>
        /// Метод ломанных (явный).
        /// </summary>
        /// <param name="x"></param>
        /// <param name="y"></param>
        /// <returns></returns>
        private static double LomExplicit(double x, double y)
        {
            return (y+H*Function(x,y));
        }

        private static double Runge2(double x, double y)
        {
            return y + H * Function(x + H / 2, y + H / 2 * Function(x, y));
        }
        
        private static double Runge4(double x, double y)
        {
            double K1 = Function(x, y);
            double K2 = Function(x + H / 2, y + H * K1 / 2);
            double K3 = Function(x + H / 2, y + H * K2 / 2);
            double K4 = Function(x + H, y + H * K3);
            
            return y + H / 6 * (K1 + 2 * K2 + 2 * K3 + K4);
        }

        private static double H=0.01;
        static void Main(string[] args)
        {
            // Границы
            double x = 0.0;
            const double maxX = 2.0;

            double loma = 0.0;

            double run2 = 0.0;
            double run4 = 0.0;

            WriteLine("X\t\t|Пикар3 \t|Пикар4 \t|Метод ломанных\t|Рунге-Кутта \t|Рунге-Кутта \t|");
            WriteLine("\t\t| \t\t| \t\t|явный \t\t|2-го порядка \t|4-го порядка \t|");
            WriteLine("-----------------------------------------------------------------------------------------");
            while (x <= maxX)
            {
                WriteLine($"{x:F5} \t| {Picar3(x):F5} \t| {Picar4(x):F5} \t| {loma:F5} \t| {run2:F5} \t| {run4:F5} \t|");
                loma = LomExplicit(x, loma);
                run2 = Runge2(x, run2);
                run4 = Runge4(x, run4);
                x += H;
            }
            WriteLine($"{x:F5} \t| {Picar3(x):F5} \t| {Picar4(x):F5} \t| {loma:F5} \t| {run2:F5} \t| {run4:F5} \t|");
            ReadKey();
          
        }

        
    }
}