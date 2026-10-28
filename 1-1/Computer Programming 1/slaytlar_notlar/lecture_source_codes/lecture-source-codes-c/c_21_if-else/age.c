#include <stdio.h>

int main(){
    int age;

    printf("Enter age:");
    scanf("%d", &age);

    if (age<=1)
        printf("infant");
    else if (age<=3)
        printf("toddler");
    else if (age<=10)
        printf("child");
    else if (age<=18)
        printf("adolescent");
    else if (age<=25)
        printf("young");
    else if (age<=39)
        printf("adult");
    else if (age<=65)
        printf("middle-aged");
    else
        printf("elderly");


    return 1;
}
