#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Function to test the code that parses and executes the "and" instruction
void test_and_instruction()
{
    printf("TEST");
    // Define test input and expected output
    char* line = "and 2 3 1";
    long int expected_output = 0b0111010001010000;

    // Execute code that parses and executes the "and" instruction
    char* token = strtok(line, " ");
    if (strcmp(token, "and") == 0)
    {
        // Parse operands
        char* operand1 = strtok(NULL, " ");
        char* operand2 = strtok(NULL, " ");
        char* operand3 = strtok(NULL, " ");
        int r1 = atoi(operand1);
        int r2 = atoi(operand2);
        int r3 = atoi(operand3);

        // Build instruction
        char instruction[16 + 1];  // +1 for null terminator
        snprintf(instruction, sizeof(instruction), "0111010%03d%03d%03d", r2, r3, r1);

        // Store instruction in program array
        long int program[1];
        program[0] = strtol(instruction, NULL, 2);  // Convert instruction to long int

        // Check output
        if (program[0] == expected_output)
        {
            printf("Test passed\n");
        }
        else
        {
            printf("Test failed: expected %ld, got %ld\n", expected_output, program[0]);
        }
    }
}

int main()
{
    test_and_instruction();
    return 0;
}
