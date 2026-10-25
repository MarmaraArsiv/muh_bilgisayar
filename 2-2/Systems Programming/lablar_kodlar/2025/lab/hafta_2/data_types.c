#include <stdio.h>
#include <limits.h>

int main(){
    printf("unsigned short max: %hu = 0x%X\n",USHRT_MAX, USHRT_MAX);
    printf("unsigned max: %u = 0x%X\n",UINT_MAX, UINT_MAX);
    printf("unsigned long max: %lu = 0x%lX\n\n",ULONG_MAX, ULONG_MAX & 0xFFFFFFFFFFFFFFFF);
    
    
    printf("signed short min: %d = 0x%04X\n",SHRT_MIN, SHRT_MIN & 0xFFFF);
    printf("signed short max: %d = 0x%X\n\n",SHRT_MAX, SHRT_MAX);
    
    printf("signed int min: %d = 0x%X\n",INT_MIN, INT_MIN);
    printf("signed int max: %d = 0x%X\n\n",INT_MAX, INT_MAX);

    return 0;
}