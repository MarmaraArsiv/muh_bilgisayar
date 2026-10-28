#include <stdio.h>

#include <unistd.h>

#include <errno.h>

#include <stdlib.h>

#include <string.h>

#include<sys/wait.h>

#include<sys/types.h>
#include<fcntl.h>



#define CHAR_LIMIT 100

struct aliasData{
    int present;
    char alias[50];
    char command[100];
};

typedef struct aliasData aliasData;

struct childP{

    pid_t pid;

    struct childP *next;

};

typedef struct childP childP;

aliasData aliases[50] = {0};
int aliasCount = 0;








pid_t fg_pid;

childP * child_head = NULL;




static void signal_handler(int signo){

    if(fg_pid!=0){

        kill(fg_pid, SIGKILL);

    }else{

        printf("There is nothing to kill.");

    }

    fflush(stdout);

    printf("\nmyshell: ");

}

void setup(char inputBuffer[], char *args[],int *background)

{

    int length, /* # of characters in the command line */

    i,      /* loop index for accessing inputBuffer array */

    start,  /* index where beginning of next command parameter is */

    ct;     /* index of where to place the next parameter into args[] */



    ct = 0;



    /* read what the user enters on the command line */

    length = read(STDIN_FILENO,inputBuffer,CHAR_LIMIT);



    /* 0 is the system predefined file descriptor for stdin (standard input),

       which is the user's screen in this case. inputBuffer by itself is the

       same as &inputBuffer[0], i.e. the starting address of where to store

       the command that is read, and length holds the number of characters

       read in. inputBuffer is not a null terminated C-string. */



    start = -1;

    if (length == 0)

        exit(0);            /* ^d was entered, end of user command stream */



/* the signal interrupted the read system call */

/* if the process is in the read() system call, read returns -1

  However, if this occurs, errno is set to EINTR. We can check this  value

  and disregard the -1 value */

    if ( (length < 0) && (errno != EINTR) ) {

        perror("error reading the command");

        exit(-1);           /* terminate with error code of -1 */

    }



    //printf(">>%s<<",inputBuffer);

    for (i=0;i<length;i++){ /* examine every character in the inputBuffer */



        switch (inputBuffer[i]){

            case ' ':

            case '\t' :               /* argument separators */

                if(start != -1){

                    args[ct] = &inputBuffer[start];    /* set up pointer */

                    ct++;

                }

                inputBuffer[i] = '\0'; /* add a null char; make a C string */

                start = -1;

                break;



            case '\n':                 /* should be the final char examined */

                if (start != -1){

                    args[ct] = &inputBuffer[start];

                    ct++;

                }

                inputBuffer[i] = '\0';

                args[ct] = NULL; /* no more arguments to this command */

                break;



            default :             /* some other character */

                if (start == -1)

                    start = i;

                if (inputBuffer[i] == '&'){

                    *background  = 1;

                    inputBuffer[i-1] = '\0';

                    i++;

                    args[ct] = NULL;

                }

        }

    }

}



void callAlias(char *args[]){

    int aliasArrSize =  sizeof(aliases)/sizeof(aliases[0]);

    char command[80] = "";
    int flag = 0;
    int bias = 0;
    int i= 1;
    int complete =0;
    while(args[i]!= NULL){
        if(complete==1) break;
        if(flag == 0 ){
            if(args[i][0]=='\"'){
                flag =1;
                char subcommand[20];
                int trim=0;
                if(args[i][strlen(args[i])-1]=='\"'){
                    complete =1;
                    trim =2;
                }
                else{
                    trim =1;
                }
                strncpy(subcommand,args[1]+1, strlen(args[1])-trim);
                strcat(command,subcommand);
                if(complete==0){
                    strcat(command," ");
                }
            }
        }
        else{
            int a=0;

            while(a< strlen(args[i])){
                if(args[i][a]=='\"' || args[i][a]<32){
                    char blank = ' ';
                    strncat(command,&blank,1);
                    goto JUMP;
                }
                strncat(command,&args[i][a],1);
                a++;
            }



        }
        i++;

    }
    JUMP:
    if(complete == 1){
        bias =0;
    } else bias =1;

    int iter =0;
    while (iter < aliasArrSize){
        if(aliases[iter].present==0){
            strcpy(aliases[iter].command, command);
            strcpy(aliases[iter].alias, args[i+ bias]);
            aliases[iter].present = 1;
            aliasCount++;
            printf("New Alias: %s= %s\n", aliases[iter].alias, aliases[iter].command);
            break;
        }
        iter++;
    }



}

void unalias(char *args[]){
    int aliasArrSize =  sizeof(aliases)/sizeof(aliases[0]);

    if(aliasCount!=0){
        int iter = 0;

        while (iter < aliasArrSize){
            if(aliases[iter].present == 0) continue;

            if(strcmp(aliases[iter].alias, args[1]) == 0){
                aliases[iter].present = 0;
                aliasCount--;
                printf("Alias removed.\n");
                break;
            }
            iter++;
        }

    }
}


void listAlias(){
    int aliasArrSize =  sizeof(aliases)/sizeof(aliases[0]);

    int iter =0;
    while (iter < aliasArrSize){
        if(aliases[iter].present==1){
            printf("%s -> %s\n", aliases[iter].alias, aliases[iter].command);
        }
        iter++;

    }

}

int searchInAlias(int background, char **args) {
    int aliasArrSize =  sizeof(aliases)/sizeof(aliases[0]);

    int iter = 0;
    while(iter < aliasArrSize){

        if(strcmp(args[0],aliases[iter].alias) == 0){
            pid_t pid;

            if((pid=fork())<0){
                fprintf(stdout,"Fork failed!\n");
            } else if(pid == 0){
                system(aliases[iter].command); /*run command in the child*/
                exit(0);
            }

            if(background == 0){

                fg_pid = pid;

                waitpid(pid, NULL, 0); /*run in foreground if background == 0*/

                fg_pid = 0;

                return 1;

            }else{

                if(child_head == NULL){

                    child_head = (childP*)malloc(sizeof(childP));

                    child_head->pid = pid;

                    child_head->next = NULL;

                }else{

                    childP *new_child;

                    new_child = (childP*)malloc(sizeof(childP));

                    new_child->pid = pid;

                    new_child->next = child_head;

                    child_head = new_child;

                }

                printf("[%d] : %d \n", background, pid);

                return 1;

            }

        }

        iter++;
    }

    return 0;

}

void fg(){

    if(child_head != NULL){

        pid_t pid = child_head->pid;



        if(!tcsetpgrp(getpid(), getpgid(pid))){

            fprintf(stdout, "Error!");

        }else{

            fg_pid = pid;

            waitpid(pid,NULL,0);

            fg_pid = 0;

        }

        childP * temp = child_head;

        child_head = child_head->next;


    } else{

        fprintf(stderr, "There is no background process.\n");

        return;

    }

}

int redirect(char *args[], int background, char *envPath) { /*checks for any redirection command and execuate if exists*/

    /*find any redirect arg if exists*/
    char *args_clones[CHAR_LIMIT / 2 + 1];
    int i = 0, ptr1 = 0, ptr2 = 0, check=0;
    int size = sizeof args
    for(i; i< size;i++) {
        if(strcmp("<", args[i]) == 0 ||strcmp(">>", args[i]) == 0 ||
           strcmp("2>", args[i]) == 0 || strcmp(">", args[i]) == 0 ){
            check =1;
            break;
        }
    }
/*    while (args[i] != NULL) {
        if (strcmp("<", args[i]) == 0 || strcmp(">>", args[i]) == 0 ||
            strcmp("2>", args[i]) == 0 || strcmp(">", args[i]) == 0) { /*check if there is any redirection command
            flag = 1;
            break;
        }
        i++;
    } */

    if (args[i] == NULL){ /*return 0 if there is no redirection command*/
        return 0;
    }

    if(args[i+1] == NULL){
        fprintf(stdout,"Missing argument.\n");
    }

    pid_t pid = fork();
    if (pid < 0)
        fprintf(stderr, "Fork failed!\n");

    if (pid == 0) {
        if(strcmp(">", args[i]) == 0){
            int fd;
            args[i]=NULL;
            fd = open(args[i+1], O_WRONLY | O_TRUNC | O_CREAT, 0644);
            dup2(fd, STDOUT_FILENO);
            close(fd);

        }else if(strcmp(">>", args[i]) == 0){
            int fd;
            args[i]=NULL;
            fd = open(args[i+1], O_WRONLY | O_APPEND | O_CREAT, 0644);
            dup2(fd, STDOUT_FILENO);
            close(fd);

        }else if(strcmp("2>", args[i]) == 0){
            int fd;
            fd = open(args[i+1], O_WRONLY | O_APPEND | O_CREAT, 0644);
            dup2(fd, STDERR_FILENO);
            close(fd);

        }else if(strcmp("<", args[i]) == 0){
            if(args[i+2]!= NULL && strcmp(">", args[i+2]) == 0){
                fflush(stdout);
                if(args[i+3] == NULL){
                    fprintf(stdout, "Missing argument!\n");
                    return 0;
                }
                int fd, fd2;
                fd = open(args[i+1], O_RDONLY, 0644);
                fd2 = open(args[i+3],O_WRONLY | O_TRUNC | O_CREAT, 0644);
                dup2(fd2, STDOUT_FILENO);
                dup2(fd, STDIN_FILENO);
                close(fd2);
                args[i]=NULL;
            }else{
                int fd;
                args[i]=NULL;
                fd = open(args[i+1], O_RDONLY, 0644);
                dup2(fd, STDIN_FILENO);
                close(fd);
            }
        }

        if(searchInAlias(background, args))
            return 1;


        char *token = strtok(envPath, ":");
        char temp[80] = "";
        strcpy(temp, token);
        strcat(temp, "/");
        strcat(temp, args[0]);
        while (execl(temp, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9],
                     args[10], args[11], args[12], NULL) != 0) {

            token = strtok(NULL, ":");
            strcpy(temp, token);
            strcat(temp, "/");
            strcat(temp, args[0]);
        }
        fprintf(stdout, "Command %s not found\n", args[0]);
        exit(0);
    }

    if (background != 0) {
        if (child_head == NULL) {
            child_head = (childP *) malloc(sizeof(childP));
            child_head->pid = pid;
            child_head->next = NULL;
        } else {
            childP *new_child;
            new_child = (childP *) malloc(sizeof(childP));
            new_child->pid = pid;
            new_child->next = child_head;
            child_head = new_child;
        }
        printf("[%d] : %d \n", background, pid);

    } else {
        fg_pid = pid;
        waitpid(pid, NULL, 0);
        fg_pid = 0;
    }

}

void executeCommand(int background, char **args, char *envPath) {

    if(searchInAlias(background, args)) return;

    pid_t pid = fork();
    if (pid == 0){

        char *token = strtok(envPath, ":");


        char temp[80] = "";

        strcpy(temp, token);

        strcat(temp, "/");

        strcat(temp, args[0]);

        while(execl(temp, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10], args[11], args[12], NULL) != 0){



            token = strtok(NULL, ":");

            strcpy(temp ,token);

            strcat(temp, "/");

            strcat(temp, args[0]);

        }


        exit(0);

    }



    if (background != 0){

        if(child_head == NULL){

            child_head = (childP*)malloc(sizeof(childP));

            child_head->pid = pid;

            child_head->next = NULL;

        }else{

            childP *new_child;

            new_child = (childP*)malloc(sizeof(childP));

            new_child->pid = pid;

            new_child->next = child_head;

            child_head = new_child;

        }

        printf("[%d] : %d \n", background, pid);

    }else{

        fg_pid = pid;

        waitpid(pid,NULL,0);

        fg_pid = 0;

    }
}

void* shutDown(){
    printf("\nHave a wonderful day!\nShutting down...\n");
    fflush(stdout);

    int countdown = 5;
    while(countdown>0){
        printf("%d ",countdown);
        fflush(stdout);
        sleep(1);
        countdown--;
    }
    printf("\n");
    exit(0);
}

void interpreter(int background, char *args[], char *envPath){


    if (strcmp(args[0], "alias") == 0){

        if(args[1] == NULL){
            printf("Error: Check your arguments!\n");
            return;
        }
        else if(strcmp(args[1], "-l") == 0){
            listAlias();
            return;
        }

        callAlias(args);
        return;

    }else if (strcmp(args[0], "exit") == 0){
        shutDown();

    } else if (redirect(args, background, envPath)){

        return;

    } else if (strcmp(args[0], "fg") == 0){

        fg();

        return;

    }  else if (strcmp(args[0], "unalias") == 0){

        unalias(args);

        return;

    }else{
        executeCommand(background, args, envPath);
        return;

    }

}



int main(int argc, char *argv[])

{

    char *envPath = getenv("PATH");
    char input[CHAR_LIMIT]; /*buffer to hold command entered */
    int background; /* equals 1 if a command is followed by '&' */
    char *args[CHAR_LIMIT/2 + 1]; /*command line arguments */



    struct sigaction act;

    act.sa_handler = signal_handler;            /* set up signal handler */

    act.sa_flags = SA_RESTART;

    if ((sigemptyset(&act.sa_mask) == -1) ||

        (sigaction(SIGTSTP, &act, NULL) == -1)) {

        fprintf(stdout,"Failed to set SIGTSTP handler!\n");

        return 1;

    }



    while (1){

        background = 0;

        printf("myshell: ");

        /*setup() calls exit() when Control-D is entered */

        fflush(NULL);

        setup(input, args, &background);



        if(args[0] == NULL) /*if user enter without anything*/

        {

            continue;

        }

        interpreter(background, args, envPath);
    }



}

