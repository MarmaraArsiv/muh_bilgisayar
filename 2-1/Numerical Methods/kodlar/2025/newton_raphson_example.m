% 1. Adım: Fonksiyonu (f) ve Türevini (df) tanımlayalım.
f = @(x) x.^3 - x - 2;
df = @(x) 3*x.^2 - 1;

% 2. Adım: Bir başlangıç tahmini (x0) seçelim.
% Kök 1.5 civarındaydı. 1.0 ile başlayalım.
x0 = 1.0;

% 3. Adım: Toleransı belirleyelim.
tolerans = 1e-8;

% 4. Adım: newton_raphson fonksiyonumuzu çağıralım.
fprintf('### NEWTON-RAPHSON YÖNTEMİ ###\n');
fprintf('Problem: f(x) = x^3 - x - 2 = 0, Başlangıç: x0 = %.1f\n', x0);
[kok_nr, iter_nr, err_nr] = newton_raphson(f, df, x0, tolerans);

% 5. Adım: Sonuçları ekrana yazdıralım.
fprintf('\n--- Sonuçlar (Newton-Raphson) ---\n');
fprintf('   Bulunan Kök = %.10f\n', kok_nr);
fprintf('   İterasyon Sayısı = %d\n', iter_nr);
fprintf('   f(kök) = %.10f\n', f(kok_nr));