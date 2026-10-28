/*
 * Shared memory example for Telrad
 * This example will read a string from user and write it to shared memory
 */
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>

#define IPCKEY 5768

void incsemaphore(int semid)
{
        struct sembuf sem_op = {0, 1, 0};
        if(semop(semid, &sem_op, 1) < 0)
                perror("incsemaphore");
}

int main(void)
{
        int ipckey, shmid, semid;
        char *shm;
        
        ipckey = ftok("/dev/null", 0);
        shmid = shmget(ipckey, 256, IPC_CREAT | 0666);
        if(shmid < 0) {
                perror("shmwrite");
                return 1;
        }
        shm = (char *)shmat(shmid, NULL, 0);
        if(shm == NULL) {
                perror("shmwrite");
                return 1;
        }
        /* Get semaphore */
        semid = semget(ipckey, 1, IPC_CREAT | 0666);
        if(semid < 0) {
                perror("semget");
                return 1;
        }
        while(1) {
                printf("String to write to shared memory: ");
                fgets(shm, 256, stdin);
                if(!strncmp(shm, "exit", 4))
                        break;
                incsemaphore(semid);
        }
        shmdt(shm);     /* Detach from process space */
        shmctl(shmid, IPC_RMID, NULL);  /* remove shared memory segment from system */
        semctl(semid, IPC_RMID, 0); /* remove semaphore */
}
