/* struct vs union */

#include <stdio.h>
#include <stdlib.h>

typedef struct fraction{
    float num;
    float den;
} frac;

typedef struct logarithm{
    float base;
    float exp;
} log;

typedef struct complex_number{
    float re;
    float im;
} comp;

typedef struct number_struct{
    unsigned int nat;
    int in;
    float rat;
    frac fr;
    log lg;
    comp cmp;
} num_struct;

/* union definition with typedef keyword, the definition is the same with struct */
typedef union number_union{
    unsigned int nat;
    int in;
    float rat;
    frac fr;
    log lg;
    comp cmp;
} num_union;

int main(){
    num_union* num_list;
    
    /* struct size ~= sum(type1, type2, ... typeN) */
    printf("sizeof(num_struct) = %ld\n", sizeof(num_struct));
    /* union size ~= max(type1, type2, ... typeN) */
    printf("sizeof(num_union) = %ld\n\n", sizeof(num_union));
    
    /* make a list of union for efficient use of memory for storing a set of numbers */
    num_list = malloc(sizeof(num_union)*7);
    
    num_list[0].nat=3;
    num_list[1].in=-7;
    num_list[2].rat=-172.13;
    num_list[3].fr.num=3.2; num_list[3].fr.den=4.5;
    num_list[4].lg.base=2;  num_list[4].lg.exp=8;
    num_list[5].cmp.re=3.2; num_list[5].cmp.im=3.5;
    
    printf("%u\n", num_list[0].nat);
    printf("%d\n", num_list[1].in);
    printf("%f\n", num_list[2].rat);
    printf("%f / %f\n", num_list[3].fr.num, num_list[3].fr.den);
    printf("log(%f)%f\n", num_list[4].lg.base, num_list[4].lg.exp);
    printf("%f + %fi\n\n", num_list[5].cmp.re, num_list[5].cmp.im);
    
    /* use only one type at a time in a union */
    /* storing two types at a time gives unexpected results */
    num_list[6].rat=2.5;
    num_list[6].in=17;
    printf("%f\n", num_list[6].rat);
    
    return 0;
}
