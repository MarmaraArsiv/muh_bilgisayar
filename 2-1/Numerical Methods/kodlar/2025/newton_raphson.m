function [root, iter, rel_err] = newton_raphson(func, dfunc, x0, tol, max_iter)
% NEWTON_RAPHSON Newton-Raphson yöntemini kullanarak bir fonksiyonun kökünü bulur.
%
% Synopsis: [root, iter, rel_err] = newton_raphson(func, dfunc, x0, tol, max_iter)
%
% Girdi:
%   func     = Kökü aranacak fonksiyon f(x) (Fonksiyon tanıtıcısı)
%   dfunc    = f(x)'in türevi f'(x) (Fonksiyon tanıtıcısı)
%   x0       = Başlangıç tahmini
%   tol      = (Opsiyonel) İstenen göreli hata toleransı. Varsayılan: 1e-6
%   max_iter = (Opsiyonel) Maksimum iterasyon sayısı. Varsayılan: 100
%
% Çıktı:
%   root     = Tahmin edilen kök
%   iter     = Yapılan iterasyon sayısı
%   rel_err  = Son hesaplanan göreli hata

% --- Girdi Argümanlarını Kontrol Et ---
if nargin < 3
    error('En az 3 girdi gereklidir: func, dfunc ve x0');
end
if nargin < 4 || isempty(tol)
    tol = 1e-6; % Varsayılan tolerans
end
if nargin < 5 || isempty(max_iter)
    max_iter = 100; % Varsayılan maksimum iterasyon
end

% --- İterasyonu Başlat ---
iter = 0;
rel_err = 1; % Döngüye girmek için 1 başlat
xi = x0;     % xi = "x_i" (mevcut tahmin)

fprintf('Iter. \t xi \t\t f(xi) \t\t f''(xi) \t\t Bağıl Hata\n');
fprintf('----------------------------------------------------------------------------\n');
fprintf('%d \t %.8f\n', iter, xi);

% --- İterasyon Döngüsü ---
for iter = 1:max_iter
    
    % Fonksiyon ve türev değerlerini hesapla
    f_xi = func(xi);
    df_xi = dfunc(xi);
    
    % Hata Kontrolü: Türev sıfırsa (veya sıfıra çok yakınsa) dur.
    % Teğet yataydır, x-eksenini asla kesmez.
    if abs(df_xi) < eps % 'eps' MATLAB'daki en küçük pozitif sayıdır
        error('Türev sıfıra çok yakın. Newton-Raphson yöntemi başarısız oldu.');
    end
    
    % Newton-Raphson formülünü uygula
    % xi_plus_1 = xi - f_xi / df_xi;
    xi_prev = xi; % Göreli hata için bir önceki adımı sakla
    xi = xi - f_xi / df_xi; % xi artık "x_{i+1}" oldu
    
    % Göreli hatayı hesapla
    if xi ~= 0
        rel_err = abs(xi - xi_prev) / abs(xi);
    else
        rel_err = abs(xi - xi_prev);
    end
    
    % Iterasyon bilgilerini ekrana bas
    fprintf('%d \t %.8f \t %.2e \t %.2e \t %.2e\n', ...
            iter, xi, f_xi, df_xi, rel_err);
    
    % --- Durma Kriteri ---
    if rel_err < tol
        break; % Başarıyla yakınsadı
    end
end

% --- Sonuç ---
root = xi; % Son hesaplanan kökü döndür

if iter == max_iter
    warning('Maksimum iterasyon (%d) sayısına ulaşıldı. Yöntem istenen toleransa ulaşmamış olabilir.', max_iter);
end

end