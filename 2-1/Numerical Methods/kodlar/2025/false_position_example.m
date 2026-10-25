% 1. Adım: Kökünü bulmak istediğimiz fonksiyonu tanımlayalım.
f = @(x) x^3 - x - 2;

% 2. Adım: Aralığı belirleyelim [1, 2]
a = 1;
b = 2;

% 3. Adım: Toleransı belirleyelim.
tolerans = 1e-8;

% 4. Adım: false_position fonksiyonumuzu çağıralım.
fprintf('### FALSE POSITION YÖNTEMİ ###\n');
[kok_fp, iter_fp, err_fp] = false_position(f, a, b, tolerans);

fprintf('\n--- Sonuçlar (False Position) ---\n');
fprintf('   Bulunan Kök = %.10f\n', kok_fp);
fprintf('   İterasyon Sayısı = %d\n', iter_fp);
fprintf('   f(kök) = %.10f\n\n', f(kok_fp));

% 5. Adım (Karşılaştırma): Bisection yöntemini de çağıralım.
% (Bir önceki cevaptaki bisection.m dosyasının kayıtlı olduğunu varsayıyoruz)
fprintf('### BISECTION YÖNTEMİ (Karşılaştırma) ###\n');
[kok_bi, iter_bi] = bisection(f, a, b, tolerans);

fprintf('\n--- Sonuçlar (Bisection) ---\n');
fprintf('   Bulunan Kök = %.10f\n', kok_bi);
fprintf('   İterasyon Sayısı = %d\n', iter_bi);
fprintf('   f(kök) = %.10f\n', f(kok_bi));