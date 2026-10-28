 function y = sincos(x)
   %  SINCOS   Evaluates sin(x)*cos(x) for any input x
   %
   %  Synopsis:  y = sincos(x)
   %
   %  Input:     x = angle in radians, or vector of angles in radians
   %
   %  Output:    y = value of product sin(x)*cos(x) for each element in x
   y = sin(x).*cos(x);
