function Lab3()
%% аппроксимация неизвестной зависимости параболой
%Вариант № 8

    T = importdata('dataT.txt');
    Y = importdata('dataY.txt');
    
    %по запросу власова
    for i = 1:length(Y)
        if Y(i) < 100
            Y(i) = 200;
        end
    end

    One(1:length(T), 1) = 1;
    T2 = T.^2;
    F = horzcat(One, T, T2);

    Ft = transpose(F);
    theta = inv(Ft*F) * Ft * Y;
    
    Yt = theta(1) + theta(2) * T + theta(3) * T2;
    delta = sqrt(sum((Y - Yt).^2));
    deltaS = sprintf('\\Delta = %.5f\n', delta);
    
    figure(1);
    
    %переопределим Yt, чтобы не получать кусочную функцию на малых выборках
    T_G = min(T):0.01:max(T);
    T_G2 = T_G.^2;
    Yt = theta(1) + theta(2) * T_G + theta(3) * T_G2;
    
    plot(T, Y, '.r'); %экспериментальные данные
    hold on;
    
    plot(T_G, Yt, 'b'); %полученная аппроксимация
    grid on;
    
    text(20,20, deltaS, 'Units', 'pixels');
    
    y_eq = sprintf('y = %.2f + %.2f*t + %.2f*t^2',...
        theta(1), theta(2), theta(3));
    legend('Y experimental', y_eq);
    xlabel('T');
    ylabel('Y');
end