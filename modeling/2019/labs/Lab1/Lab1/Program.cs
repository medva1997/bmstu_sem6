using System;


namespace Lab1
{
    class Program
    {
        private const double H = 0.001;

        /// <summary>
        /// Чудо функция1
        /// </summary>
        /// <param name="x"></param>
        /// <param name="u"></param>
        /// <returns></returns>
        private static double Function1(double x, double u)
        {
            return x + u;
        }
        /// <summary>
        /// Чудо функция2
        /// </summary>
        /// <param name="x"></param>
        /// <param name="u"></param>
        /// <returns></returns>
        private static double Function2(double x, double u)
        {
            return Math.Pow(x, 2) + Math.Pow(u, 2);
        }


        private static double Picar3(double x, Func<double, double, double> function)
        {
            if (function == Function1)
            {
                double y = Math.Pow(x, 2) / 2;
                y += Math.Pow(x, 3) / 6;
                y += Math.Pow(x, 4) / 24;
                return y;
            }
            else
            {
                double y = Math.Pow(x, 3)/3;
                y += Math.Pow(x, 7) / 63;
                y += 2 * Math.Pow(x, 11) / (3 * 693);
                y += Math.Pow(x, 15) / (3 * 19845);
                return y;
            }
            
        }
        
        
        
        
        static double Picar4(double x, Func<double, double, double> function)
        {
            if (function == Function1)
            {
                double y = Math.Pow(x, 2) / 2;
                y += Math.Pow(x, 3) / 6;
                y += Math.Pow(x, 4) / 24;
                y += Math.Pow(x, 5) / (5*24);
                return y;
            }
            else
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
            
        }
        
        static double Picar5(double x, Func<double, double, double> function)
        {
            if (function == Function1)
            {
                double y = Math.Pow(x, 2) / 2;
                y += Math.Pow(x, 3) / 6;
                y += Math.Pow(x, 4) / 24;
                y += Math.Pow(x, 5) / (5*24);
                y += Math.Pow(x, 6) / (6*5*24);
                return y;
            }

            return 0;

        }
        static double Picar6(double x, Func<double, double, double> function)
        {
            if (function == Function1)
            {
                double y = Math.Pow(x, 2) / 2;
                y += Math.Pow(x, 3) / 6;
                y += Math.Pow(x, 4) / 24;
                y += Math.Pow(x, 5) / (5*24);
                y += Math.Pow(x, 6) / (6*5*24);
                y += Math.Pow(x, 7) / (7*6*5*24);
                return y;
            }

            return 0;

        }
        static double Picar7(double x, Func<double, double, double> function)
        {
            if (function == Function1)
            {
                double y = Math.Pow(x, 2) / 2;
                y += Math.Pow(x, 3) / 6;
                y += Math.Pow(x, 4) / 24;
                y += Math.Pow(x, 5) / (5*24);
                y += Math.Pow(x, 6) / (6*5*24);
                y += Math.Pow(x, 7) / (7*6*5*24);
                y += Math.Pow(x, 8) / (8*7*6*5*24);
                return y;
            }

            return 0;

        }
        static double Picar8(double x, Func<double, double, double> function)
        {
            if (function == Function1)
            {
                double y = Math.Pow(x, 2) / 2;
                y += Math.Pow(x, 3) / 6;
                y += Math.Pow(x, 4) / 24;
                y += Math.Pow(x, 5) / (5*24);
                y += Math.Pow(x, 6) / (6*5*24);
                y += Math.Pow(x, 7) / (7*6*5*24);
                y += Math.Pow(x, 8) / (8*7*6*5*24);
                y += Math.Pow(x, 9) / (9*8*7*6*5*24);
                return y;
            }

            return 0;

        }
        static double Picar9(double x, Func<double, double, double> function)
        {
            if (function == Function1)
            {
                double y = Math.Pow(x, 2) / 2;
                y += Math.Pow(x, 3) / 6;
                y += Math.Pow(x, 4) / 24;
                y += Math.Pow(x, 5) / (5*24);
                y += Math.Pow(x, 6) / (6*5*24);
                y += Math.Pow(x, 7) / (7*6*5*24);
                y += Math.Pow(x, 8) / (8*7*6*5*24);
                y += Math.Pow(x, 9) / (9*8*7*6*5*24);
                y += Math.Pow(x, 10) / (10*9*8*7*6*5*24);
                return y;
            }

            return 0;

        }

        static double Picar10(double x, Func<double, double, double> function)
                 {
                     if (function == Function1)
                     {
                         double y = Math.Pow(x, 2) / 2;
                         y += Math.Pow(x, 3) / 6;
                         y += Math.Pow(x, 4) / 24;
                         y += Math.Pow(x, 5) / (5 * 24);
                         y += Math.Pow(x, 6) / (6 * 5 * 24);
                         y += Math.Pow(x, 7) / (7 * 6 * 5 * 24);
                         y += Math.Pow(x, 8) / (8 * 7 * 6 * 5 * 24);
                         y += Math.Pow(x, 9) / (9 * 8 * 7 * 6 * 5 * 24);
                         y += Math.Pow(x, 10) / (10 * 9 * 8 * 7 * 6 * 5 * 24);
                         y += Math.Pow(x, 11) / (11*10 * 9 * 8 * 7 * 6 * 5 * 24);
                         return y;
                     }
         
                     return 0;
                 }
        static double Picar11(double x, Func<double, double, double> function)
        {
            if (function == Function1)
            {
                double y = Math.Pow(x, 2) / 2;
                y += Math.Pow(x, 3) / 6;
                y += Math.Pow(x, 4) / 24;
                y += Math.Pow(x, 5) / (5 * 24);
                y += Math.Pow(x, 6) / (6 * 5 * 24);
                y += Math.Pow(x, 7) / (7 * 6 * 5 * 24);
                y += Math.Pow(x, 8) / (8 * 7 * 6 * 5 * 24);
                y += Math.Pow(x, 9) / (9 * 8 * 7 * 6 * 5 * 24);
                y += Math.Pow(x, 10) / (10 * 9 * 8 * 7 * 6 * 5 * 24);
                y += Math.Pow(x, 11) / (11 * 10 * 9 * 8 * 7 * 6 * 5 * 24);
                y += Math.Pow(x, 12) / (12*11 * 10 * 9 * 8 * 7 * 6 * 5 * 24);
                return y;
            }

            return 0;
        }


        /// <summary>
        /// Метод Эллера (явный).
        /// </summary>
        /// <param name="x"></param>
        /// <param name="y"></param>
        /// <param name="function"></param>
        /// <returns></returns>
        private static double Euler(double x, double y, Func<double, double,double>function)
        {
            return (y+H*function(x,y));
        }
        private static double HideEuler(double x, double y, Func<double, double,double>function)
        {
            double yStrix = function(x + H / 2, y + H / 2);
            return (y+H*yStrix);
        }
        private static double Runge2(double x, double y,Func<double, double,double>function)
        {
            return y + H * function(x + H / 2, y + H / 2 * function(x, y));
        }
        
        private static double Runge4(double x, double y,Func<double, double,double>function)
        {
            double k1 = function(x, y);
            double k2 = function(x + H / 2, y + H * k1 / 2);
            double k3 = function(x + H / 2, y + H * k2 / 2);
            double k4 = function(x + H, y + H * k3);
            
            return y + H / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
        }


        private static void Worker(Func<double, double, double> function)
        {
            // Границы
            double x = 0.0;
            const double maxX = 2.0;

            double euler = 0.0;
            double hideEuler = 0.0;

            double run2 = 0.0;
            
            
            Console.WriteLine("X\t\t|Пикар3 \t|Пикар4 \t|Явный метод\t|Неавный метод\t|Рунге-Кутта \t|");
            Console.WriteLine("\t\t| \t\t| \t\t|Эллера \t|Эллера \t|2-го порядка \t|");
            Console.WriteLine("-----------------------------------------------------------------------------------------");
            while (x <= maxX)
            {
                Console.WriteLine($"{x:F5} \t| {Picar3(x,function):F5} \t| {Picar4(x,function):F5} \t| {euler:F5} \t|{hideEuler:F5} \t| {run2:F5} \t|");
                euler = Euler(x, euler,function);
                hideEuler = HideEuler(x, euler,function);
                run2 = Runge2(x, run2,function);
                
                
                x += H;
            }
            Console.WriteLine($"{x:F5} \t| {Picar3(x,function):F5} \t| {Picar4(x,function):F5} \t| {euler:F5} \t| {hideEuler:F5} \t| {run2:F5} \t|");
            Console.ReadKey();
            Console.WriteLine();
            Console.WriteLine();
            
        }
        
        
        
        static void Main()
        {
           //Worker(Function1);
           Worker(Function2);
           //double x = 2;
           //Console.WriteLine($"P3 {Picar3(x, Function1):F5}");
           //Console.WriteLine($"P4 {Picar4(x, Function1):F5}");
           //Console.WriteLine($"P5 {Picar5(x, Function1):F5}");
           //Console.WriteLine($"P6 {Picar6(x, Function1):F5}");
           //Console.WriteLine($"P7 {Picar7(x, Function1):F5}");
           //Console.WriteLine($"P8 {Picar8(x, Function1):F5}");
           //Console.WriteLine($"P9 {Picar9(x, Function1):F5}");
           //Console.WriteLine($"P10 {Picar10(x, Function1):F5}");
           //Console.WriteLine($"P11 {Picar11(x, Function1):F5}");

            Console.ReadKey();
        }
        
    }
}