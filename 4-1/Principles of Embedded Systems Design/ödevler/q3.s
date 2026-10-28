INCLUDE core_cm4_constants.s		; Load Constant Definitions
	INCLUDE stm32l476xx_constants.s      


	AREA    main, CODE, READONLY
	EXPORT	__main				; make __main visible to linker
	ENTRY			
				
__main	PROC		
	LDR r0, =matrix				;r0 da matrix adresi
	LDR r1, =matrixTranspose	;r1 de transpose adresi
	MOV r2, #0					;r2 de row
	MOV r3, #0					;r3 de column
loop_row	CMP	r2, #4			;row 4den büyük veya esit mi ?
	BGE exit_row				;öyle ise ilk loopdan çik
	
loop_column	CMP r3, #3			;column 3den büyük veya esit mi ?
	BGE exit_column				;öyle ise ikinci loopdan cik
	
	ADD r5,r2,r2,LSL #1			;r5 de 2 * row + row
	ADD r5,r5,r3				;r5 de 3 * row + column
	ADD	r4,r2,r3,LSL #2			;r4 de 4 * column + row 
	ADD r5,r0,r5,LSL #2			;orijinal matrix elemani adresi
	ADD r4,r1,r4,LSL #2			;transpose eleman adresi
	
	LDR	r5,[r5]					;orijinal eleman
	STR	r5,[r4]					;orijinal eleman tranpose matrixte
	
	ADD r3,r3,#1				;column++
	B	loop_column


exit_column	ADD	r2,r2,#1		;row++
	MOV r3,#0					;column sifirla
	B	loop_row

exit_row	B 	stop
	 
	
	 
	
stop 	B 		stop     		; dead loop & program hangs here

	ENDP
					
	ALIGN			

	AREA    myData, DATA, READWRITE
	ALIGN
matrix DCD 0,1,2,3,4,5,6,7,8,9,10,11
matrixTranspose DCD 0,0,0,0,0,0,0,0,0,0,0,0
	END