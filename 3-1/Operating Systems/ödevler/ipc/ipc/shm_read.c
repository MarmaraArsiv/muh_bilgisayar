 /*
 * Shared memory example for Telrad
 * Written by Ori Idan
 */
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>

void waitsemaphore(int semid) {
        struct sembuf sem_op = {0, -1, 0};
        if(semop(semid, &sem_op, 1) < 0)
                perror("waitsemaphore");
}

int main(void) {
        int ipckey, shmid, semid;
        char *shm;
        char last[256];

        ipckey = ftok("/dev/null", 0);
        shmid = shmget(ipckey, 256, 0666);
        if(shmid < 0) {
                perror("shmget");
                return 1;
        }
        shm = (char *)shmat(shmid, NULL, 0);
        semid = semget(ipckey, 1, 0666);
        if(semid < 0) {
                perror("semget");
                return 1;
        }
        while(1) {
                waitsemaphore(semid);
                printf(shm);
                if(!strncmp(shm, "exit", 4))
                        break;
        }
        shmdt(shm);     /* detach pointer from process memory space */
        shmctl(shmid, IPC_RMID, NULL);  /* remove segment from system */
        semctl(semid, IPC_RMID, 0);     /* remove semaphore */
        return 0;
}

