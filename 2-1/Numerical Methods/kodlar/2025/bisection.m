function [root, iter] = bisection(func, a, b, tol, max_iter)
% BISECTION Kökü kapsayan 'İkiye Bölme' yöntemini kullanarak bir fonksiyonun kökünü bulur.
%
% Synopsis: [root, iter] = bisection(func, a, b, tol, max_iter)
%
% Girdi:
%   func     = Kökü aranacak fonksiyon (bir fonksiyon tanıtıcısı, örn: @(x) x^3-x-2)
%   a        = Aralığın alt sınırı
%   b        = Aralığın üst sınırı
%   tol      = (Opsiyonel) İstenen tolerans (hata payı). Varsayılan: 1e-6
%   max_iter = (Opsiyonel) Maksimum iterasyon sayısı. Varsayılan: 100
%
% Çıktı:
%   root = Tahmin edilen kök
%   iter = Kökü bulmak için yapılan iterasyon sayısı

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
% Eğer f(a) ve f(b) aynı işaretliyse, bu aralıkta kök garantisi yoktur.
if fa * fb > 0
    error('Geçersiz aralık: f(a) ve f(b) aynı işaretli. Kök bulunamaz.');
end

% --- İterasyon Döngüsü ---
% Döngü, aralığın genişliği (b-a) toleranstan küçük olana kadar veya
% maksimum iterasyon sayısına ulaşılana kadar devam eder.
% Not: Tahminin hatası en fazla (b-a)/2 kadardır.
while (b-a)/2 > tol && iter < max_iter
    
    % Aralığın orta noktasını (tahmini kök) bul
    c = (a + b) / 2;
    fc = func(c);
    
    % İterasyon sayacını artır
    iter = iter + 1;
    
    % Kökü tam olarak bulduysak (çok düşük ihtimal) döngüyü bitir
    if fc == 0
        break;
    end
    
    % --- Aralığı Daralt ---
    % Kök hangi yarıdaysa, o yarıyı yeni aralık olarak belirle.
    if fa * fc < 0
        % Kök [a, c] aralığındadır. Üst sınırı 'c' yap.
        b = c;
        fb = fc; % (Bu atama zorunlu değil ama iyi bir pratiktir)
    else
        % Kök [c, b] aralığındadır. Alt sınırı 'c' yap.
        a = c;
        fa = fc; % Yeni alt sınırın f değerini sakla
    end
end

% --- Sonuç ---
% Son tahmini kök, en son hesaplanan orta noktadır
root = (a + b) / 2; % Veya sadece 'c' de döndürülebilir

end