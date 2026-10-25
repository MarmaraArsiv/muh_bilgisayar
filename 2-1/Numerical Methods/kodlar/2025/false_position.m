function [root, iter, rel_err] = false_position(func, a, b, tol, max_iter)
% FALSE_POSITION Kökü kapsayan 'False Position' (Regula Falsi) yöntemini kullanır.
%
% Synopsis: [root, iter, rel_err] = false_position(func, a, b, tol, max_iter)
%
% Girdi:
%   func     = Kökü aranacak fonksiyon (bir fonksiyon tanıtıcısı, örn: @(x) x^3-x-2)
%   a        = Aralığın alt sınırı
%   b        = Aralığın üst sınırı
%   tol      = (Opsiyonel) İstenen göreli hata toleransı. Varsayılan: 1e-6
%   max_iter = (Opsiyonel) Maksimum iterasyon sayısı. Varsayılan: 100
%
% Çıktı:
%   root     = Tahmin edilen kök
%   iter     = Yapılan iterasyon sayısı
%   rel_err  = Son hesaplanan göreli hata

% --- Girdi Argümanlarını Kontrol Et ---
if nargin < 3
    error('En az 3 girdi gereklidir: func, a, b');
end
if nargin < 4 || isempty(tol)
    tol = 1e-6; % Varsayılan tolerans
end
if nargin < 5 || isempty(max_iter)
    max_iter = 100; % Varsayılan maksimum iterasyon
end

% --- Başlangıç Değerlerini Hesapla ---
fa = func(a);
fb = func(b);
iter = 0;

% --- Girdi Kontrolü: Kök aralıkta mı? ---
if fa * fb > 0
    error('Geçersiz aralık: f(a) ve f(b) aynı işaretli. Kök bulunamaz.');
end

% c = 'root' için ilk tahmini yapalım (göreli hata hesaplaması için)
% Başlangıçta bisection gibi bir orta nokta almak güvenlidir
c = (a + b) / 2;
rel_err = 1; % Göreli hatayı döngüye girmek için 1 başlat

fprintf('Iter. \t a \t\t b \t\t c \t\t f(c) \t\t Bağıl Hata\n');
fprintf('-----------------------------------------------------------------------------------\n');

% --- İterasyon Döngüsü (max_iter veya tolerans sağlanana kadar) ---
for iter = 1:max_iter
    
    % Önceki kök tahminini sakla
    c_prev = c;
    
    % --- False Position Formülü ---
    % Doğrunun x-eksenini kestiği yeri hesapla
    % c = (a*fb - b*fa) / (fb - fa);
    % Sayısal kararlılık için bu formül daha iyidir:
    c = b - (fb * (b - a)) / (fb - fa);
    
    fc = func(c);
    
    % Göreli hatayı hesapla
    if c ~= 0
        rel_err = abs(c - c_prev) / abs(c);
    else
        rel_err = 0; % Kök tam olarak 0 ise
    end
    
    % Iterasyon bilgilerini ekrana bas
    fprintf('%d \t %.8f \t %.8f \t %.8f \t %.2e \t %.2e\n', ...
            iter, a, b, c, fc, rel_err);
    
    % --- Durma Kriterleri ---
    % 1. Kriter: Göreli hata toleranstan küçükse (ilk iterasyon hariç)
    if rel_err < tol && iter > 1
        break;
    end
    
    % 2. Kriter: Kök tam olarak bulunduysa veya f(c) yeterince küçükse
    % (Bu, y-ekseninde düzleşen fonksiyonlar için iyi bir kontroldür)
    if abs(fc) < tol % (veya eps)
        break;
    end
    
    % --- Aralığı Güncelle ---
    if fa * fc < 0
        % Kök [a, c] aralığındadır
        b = c;
        fb = fc;
    else
        % Kök [c, b] aralığındadır
        a = c;
        fa = fc;
    end
end

% --- Sonuç ---
root = c; % Son hesaplanan kökü döndür

if iter == max_iter
    warning('Maksimum iterasyon (%d) sayısına ulaşıldı. Sonuç istenen toleransa ulaşmamış olabilir.', max_iter);
end

end