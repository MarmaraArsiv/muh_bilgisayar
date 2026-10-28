sinEval(0.9, 8);
function  sinEval(x, n)
% Approximation of sine function by the Taylor series (part of)
% input:
%    x - the point at which the function is being evaluated
%    n  - number of terms

i = 1;
trueVal = sin(x);
approxVal = 0;
fprintf('\n');
fprintf(' order    true value    approximated value         error \n');
while(1)
    if i>n, break, end 
    approxVal = approxVal+(-1)^(i-1)*x^(2*i-1)/factorial(2*i-1);
    err = 100*(trueVal-approxVal)/trueVal;
    fprintf('%3d      %12.10f       %12.10f      %15.10f\n', i, trueVal, approxVal, err);
    i = i+1;
end
end
% test: 