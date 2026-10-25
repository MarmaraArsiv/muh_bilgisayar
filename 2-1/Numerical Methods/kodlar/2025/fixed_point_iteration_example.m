% 1. Adım: İterasyon fonksiyonu g(x)'i tanımlayalım.
% Problem f(x) = x - cos(x) = 0
% Yeniden düzenlersek: x = cos(x)
% Dolayısıyla bizim iterasyon fonksiyonumuz:
g = @(x) cos(x);

% 2. Adım: Bir başlangıç tahmini (x0) seçelim.
% Kök 1'e yakın bir yerde, 0.5 ile başlayalım.
x0 = 0.5; % (Radyan cinsinden)

% 3. Adım: Toleransı belirleyelim.
tolerans = 1e-8;

% 4. Adım: fixed_point_iteration fonksiyonumuzu çağıralım.
fprintf('### SABİT NOKTA İTERASYONU ###\n');
fprintf('Problem: x = cos(x), Başlangıç: x0 = %.1f\n', x0);
[kok_fp, iter_fp, err_fp] = fixed_point_iteration(g, x0, tolerans);

% 5. Adım: Sonuçları ekrana yazdıralım.
fprintf('\n--- Sonuçlar (Sabit Nokta) ---\n');
fprintf('   Bulunan Kök = %.10f\n', kok_fp);
fprintf('   İterasyon Sayısı = %d\n', iter_fp);

% Kontrol edelim: g(kök) gerçekten köke eşit mi?
fprintf('   g(kök) = %.10f\n', g(kok_fp));