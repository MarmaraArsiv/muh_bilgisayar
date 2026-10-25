function [root, iter, rel_err] = fixed_point_iteration(g, x0, tol, max_iter)
% FIXED_POINT_ITERATION Sabit nokta iterasyonu (x = g(x)) yöntemini uygular.
%
% Synopsis: [root, iter, rel_err] = fixed_point_iteration(g, x0, tol, max_iter)
%
% Girdi:
%   g        = İterasyon fonksiyonu (x = g(x) formundaki)
%              (Fonksiyon tanıtıcısı, örn: @(x) cos(x))
%   x0       = Başlangıç tahmini
%   tol      = (Opsiyonel) İstenen göreli hata toleransı. Varsayılan: 1e-6
%   max_iter = (Opsiyonel) Maksimum iterasyon sayısı. Varsayılan: 100
%
% Çıktı:
%   root     = Tahmin edilen kök (sabit nokta)
%   iter     = Yapılan iterasyon sayısı
%   rel_err  = Son hesaplanan göreli hata

% --- Girdi Argümanlarını Kontrol Et ---
if nargin < 2
    error('En az 2 girdi gereklidir: g(x) fonksiyonu ve x0 başlangıç tahmini');
end
if nargin < 3 || isempty(tol)
    tol = 1e-6; % Varsayılan tolerans
end
if nargin < 4 || isempty(max_iter)
    max_iter = 100; % Varsayılan maksimum iterasyon
end

% --- İterasyonu Başlat ---
iter = 0;
rel_err = 1; % Döngüye girmek için 1 başlat
xi = x0;     % xi = "x_i" (mevcut tahmin)

fprintf('Iter. \t xi \t\t g(xi) \t\t Bağıl Hata\n');
fprintf('------------------------------------------------------------\n');
fprintf('%d \t %.8f\n', iter, xi);

% --- İterasyon Döngüsü ---
for iter = 1:max_iter
    
    % Bir sonraki tahmini hesapla: x_{i+1} = g(x_i)
    xi_plus_1 = g(xi);
    
    % Göreli hatayı hesapla
    if xi_plus_1 ~= 0
        rel_err = abs(xi_plus_1 - xi) / abs(xi_plus_1);
    else
        rel_err = abs(xi_plus_1 - xi);
    end
    
    % Mevcut tahmini güncelle
    xi = xi_plus_1;
    
    % Iterasyon bilgilerini ekrana bas
    fprintf('%d \t %.8f \t %.8f \t %.2e\n', ...
            iter, xi_plus_1, g(xi_plus_1), rel_err);
    
    % --- Durma Kriteri ---
    if rel_err < tol
        break;
    end
end

% --- Sonuç ---
root = xi; % Son hesaplanan kökü döndür

if iter == max_iter
    warning('Maksimum iterasyon (%d) sayısına ulaşıldı. Yöntem yakınsamamış olabilir.', max_iter);
end

end