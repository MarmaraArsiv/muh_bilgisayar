figure
% Part 1
subplot(3,2,1)
mu = [0 0]; % mean
Sigma = [1 0; 0 1]; % covariance matrix
rng('default')
R = mvnrnd(mu,Sigma,100);
plot(R(:,1),R(:,2),'+')
xlim([-5 +5]) % limit x-axis values
ylim([-5 5]) % limit y-axis values
title('Part 1')

% Part 2
subplot(3,2,2)
mu = [1 -1]; % mean
Sigma = [1 0; 0 1]; % covariance matrix
rng('default')
R = mvnrnd(mu,Sigma,100);
plot(R(:,1),R(:,2),'+')
xlim([-5 +5]) % limit x-axis values
ylim([-5 5]) % limit y-axis values
title('Part 2')

% Part 3
subplot(3,2,3)
mu = [0 0]; % mean
Sigma = [2 0; 0 2]; % covariance matrix
rng('default')
R = mvnrnd(mu,Sigma,100);
plot(R(:,1),R(:,2),'+')
xlim([-5 +5]) % limit x-axis values
ylim([-5 5]) % limit y-axis values
title('Part 3')

% Part 4
subplot(3,2,4)
mu = [0 0]; % mean
Sigma = [2 0.2; 0.2 2]; % covariance matrix
rng('default')
R = mvnrnd(mu,Sigma,100);
plot(R(:,1),R(:,2),'+')
xlim([-5 +5]) % limit x-axis values
ylim([-5 5]) % limit y-axis values
title('Part 4')

% Part 5
subplot(3,2,5)
mu = [0 0]; % mean
Sigma = [2 -0.2; -0.2 2]; % covariance matrix
rng('default')
R = mvnrnd(mu,Sigma,100);
plot(R(:,1),R(:,2),'+')
xlim([-5 +5]) % limit x-axis values
ylim([-5 5]) % limit y-axis values
title('Part 5')

print('FillPageFigure','-dpdf','-fillpage')