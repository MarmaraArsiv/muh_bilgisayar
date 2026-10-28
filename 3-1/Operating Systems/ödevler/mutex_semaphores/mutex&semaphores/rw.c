#include <sys/time.h>
#include <stdio.h>
#include <pthread.h> 
#include <errno.h>

pthread_mutex_t rw_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t reader_mutex = PTHREAD_MUTEX_INITIALIZER;
int num_readers = 0;


main()  
{
  pthread_t reader_thread1; 
  pthread_t reader_thread2; 
  pthread_t reader_thread3; 
  pthread_t reader_thread4; 
  pthread_t writer_thread1; 
  pthread_t writer_thread2; 
  void *reader();
  void *writer();

    pthread_create(&reader_thread1,NULL,reader,NULL);
    pthread_create(&reader_thread2,NULL,reader,NULL);
    pthread_create(&reader_thread3,NULL,reader,NULL);
    pthread_create(&reader_thread4,NULL,reader,NULL);
    pthread_create(&writer_thread1,NULL,writer,NULL);
    pthread_create(&writer_thread2,NULL,writer,NULL);
    pthread_join(writer_thread2,NULL);  /* each will write 100 times */
    pthread_join(writer_thread1,NULL);

}

void *reader()
{
  int i = 0;
  while  (1) {
    pthread_mutex_lock(&reader_mutex);
    num_readers++;
    if (num_readers == 1) pthread_mutex_lock(&rw_mutex);
    pthread_mutex_unlock(&reader_mutex);

    printf("start reading\n");
    sleep(1);
    printf("finish reading\n");

    pthread_mutex_lock(&reader_mutex);
    num_readers--;
    if (num_readers == 0) pthread_mutex_unlock(&rw_mutex);
    pthread_mutex_unlock(&reader_mutex);
    sleep(i%3);
    i++;
  }
  pthread_exit(NULL);
}

void *writer()
{ int i;
  for (i=0;i<100;i++) {
    pthread_mutex_lock(&rw_mutex);
    printf("start writing \n");
    sleep(1);
    printf("finish writing \n");
    pthread_mutex_unlock(&rw_mutex);
    sleep(i%2);
  }
  pthread_exit(NULL);
}
 
