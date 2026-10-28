D = load('pdxTemp.dat'); 
m = D(:,1); 
T = D(:,2:4);
plot(m,T(:,1),'ro',m,T(:,2),'k+',m,T(:,3),'b-');
xlabel('Month');
ylabel('Temperature ({}^\circ F)');
title('Monthly average temperature at PDX');
axis([1 12 20 100]);
legend('High','Low','Average');