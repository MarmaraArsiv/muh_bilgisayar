#include <stdio.h>

int main(){
    
    int x = 53191;
    short sx = (short int) x;  /* sx = -12345 */
    int y = sx;      /*  y = -12345 */

    printf(" x=%d (0x%.8x)\n sx=%hi (0x%.4x)\n y=%d (0x%.8x)",x,x,sx,sx,y,y);

    return 0;
}