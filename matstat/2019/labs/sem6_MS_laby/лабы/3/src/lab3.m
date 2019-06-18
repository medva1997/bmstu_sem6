T = csvread("t");
Y = csvread("y");

ones = [];
ones(1, 1:length(T)) = 1;

F = ones;
n = 2;
for i = 1:n
    F = [F; T.^i];
end

theta = ((F * F')\F) * Y';

Yt = theta(1);
for i = 2:(n+1)
    Yt = Yt + theta(i) * T.^(i-1);
end

delta = sqrt(sum((Y - Yt).^2));
 
plot(T, Y, '.b');
hold on;
plot(T, Yt, 'r');
hold off;
axis tight; 
grid on;
