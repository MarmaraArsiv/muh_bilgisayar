% Kökünü aradığımız problem: f(x) = x^2 - 2x - 3 = 0 (Gerçek kök x = 3)

% --- ÖRNEK 1: IRAKSAK (DIVERGENT) ---
fprintf('### IRAKSAK ÖRNEK (g(x) = (x^2 - 3)/2) ###\n');
g_diverge = @(x) (x.^2 - 3) ./ 2;
x0 = 4;
tol = 1e-4;
max_iter = 10; % Iraksadığını görmek için 10 iterasyon yeterli

% fixed_point_iteration fonksiyonunu çağır
[root_div, iter_div, err_div] = fixed_point_iteration(g_diverge, x0, tol, max_iter);
fprintf('Iraksak deneme %d iterasyon sonunda durdu.\n\n', iter_div);


% --- ÖRNEK 2: YAKINSAK (CONVERGENT) ---
fprintf('### YAKINSAK ÖRNEK (g(x) = sqrt(2x + 3)) ###\n');
g_converge = @(x) sqrt(2*x + 3);
% Aynı x0 değerini kullanıyoruz!
x0 = 4;
tol = 1e-4;
max_iter = 10;

% fixed_point_iteration fonksiyonunu çağır
[root_conv, iter_conv, err_conv] = fixed_point_iteration(g_converge, x0, tol, max_iter);
fprintf('Yakınsak deneme %d iterasyon sonunda durdu.\n', iter_conv);
fprintf('Bulunan kök: %.8f\n', root_conv);