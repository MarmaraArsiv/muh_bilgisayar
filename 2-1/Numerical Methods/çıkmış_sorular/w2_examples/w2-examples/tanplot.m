%% this code is ... 
theta = linspace(1.6,4.6);
tandata = tan(theta);

%%
plot(theta,tandata);
xlabel('\theta     (radians)');
ylabel('tan(\theta)');
grid on;
axis([min(theta) max(theta) -5 5]);
