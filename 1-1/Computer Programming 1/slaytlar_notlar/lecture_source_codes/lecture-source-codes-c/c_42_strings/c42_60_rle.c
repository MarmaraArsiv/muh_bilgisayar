#include <stdio.h>
#include <string.h>

int main(int argc, const char * argv[])
{
    char input[100];
    int i, index=0;
    char pre;
    char characters[100];
    int occurence[100]={0};

    puts("Enter input string:");
	//scanf("%50s",input);
    gets(input);
    printf("\nInput: %s", input);

    pre=input[0];
    characters[index]=input[0];
    occurence[index]=1;
    for(i=1;i<strlen(input);i++){
        if(pre==input[i]){
            occurence[index]++;
        }
        else{
            pre=input[i];
            index++;
            characters[index]=input[i];
            occurence[index]=1;
        }
    }
    printf("\nOUTPUT:");
    for(i=0;i<=index;i++)
        printf("%d%c",occurence[i],characters[i]);


    return 0;
}
