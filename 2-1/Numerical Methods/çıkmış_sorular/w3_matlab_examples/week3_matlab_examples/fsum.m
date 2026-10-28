function s = fsum(fun,a,b,n)
%  FSUM  Computes the sum of function values, f(x), at n equally
%        distributed points in an interval a <= x <= b
%
%  Synopsis:   s = fsum(fun,a,b,n)
%
%  Input:  fun = (string) name of the function to be evaluated
%          a,b = endpoints of the interval
%          n   = number of points in the interval
x = linspace(a,b,n);
y = feval(fun,x);
s = sum(y);
%  create points in the interval
%  evaluate function at sample points
%  compute the sum