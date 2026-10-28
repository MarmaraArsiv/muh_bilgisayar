#include <stdio.h>
#include <string.h>

int main(int argc, const char * argv[])
{

   char st1[6]="abdef", st2[5]="xyz";
    strcpy(st1,st2);
    st1[2]='M';
    st2[3]='N';
    printf("<st1:%s>\n",st1);
    printf("<st2:%s>\n",st2);

}
