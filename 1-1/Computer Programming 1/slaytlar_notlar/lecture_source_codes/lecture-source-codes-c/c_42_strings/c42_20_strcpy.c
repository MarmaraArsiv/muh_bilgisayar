#include <stdio.h>
#include <string.h>

int main(int argc, const char * argv[])
{

   char st1[5]="abcd", st2[5]="xyz";
   char st3[5]; int i;

   strcpy(st3,st1);

   printf("%s %s %s\n", st1, st2, st3);

   for(i=0;i<strlen(st2);i++)
    st3[i]=st2[i];

   printf("%s %s %s\n", st1, st2, st3);

   st3[strlen(st2)]='\0';

   printf("%s %s %s\n", st1, st2, st3);

}
