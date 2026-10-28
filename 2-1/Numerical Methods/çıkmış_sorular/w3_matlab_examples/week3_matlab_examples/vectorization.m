%%The following loop increases the size of s on each pass.
tic
y = rand(1,100000)-0.5;      %  some computation to define y
for j=1:length(y)
    if y(j)>0
        s(j) = sqrt(y(j));
    else
        s(j) = 0;
    end
end
disp('First ')
toc


%% Preallocate s before assigning values to elements.
tic
y = rand(1,100000)-0.5;     %  some computation to define y
s = zeros(size(y));
for j=1:length(y)
    if y(j)>0
        s(j) = sqrt(y(j));
    end
end
disp('Second ')
toc

%% In fact, the loop can be replaced entirely by using logical and array indexing
tic
y = rand(1,100000)-0.5;  %  some computation to define y
s = zeros(size(y));
i = find(y>0);           %  indices such that y(i)>0
s(i) = sqrt(y(i));
disp('Third')
toc

