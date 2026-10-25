#include <stdio.h>

int main(){
    
  int x=5; /* 00..0101 in binary, 00000005 in hex */
  int y = x<<4; /* x<<4 = 00..01010000 in binary, 00000050 in hex */
  printf("%d<<4 = %d*(2^4) = %d (0x%.8X<<4 = 0x%.8X)\n", x,x,y,x,y);
    
  return 0;
}