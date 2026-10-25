#include <stdio.h>

int main(){
    
    short a = 13;
    printf("%d & 0 = %d (%.4X & %.4X = %.4X)\n",a,a&0,a,0,a&0);
    printf("%d & 1 = %d (%.4X & %.4X = %.4X)\n\n",a,a&1,a,1,a&1);

    printf("%d | 0 = %d (%.4X | %.4X = %.4X)\n",a,a|0,a,0,a|0);
    printf("%d | 1 = %d (%.4X | %.4X = %.4X)\n\n",a,a|1,a,1,a|1);
    
    printf("%d ^ 0 = %d (%.4X ^ %.4X = %.4X)\n",a,a^0,a,0,a^0);
    printf("%d ^ 1 = %d (%.4X ^ %.4X = %.4X)\n\n",a,a^1,a,1,a^1);

    printf("~%d = %d (~%.4X = %.4X)\n",a,~a,a,~a);
    
    return 0;
}