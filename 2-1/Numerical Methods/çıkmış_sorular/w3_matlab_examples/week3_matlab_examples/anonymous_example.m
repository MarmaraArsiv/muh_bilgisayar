%% An anonymous function 
% is a function that is not stored in a program file, but is associated with a 
% variable whose data type is function_handle. Anonymous functions can accept 
% inputs and return outputs, just as standard functions do. However, they 
% can contain only a single executable statement.

sqr = @(x) x.^2;
a = sqr(5)

%% Function handles can store not only an expression, but also variables that
% the expression requires for evaluation. For example, create a function handle 
% to an anonymous function that requires coefficients a, b, and c.

a = 1.3;
b = .2;
c = 30;
parabola = @(x) a*x.^2 + b*x + c;

% Because a, b, and c are available at the time you create parabola, the function handle includes those values. The values persist within the function handle even if you clear the variables:

clear a b c
x = 1;
y = parabola(x)

% To supply different values for the coefficients, you must create a new function handle:

a = -3.9;
b = 52;
c = 0;
parabola = @(x) a*x.^2 + b*x + c;

x = 1;
y = parabola(1)

% You can save function handles and their associated values in a MAT-file and load them in a subsequent MATLAB session using the save and load functions, such as

save myfile.mat parabola