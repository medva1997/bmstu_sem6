close all;

X = csvread('data');

count = length(X);

M_max = max(X);
M_min = min(X);
R = M_max - M_min;
MX = mean(X);
fixed_disp = var(X);
sigma = sqrt(fixed_disp);
break_cnt = floor(log2(count)) + 2;
[y1, x1] = hist(X, break_cnt);

delta = (M_max - M_min) / break_cnt;
y1 = y1 / (count * delta);

normx = -8 : delta / 10 : 10;
normp = normpdf(normx, MX, sigma);
normc = normcdf(normx, MX, sigma);

[y2, x2] = ecdf(X);

figure
bar(x1, y1, 1);
hold on;
plot(normx, normp, 'r');
hold off;

x2 = vertcat(min(normx), x2, max(normx));
y2 = vertcat(min(normc), y2, max(normc));

figure
stairs(x2, y2, 'b');
hold on;
plot(normx, normc, 'r');
hold off;
