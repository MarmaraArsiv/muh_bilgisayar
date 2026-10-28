	INCLUDE core_cm4_constants.s		; Load Constant Definitions
	INCLUDE stm32l476xx_constants.s      


	AREA    main, CODE, READONLY
	EXPORT	__main			; make __main visible to linker
	ENTRY			
				
__main	PROC		
	
	LDR r0, =firstStr
	LDR r1, =firstStrMod
loop LDRB r2, [r0], #1
	 
	 cmp r2, #0x5A
	 BGT notModified 		; if greater than "Z" go to notModified 
	 
	 cmp r2, #0x41
	 BLT notModified 		; if smaller than "A" go to notModified 

	 ADD r2, #0x20 			; the char is in between "A-Z" here. make the char lowercase
	 STRB r2, [r1], #1 		; store the modified char to the memory
	 
	 B checknull			; check if the null character is reached
	 
notModified STRB r2, [r1], #1; store the unmodified char to the memory
	 
checknull	cmp r2, #0 		; if not null character -> go to the beginning of the loop
			BNE loop
			
	
	LDR r0, =secondStr
	LDR r1, =secondStrMod
loop2 LDRB r2, [r0], #1
	 
	 cmp r2, #0x7A
	 BGT notModified2 		; if greater than "z" go to notModified 
	 
	 cmp r2, #0x61
	 BLT notModified2 		; if smaller than "a" go to notModified 

	 SUB r2, #0x20 			; the char is in between "a-z" here. make the char uppercase
	 STRB r2, [r1], #1	 	; store the modified char to the memory
	 
	 B checknull2
	 
notModified2 STRB r2, [r1], #1; store the unmodified char to the memory
	 
checknull2	cmp r2, #0 		; if not null character -> go to the beginning of the loop
			BNE loop2
			
stop 	B 		stop     		; dead loop & program hangs here

	ENDP
					
	ALIGN			

	AREA    myData, DATA, READWRITE
	ALIGN
firstStr DCB "aaDDzzZZ1",0 ; First null terminated string
firstStrMod   SPACE   128       ; defines 255 bytes of zeroed store
secondStr DCB "aAbB12cC1",0 ; Second null terminated string
secondStrMod   SPACE   128       ; defines 255 bytes of zeroed store
	END