function laba1()
    sample = importdata('data(var11).txt');
    sample = sort(sample);
    global grmin;
    global grmax;
    grmin = -7;
    grmax = 3;
    global Min;
    [Max] = getMaxValue(sample);
    [Min] = getMinValue(sample);
    [Range] = getRange(sample);
    fprintf("min value: %.2f \n", Min);
    fprintf("max value: %.2f \n", Max);
    fprintf("range    : %.2f \n", Range);
    fprintf("N        : %d \n", length(sample));

    [MX] = getExpectedValue(sample);
    [DX] = getDispersionValue(sample);

    fprintf("MX       : %.3f \n", MX);
    fprintf("DX       : %.3f \n", DX);

    [GroupTable, m] = Group(sample);
    for i=1:m-1
        fprintf("[%5.2f;%5.2f) ", GroupTable(1,i), GroupTable(1,i+1));
    end

    fprintf("[%5.2f;%5.2f]\n",GroupTable(1,m), Max);
    for i=1:m
        fprintf("%13d ", GroupTable(2,i));
    end
    fprintf("\n\n\n"); 

    figure(1);
    grid;
    hold on;
    HistogramAndDensity(sample)

    figure(2);
    grid;
    hold on;
    EmpiricalAndDensity(sample)
end

function [Max] = getMaxValue(sample)
	Max = max(sample);
end

function [Min] = getMinValue(sample)
	Min = min(sample);
end

function[Range] = getRange(sample)
	Range = getMaxValue(sample) - getMinValue(sample);
end

function [MX] = getExpectedValue(sample)
    n = length(sample);
    MX = sum(sample)/n;
end

function[DX] = getDispersionValue(sample)
    n = length(sample);
    MX = getExpectedValue(sample);
    DX = sum((sample - MX).^2) / n;
end

function[GroupTable, m] = Group(sample)
    n = length(sample);
    m = floor(log2(n)) + 2;
    fprintf("m        : %d \n", m);

    GroupTable = zeros(2,m);
    Delta = getRange(sample)/m;
    fprintf("Delta     : %d \n", Delta);    
    for k = 0:m-1
        GroupTable(1, k+1) = sample(1)+Delta*k;
    end
    
    count = 0;
    for i = 1:n
        for j = 1:m-1
            if GroupTable(1,j) <= sample(i)  && sample(i) < GroupTable(1, j+1)
                GroupTable(2, j) = GroupTable(2, j) + 1;
                count = count + 1;
                break;
            end    
        end    
    end
   GroupTable(2, m) = n - count;
end


%Гистограмма и график функции плотности
function[] = HistogramAndDensity(sample)
    [min] = getMinValue(sample);
    [max] = getMaxValue(sample);
    count = length(sample);
    
    Delta = (max-min)/(count-1);

    Graph = zeros(2,count+2);
    [MX] = getExpectedValue(sample);
    [DX] = getDispersionValue(sample);
    Graph(1,1) = -7;
    Graph(2,1) = 0;
    for i = 1:count
        X = min + Delta*(i-1);
        Graph(1,i+1) = X;
        Graph(2,i+1) = NormalDensityDistribution(X, MX, DX);
    end
    Graph(1,count+2) = 3;
    Graph(2,count+2) = 0;
   
    [GroupTable, n] = Group(sample);
    x = zeros(n+4);
    y = zeros(n+4);
    global grmin;
    global grmax;
    x(1) = grmin;
    y(1) = 0;
   
    %znam = length(sample)*getRange(sample)/n;
    for i =1:n
        x(i+1) = GroupTable(1,i);
        y(i+1) = GroupTable(2,i) ./ length(sample);
        fprintf("%f %f \n", x(i+1), y(i+1));
    end
    x(n+2) = x(n+1)+(x(n+1)-x(n));
    y(n+2) = y(n+1);
    x(n+3) = x(n+2);
    y(n+3) = 0;
    y(n+4)= 0;
    x(n+4)= grmax;
    
    %гистограмма
    stairs(x, y), grid;
    %график плотности
    plot(Graph(1,:), Graph(2,:), 'r'),grid;
    
end

function[y] = NormalDensityDistribution(x, mx, dx) 
    y = exp(-((x-mx).^2)/2/dx)/sqrt(2*pi*dx);
end

function[y] = NormalDistribution(x,mx,sdx)
    %syms t;
    %global Min;
    %y = 1/sqrt(2*pi*dx) * int( exp(-((t-mx).^2)/2/dx), t, Min-2 , x);
    y = cdf('Normal', x, mx,sdx);
end

%эмпирическая функция и функции распределения случайной величины 
function EmpiricalAndDensity(sample)
    global grmax;
    global grmin;
    [min] = grmin;
    [max] = grmax;
    count = length(sample);
    Delta = (max-min)/(count-1);

    Graph = zeros(2,count);
    [MX] = getExpectedValue(sample);
    [DX] = getDispersionValue(sample);
    SDX = sqrt(DX);
    
    %syms t;
    for i = 1:count
        X = min + Delta*(i-1);
        Graph(1,i) = X;        
        Graph(2,i) = NormalDistribution(X, MX, SDX);
        %fprintf("%f %f \n", Graph(1,i), Graph(2,i));
    end

    
    F = zeros(count+2);    
    for i = 1:count
       F(i+1) = EmpiricFunc(sample(i), sample, count);
       %fprintf("EmpiricFunc %f %f \n", sample(i), F(i));
    end
     %график эмпирической функции
    x=[grmin sample grmax];
    F(count+2)=1;
    stairs(x,F),grid;
     %график функции распределения
    plot(Graph(1,:), Graph(2,:), 'r'),grid;
end

function[Fi] = EmpiricFunc(x, sample, n)
    count = 0;
    for i = 1:n
        if (sample(i) <= x)
            count = count + 1;
        else
            continue;
        end
    end
    Fi = count/n;
end
