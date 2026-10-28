#include <stdio.h>

int main(){
    enum day_type {MON=1,TUE,WED,THU,FRI,SAT,SUN} day;



    printf("Enter day:");
    scanf("%d", &day);
    switch (day){
        case SUN: printf("Sunday\n"); //break;
        case WED: printf("Wednesday\n"); //break;
        case TUE: printf("Tuesday\n"); //break;
        case THU: printf("Thursday\n"); //break;
        case FRI: printf("Friday\n"); //break;
        case SAT: printf("Saturday\n"); //break;
        case MON: printf("Monday\n"); //break;
        default:  printf("Incorrect day\n"); //break;
    }


    return 1;
}
