#include <stdio.h>
#include <string.h>

int main(int argc, const char * argv[])
{
    char input[100];
    int i, palindrome=1;

    puts("Enter input string:");
	//scanf("%50s",input);
    gets(input);
	printf("\nInput: %s\n", input);

    for(i=0;i<strlen(input)/2;i++){
        if(input[i]!=input[strlen(input)-1-i]){
            palindrome=0;
            break;
        }
    }
    if(palindrome)
        printf("\"%s\" is a palindrome\n",input);
    else
        printf("\"%s\" is not a palindrome\n",input);
    return 0;
}
