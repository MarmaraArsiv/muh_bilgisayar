% example 2.8
clc; clear all;
format long
n = 1:10;
x = 10.^(-n)
y = ((1+x).^2-(2*x+1)) ./ x.^2
