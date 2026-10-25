% 1. Adım: Kökünü bulmak istediğimiz fonksiyonu tanımlayalım.
% f(x) = x^3 - x - 2
% '@(x)' ile bu ifadeyi bir fonksiyon tanıtıcısı (handle) yapıyoruz.
f = @(x) x^3 - x - 2;

% 2. Adım: Kök için bir aralık belirleyelim.
% f(1) = 1 - 1 - 2 = -2  (Negatif)
% f(2) = 8 - 2 - 2 = +4  (Pozitif)
% İşaretler zıt olduğu için [1, 2] aralığı geçerlidir.
a = 1;
b = 2;

% 3. Adım (Opsiyonel): Toleransı belirleyelim.
% Eğer belirtmezsek, fonksiyon varsayılan olarak 1e-6 kullanır.
tolerans = 1e-8;

% 4. Adım: Yazdığımız bisection fonksiyonunu çağıralım.
[bulunan_kok, iterasyon_sayisi] = bisection(f, a, b, tolerans);

% 5. Adım: Sonuçları ekrana yazdıralım.
fprintf('f(x) = x^3 - x - 2 fonksiyonunun kökü:\n');
fprintf('   Kök = %.10f\n', bulunan_kok);
fprintf('   İterasyon Sayısı = %d\n', iterasyon_sayisi);

% Kontrol edelim:
fprintf('   f(kök) = %.10f (Sıfıra çok yakın olmalı)\n', f(bulunan_kok));