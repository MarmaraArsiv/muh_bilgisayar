#include <stdio.h>

struct person_info {
   enum {student, staff} type;
   union {
      struct stu_info {
         char name[41];
         long int id;
         char dept[16];
         short int class;
         float gpa;
      } student;
      
      struct staff_info {
         char name[41];
         long int SSid;
         enum {assist, prof, personnel} status;
         int salary;
      } staff;
      
   } info;
};

void read_student(struct stu_info *s)
{
	//Implement read_student function
}
void read_staff(struct staff_info *s)
{
	//Implement read_staff function	
}


int main(void)
{ 
   struct person_info person[100];
   int i;

   for (i=0; i<100; i++)
   {  printf("Enter 0 for student, 1 for staff: ");
      scanf("%d", &person[i].type);
      if (person[i].type==student)
         read_student(&person[i].info.student);
      else
 		  read_staff(&person[i].info.staff);
   }
   return 0;

} 



