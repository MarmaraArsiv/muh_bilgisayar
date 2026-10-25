%% Solve cos(x) = x 
x0 = 0.5;      %  initial guess
it = 0;
xnew = x0;
xold = 0;
maxit = 100;
delta = 10^-4
while ((abs(xold-xnew)/xold) > delta) & it < maxit
    xold = xnew;
    xnew = cos(xold);
    it = it + 1
end
format long
xnew