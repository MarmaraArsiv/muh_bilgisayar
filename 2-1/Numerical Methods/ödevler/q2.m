th = 0:pi/32:8*pi;
r = exp(sin(th))-2*cos(4*th)-sin((2*th-pi)/24).^5;
% exp(sin(th))-2*cos(4*th)-sin((2*th-pi)/24).^5
polarplot(t, r, '--r');