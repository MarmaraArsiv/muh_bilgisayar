#include <stdio.h>

struct complex add(struct complex, struct complex);
void update(struct complex *);
struct complex {
    float real;
    float imaginary;
   } c={5.2,6.7};

int main(void){



   struct complex e, d={3,4};
   e=add(c,d);
   printf("c:\treal %f\timaginary %.2f\n",c.real,c.imaginary);
   printf("d:\treal %f\timaginary %.2f\n",d.real,d.imaginary);
   printf("c+d:\treal %f\timaginary %.2f\n",e.real,e.imaginary);

   update(&e);
   printf("Updated\ne:\treal %f\timaginary %.2f\n",e.real,e.imaginary);

}
struct complex add(struct complex n1, struct complex n2){
   struct complex r;
   r.real = n1.real + n2.real;
   r.imaginary = n1.imaginary + n2.imaginary;
   return r;
}

void update(struct complex *n1){
   (*n1).real += 1;
   n1->imaginary =2.1;
}
