theta = 0:1/16:100;
t = 0:1/16:100;
x = sin(t).* ((exp(1).^cos(t))-2.*cos(4.*t)-(sin(t/12).^5));
y = cos(t).* ((exp(1).^cos(t))-2.*cos(4.*t)-(sin(t/12).^5));
subplot(2,1,1)
plot(t, x, t, y, ':')
xlabel('t')
xlabel(' x & y ')
legend('x', 'y')

subplot(2,1,2)
plot(x,y)
xlabel('x')
ylabel('y')
axis square