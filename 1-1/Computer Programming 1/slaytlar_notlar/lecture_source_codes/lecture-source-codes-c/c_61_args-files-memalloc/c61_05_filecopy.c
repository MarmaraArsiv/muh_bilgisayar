/* Figure 11.1  Program to Make a Backup Copy of a Text File */
/*
 * Makes a backup file. Repeatedly prompts for the name of a file to
 * back up until a name is provided that corresponds to an available
 * file. Then it prompts for the name of the backup file and creates
 * the file copy.
 */

#include <stdio.h>
#define STRSIZ 80

int
main(void)
{
      char in_name[STRSIZ],  /* strings giving names                     */
           out_name[STRSIZ]; /*    of input and backup files             */
      FILE *inp,             /* file pointers for input and              */
           *outp;            /*     backup files                         */
      char ch;               /* one character of input file              */

      /* Get the name of the file to back up and open the file for input */
      printf("Enter name of file you want to back up> ");
      for (scanf("%s", in_name); (inp = fopen(in_name, "r")) == NULL; scanf("%s", in_name)) {
      	printf("Cannot open %s for input\n", in_name);
     	printf("Re-enter file name> ");
      }

      /* Get name to use for backup file and open file for output        */
      printf("Enter name for backup copy> ");
      for (scanf("%s", out_name);
           (outp = fopen(out_name, "w")) == NULL;
           scanf("%s", out_name)) {
         printf("Cannot open %s for output\n", out_name);
         printf("Re-enter file name> ");
      }

      /* Make backup copy one character at a time                        */
      for (ch = getc(inp); ch != EOF; ch = getc(inp))
         putc(ch, outp);

      /* Close files and notify user of backup completion                */
      fclose(inp);
      fclose(outp);
      printf("Copied %s to %s.\n", in_name, out_name);

      return(0);
}

