#include <stdio.h>

int main(){
    
    unsigned short a = 13;
    printf("%hu & 0 = %hu (%.4X & %.4X = %.4X)\n",a,a&0,a,0,a&0);
    printf("%hu & 1 = %hu (%.4X & %.4X = %.4X)\n\n",a,a&1,a,1,a&1);

    printf("%hu | 0 = %hu (%.4X | %.4X = %.4X)\n",a,a|0,a,0,a|0);
    printf("%hu | 1 = %hu (%.4X | %.4X = %.4X)\n\n",a,a|1,a,1,a|1);
    
    printf("%hu ^ 0 = %hu (%.4X ^ %.4X = %.4X)\n",a,a^0,a,0,a^0);
    printf("%hu ^ 1 = %hu (%.4X ^ %.4X = %.4X)\n\n",a,a^1,a,1,a^1);

    printf("~%hu = %hu (~%.4X = %.4X)\n",a,~a,a,~a);
    
    return 0;
}