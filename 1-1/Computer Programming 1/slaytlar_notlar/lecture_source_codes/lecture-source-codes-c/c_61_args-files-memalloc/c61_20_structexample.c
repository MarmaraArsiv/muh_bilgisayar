/* struct keyword and some properties */ 


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*regular struct definition*/
struct first_struct{
	int i;
	float f;
	int* ip;
	char ca[12];
}; 

/* struct with typedef keyword */
typedef struct second_struct{
	int x;
	float y;
} struct_with_name;

/* struct that contains another type of struct and a pointer to another type of struct */ 
typedef struct third_struct{
	int x;
	struct first_struct* first;
	struct_with_name second;
} nested_struct;

void printFirstStruct(struct first_struct s);
void printSecondStruct(struct_with_name* s);
void printThirdStruct(nested_struct s);

int main(){
	int i;

	/* declarations of structs */
	struct first_struct fs;
	struct_with_name ss;
	struct third_struct ts;
	
	/* dot operator in struct */
	fs.i = 1;
	fs.f = 2.5;
	fs.ip = malloc(sizeof(int)*10);
	for(i=0;i<10;i++){
		fs.ip[i] = i;
	}
	strcpy(fs.ca, "some_string");

	ss.x = 2;
	ss.y = 5.2;
	
	ts.x = 3;
	ts.first = &fs;
	(ts.first)->f = 3.2;
	ts.second = ss;
	
	ss.x=12;
	ss.y=22.5;
	
	
	printFirstStruct(fs);
	printf("\n*------------------------------*\n\n");
	printSecondStruct(&ss);
	printf("\n*------------------------------*\n\n");
	printThirdStruct(ts);
	printf("\n");


	return 0;
}

void printFirstStruct(struct first_struct s){
	printf("A struct first_struct content:\n %d\n %f\n %d\n %s\n", s.i, s.f, s.ip[0], s.ca);
}

void printSecondStruct(struct_with_name* s){
	printf("A struct_with_name content:\n %d\n %f\n", s->x, s->y);
}

void printThirdStruct(nested_struct s){
	printf("A nested_struct content:\n %d\n", s.x);
	printFirstStruct(*(s.first));
	printSecondStruct(&(s.second));
}




