pkg load nan
X = csvread('data');
 
gamma = 0.9;
alpha = (1 - gamma)/2;
N = 6:length(X);
 
meanX = mean(X);
varX = var(X);
 
M = [];
S = [];
for i=N
    M = [M, mean(X(1:i))];
    S = [S, var(X(1:i))];
end;
 
t = tinv(1 - alpha, N - 1)
hl = chi2inv(1 - alpha, N - 1)
hh = chi2inv(alpha, N - 1)
 
figure
plot([N(1), N(end)], [meanX, meanX], 'm');
hold on;
plot(N, M, 'g');
Ml = M .- sqrt(S).*t./sqrt(N);
plot(N, Ml, 'b');
Mh = M .+ sqrt(S).*t./sqrt(N);
plot(N, Mh, 'r');
hold off;
 
figure
plot([N(1), N(end)], [varX, varX], 'm');
hold on;
plot(N, S, 'g');
Sl = S.*(N - 1)./hl;
plot(N, Sl, 'b');
Sh = S.*(N - 1)./hh;
plot(N, Sh, 'r');
hold off;

fprintf('mu = %.2f\nS^2 = %.2f\n\n', meanX, varX);
fprintf('mu_low = %.2f\nmu_high = %.2f\n', Ml(end), Mh(end));
fprintf('sigma^2_low = %.2f\nsigma^2_high = %.2f\n', Sl(end), Sh(end));