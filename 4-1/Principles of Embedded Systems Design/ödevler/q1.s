	INCLUDE core_cm4_constants.s		; Load Constant Definitions
	INCLUDE stm32l476xx_constants.s      


	
	AREA    main, CODE, READONLY
	EXPORT	__main				; make __main visible to linker
	ENTRY			
				
__main	PROC
    LDR r0, =str1				; Load the address of str1     
    MOV r1, #0					; Initialize the result to 0

loop
    LDRB r3, [r0], #1			; Load a byte pointed to by r0
    CMP r3, #0					; Check is it the end of the string
    BEQ done					; If end of string, exit loop
    MOV r4, #16					; Load the base (16) into r4
    MUL r1, r1, r4				; Multiply the result by the base
    CMP r3, #'A'				; Check if the digit is between A-F
    BLT add_digit				; If value is less than 'A' (ASCII: 65) this means it's a digit (0-9)
    SUB r3, r3, #55				; If it's an ASCII char we need to subtract 55 because ASCII A has value 65 but it should be 10 in decimal
	;CMP r3, #'a'				; ASCII 'a' (97) is greater than ASCII 'A' (55)
	;BLT add_lowercase			; If it's a lowercase letter we need to do one more subtraction
add_digit
    ADD r1, r1, r3				; If it's a digit we can add directly
    B loop						; Skip to the next digit
add_lowercase
	SUB r3, r3, #32				; If it's a lowercase letter subtract extra 32 due to 97(a)-55(A)=32
	B add_digit
done
    MOV r6, r1					; Move the result to r6
	
	LDR r0, =str2				; Load the address of str2  
	MOV r1, #0					; Initialize the result to 0

loop2
    LDRB r3, [r0], #1			; Load a byte pointed to by r0
    CMP r3, #0					; Check is it the end of the string
    BEQ done2					; If end of string, exit loop
    MOV r4, #16					; Load the base (16) into r4
    MUL r1, r1, r4				; Multiply the result by the base
    CMP r3, #'A'				; Check if the digit is between A-F
    BLT add_digit2				; If value is less than 'A' (ASCII: 65) this means it's a digit (0-9)
    SUB r3, r3, #55				; If it's an ASCII char we need to subtract 55 because ASCII A has value 65 but it should be 10 in decimal
	;CMP r3, #'a'				; ASCII 'a' (97) is greater than ASCII 'A' (55)
	;BLT add_lowercase2			; If it's a lowercase letter we need to do one more subtraction
add_digit2
    ADD r1, r1, r3				; If it's a digit we can add directly
    B loop2						; Skip to the next digit
add_lowercase2
	SUB r3, r3, #32				; If it's a lowercase letter subtract extra 32 due to 97(a)-55(A)=32
	B add_digit2
done2
    MOV r7, r1					; Move the result to r7
	
	
	SUB r8, r6, r7				; r8=r6-r7=str1-str2
	LDR r0, =result				; Load address of result into r0
	STR r8, [r0]				; Store the result in memory
	
stop
	B stop               ; dead loop & program hangs here
	
	ENDP
					
	ALIGN			

	AREA    myData, DATA, READWRITE
	ALIGN
str1 DCB "DEFA",0
str2 DCB "ABC",0
result DCD 0
	END