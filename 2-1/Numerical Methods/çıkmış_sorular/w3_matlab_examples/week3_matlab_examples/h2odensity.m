function rho = H2Odensity(T,units)
% H2Odensity Density of saturated liquid water

if nargin<1
    rho = 998.2;  return;
elseif nargin==1
    units='C';
end

% --- Convert to degrees C if necessary
if upper(units)=='F'
    Tin = (T-32)*5/9;        %  Convert F to C; don’t change input variable
elseif upper(units) == 'C'
    Tin = T;
else
    error(sprintf('units = ''%s’’ not allowed in H20density',units));
end

% --- Make sure temperature is within range of curve fit

%  Density at 20 C w/out evaluating curve fit
%  Default units are C

if Tin<0 | Tin>100
    error(sprintf('T = %f (C) is out of range for density curve fits',Tin));
end

% --- Curve fit coefficients
c = [ 1.543908249780381441e-05  -5.878005395030049852e-03 ...
    1.788447211945859774e-02  1.000009926781338436e+03];

rho = polyval(c,Tin);     % Evaluate polynomial curve fit
if upper(units)=='F'
    rho = rho*6.243e-2;     % Convert kg/m^3 to lbm/ft^3
end