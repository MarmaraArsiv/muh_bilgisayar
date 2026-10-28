#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>


#define N 20 // fixed size for buffer items
#define P_RAND_SEED 1234
#define C_RAND_SEED 1235
#define timeToProduce 10
#define timeToConsume 10
#define TRUE 1
#define FALSE 0

// These are globals and shared
int runFlag;
identify prod_thrd, cons_thrd; //thread identifiers for producer and consumer threads
SEMAPHORE empty;			// empty semaphore identifier
SEMAPHORE sem_t full;			// full semaphore identifier
MUTEX bufManip;	// bufManip mutex identifier
int itCount;


struct buffer_t {
int buffer[N];
unsigned int nextFull;
unsigned int nextEmpty;
} items;



void* producer (void *ip);
void* consumer (void *ip);


// The main program establishes the shared information used by
// the producer and consumer threads
int main(int argc, char *argv[])
{
	// local variables
	int runTime;
	int i;
	int error;

	runFlag = TRUE; // set runFlag true

	// get a value for runtime
	runTime = atoi(argv[1]);
	if ( runTime < 0 ){
		fprintf(stderr,"An integer >= 0 is required..\n");
		return -1;
	}

	//initialize synchronization objects
	initialize empty semaphore	// sem_init() function initialize the semaphore with the given value
							    // empty semaphore should be started with the value N, because all items are empty..
	
	initialize full semaphore  // full semaphore should be started with 0, because of thre is no produced item by now..
	
	initialize bufManip mutex	// initialize bufManip mutex 
	

	// initialize buffer pool
	items.nextEmpty = 0;
	items.nextFull = 0;
	for (i = 0; i < N; i++)
	{
		items.buffer[i] = 0;
	}

	// create producer and consumer threads
	//pthread_create() function creates the thread with the expected function and given arguments 
	// prod_thrd is thread identifier, producer is function name and items is the thread argument
	if (error = CREATE PRODUCER THREADS) { 
       		 fprintf(stderr, "Failed to create thread:%s\n", strerror(error));
		 // if there is a problem while creating thread give me the error type
         	 return 1;
      	}
	

	if (error = CREATE CONSUMER THREADS) {
       		 fprintf(stderr, "Failed to create thread:%s\n", strerror(error));
         	 return 1;
      	}	

	// sleep while the children work...
	sleep(runTime);
	runFlag = FALSE; // signal children to terminate

	// wait for producer & consumer to terminate
	if (error = WAIT PRODUCER THREADS) {
         	fprintf(stderr, "Failed to join thread:%s\n", strerror(error));
         	return 1;
      	}

	if (error = WAIT CONSUMER THREADS, NULL)) {
         	fprintf(stderr, "Failed to join thread:%s\n", strerror(error));
         	return 1;
      	}
	
	destroy seamphores 	

	// now we can quit
	printf("Main thread: Terminated\n");
	pthread_exit(NULL);
	return 0;
}

void* producer (void *ip)
{
	//local variables
	struct buffer_t *itemPtr;

	itemPtr = (struct buffer_t *) ip; // cast buffer pointer
	srand(P_RAND_SEED); // set random number seed
	itCount = 100;
	while (runFlag)
	{
	// produce the buffer
	usleep(rand() % timeToProduce); // simulate production time
	// get an empty buffer
	synchronize with empty semaphore
	// manipulate the buffer pool
	synchronize with bufManip mutex

	
	itemPtr->buffer[itemPtr->nextEmpty] = itCount++;
	itemPtr->nextEmpty = (itemPtr->nextEmpty+1) % N;

	synchronize with bufManip mutex 
	synchronize with full semaphore 
	}
	// terminate
}

void* consumer(void *ip)
{
	//local variables
	struct buffer_t *itemPtr;

	itemPtr = (struct buffer_t *) ip;
	srand(C_RAND_SEED);
	runFlag = TRUE;

	while (runFlag)
	{
	// get a full buffer
	
	synchronize with full semaphore 

	synchronize with bufManip mutex 

	itCount = itemPtr->buffer[itemPtr->nextFull];
	itemPtr->nextFull = (itemPtr->nextFull+1) % N;


	synchronize with bufManip mutex 

	synchronize with empty semaphore 
	// consume the buffer
	usleep(rand()%timeToConsume); // simulate consumption
	
	
	}
	// terminate
}




