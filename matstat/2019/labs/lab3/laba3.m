
function laba3()
    Y = importdata('dataY(v11).txt');
    T1 = importdata('dataT(v11).txt');
    %Y = Y-100*sin(abs(Y));
    
    T2 = T1.^2;
    T3 = T1.^3;
    T4 = T1.^4;
    T5 = T1.^5;
    T6 = T1.^6;
    T0(1:length(T1), 1)=1;
    
    psi = horzcat(T0,T1,T2,T3,T4,T5,T6);
    psiT = transpose(psi);
    %вычисление МНК оценки вектора theta
    theta = inv(psiT*psi)*psiT*Y;
    Yt = theta(1) + theta(2)*T1 + theta(3)*T2 +theta(4)*T3+theta(5)*T4+theta(6)*T5+ theta(7)*T6;
    fprintf("theta1 = %.2f\n",theta(1));
    fprintf("theta2 = %.2f\n",theta(2));
    fprintf("theta3 = %.2f\n",theta(3));
    fprintf("theta4 = %.2f\n",theta(4));
    fprintf("theta5 = %.4f\n",theta(5));
    fprintf("theta6 = %.4f\n",theta(6));
    fprintf("theta7 = %.6f\n",theta(7));
    Ystr = sprintf("Yt = %.2f + %.2f*t + %.2f*t^2+ %.2f*t^3+ %.4f*t^4+ %.4f*t^5+ %.6f*t^6\n", theta(1), theta(2), theta(3),theta(4), theta(5), theta(6), theta(7));
    fprintf(Ystr);
    
    %вычисление среднеквадратичного отклонения полученной модели 
    %от результатов изменений
    delta = sqrt(sum((Y-Yt).^2));
    fprintf("delta=%.3f\n",delta);
   
    figure(1);
    xlabel('T');
    ylabel('Y');
    plot(T1, Y, '.r'); %эксперимент   
    hold on;
    plot(T1, Yt, 'b'); %получено
    legend("Система точек (t_i,y_i)", Ystr);
    grid on;
end