#include <stdio.h>
#include <string.h>

int main(void){
    struct stu_info {
        char name[41];
        long int id;
        char dept[16];
        short int clas;
        float gpa;
    } stu1;

    struct stu_info stu2;

    strcpy(stu1.name,"Ahmet");
    stu1.id=198;
    strcpy(stu1.dept,"EE");
    stu1.clas=2;
    stu1.gpa=3.5;

    strcpy(stu2.name,"Ayse");
    stu2.id=1234;
    strcpy(stu2.dept,"CSE");
    stu2.clas=1;
    stu2.gpa=1.78;

    printf(":: Student1 Info\n");
    printf("Name: %s\nID: %ld\nDept: %s\nClass: %d\nGPA: %.2f\n",stu1.name,stu1.id,stu1.dept,stu1.clas,stu1.gpa);
    printf("\n\n:: Student2 Info:\n");
    printf("Name: %s\nID: %ld\nDept: %s\nClass: %d\nGPA: %.2f\n",stu2.name,stu2.id,stu2.dept,stu2.clas,stu2.gpa);
}
