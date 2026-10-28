[f_min, f_max]=minMax(@(t) humps(t), 0, 2);
function [f_min, f_max] = minMax(f, x_start, x_end)
% input:
%   f - given function
%   x:=[x_start, x_end] the given range on which the function is being
%   evaluated
% output: 
%   f_min - minimum of function f on range x
%   f_max - maximum of function f on range x

x = linspace(x_start, x_end, max(abs(x_start), abs(x_end))*1000);
y = f(x);

f_min = min(y);
f_max = max(y);

plot(x,y);
xlabel('x');
ylabel('y');
axis([x_start x_end f_min f_max]);

end

% Tests:
% (a) [f_min, f_max]=minMax(@(t) 8*exp(-0.25*t).*sin(t-2), 0, 6*pi);
% (b) [f_min, f_max]=minMax(@(x) exp(4*x).*sin(1./x), 0.01, 0.2);
% (c) [f_min, f_max]=minMax(@(t) humps(t), 0, 2);