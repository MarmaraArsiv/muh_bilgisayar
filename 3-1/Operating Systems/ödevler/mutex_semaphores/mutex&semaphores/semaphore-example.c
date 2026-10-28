#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

#define NUM_THREADS 5

sem_t semaphore_queue;
               
void* print(void* order){
 printf("\nI am thread %d and I am in the semaphore queue now\n",(int)pthread_self());
 sem_wait(&semaphore_queue); //threads wait in this semaphore queue
 printf("\n%d-) I am thread %d and I have passed the semaphore queue\n",(int *)order,(int)pthread_self());
 sleep(3); //wait for 3 seconds
 printf("I am existing now. (My ID: %d )\n",(int)pthread_self());
 sem_post(&semaphore_queue);
 return NULL;
 }

int main(int argc, char *argv[])
{
  int i;  
  sem_init (&semaphore_queue,0,2); //initialize the semaphore. The last paramater identifies the number of threads allowed to run the code between "sem_wait" and "sem_post" concurrently
  
  //**** try for the different number of allowed threads such as:
  // sem_init (&semaphore_queue,0,1);
  //sem_init (&semaphore_queue,0,3);
  
  //*** sem_init (&semaphore_queue,0,0); //try this one and see whether your program finishes or not !!!!!!!!!!
  
  pthread_t threads[NUM_THREADS];
  
  for(i=0;i<NUM_THREADS;i++)
      pthread_create (&(threads[i]),NULL,&print,(void *)i); //create 3 threads

  for(i=0;i<NUM_THREADS;i++)
      pthread_join(threads[i],NULL); //wait for the termination of the threads
  
  
  return 0;
}
