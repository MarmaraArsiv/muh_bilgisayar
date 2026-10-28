.data
menuText: .asciiz "\nMain Menu:\n1. Base Converter\n2. Add Rational Number\n3. Text Parser\n4. Mystery Matrix Operation\n5. Exit\nPlease select an option: "
newline: .asciiz "\n"
slash: .asciiz "/"
equalSign: .asciiz " = "
plusSign: .asciiz " + "
msg1:.asciiz "Enter a binary number(without empty space between bytes): "
msg2:.asciiz "\nResult: "
msg3:.asciiz "\nEnter the type: "
empty:.space 120
sumMsg:.asciiz "\nOutput: "
n1msg: .asciiz "\nEnter the first number's numerator\n"
d1msg: .asciiz "\nEnter the first number's denominator\n"
n2msg: .asciiz "\nEnter the second number's numerator\n"
d2msg: .asciiz "\nEnter second number's denominator\n"
.text
main:

	menu:
        		li $v0, 4
		la $a0, menuText
		syscall
		li $v0, 5
		syscall
		move $t0, $v0
		beq $t0, 2, addRational
		beq $t0, 1, getNumber
		
	addRational:
	
		li $v0, 4
		la $a0, n1msg
		syscall
	
		li $v0, 5
		syscall
		move $s0, $v0
		
		li $v0, 4
		la $a0, d1msg
		syscall
		
		li $v0, 5
		syscall
		move $s1, $v0
		
		li $v0, 4
		la $a0, n2msg
		syscall
		
		li $v0, 5
		syscall
		move $s2, $v0
		
		li $v0, 4
		la $a0, d2msg
		syscall
		
		li $v0, 5
		syscall
		move $s3, $v0
		
		move $a0, $s0
		move $a1, $s1
		move $a2, $s2
		move $a3, $s3
	jal RationalSum
	j menu
	
	
RationalSum:
	addi $sp, $sp, -20
	sw $ra, 0($sp)
	sw $a0, 4($sp)
	sw $a1, 8($sp)
	sw $a2, 12($sp)
	sw $a3, 16($sp)
	
	lw $a0, 8($sp)
	lw $a1, 16($sp)
	jal GCD
	lw $a0, 4($sp)
	lw $a1, 8($sp)	#restored
	move $s0, $v0 #return value is stored
	move $s1, $a1  #oldD1
	move $s2, $a3  #oldD2
	move $s5, $a0  #old N1 (for printing in the end)
	move $s6, $a2  #old N2 (For the same storing reason)
	
	li $t0, 1
	bne $s0, $t0, RationalElse
	
	mult $a0, $s2
	mflo $a0 	#n1
	mult $a1, $s2
	mflo $a1 	#d1
	
	mult $a2, $s1
	mflo $a2 	#n2
	mult $a3, $s1
	mflo $a3 	#d2
	j RationalElseEnd

RationalElse:
	div $s1, $s0
	mflo $t5		#divisor1
	div $s2, $s0
	mflo $t6		#divisor2
	
	mult $a0, $t6
	mflo $a0		#n1*=divisor2
	mult $a1, $t6
	mflo $a1		#d1*=divisor2
	
	mult $a2, $t5
	mflo $a2		#n2*=divisor1
	mult $a3, $t5
	mflo $a3		#d2*=divisor1
RationalElseEnd:
	add $s3, $a0, $a2	#newN = n1+n2
	move $a0, $s3
	jal GCD
	move $s4, $v0 	#return value is stored (gcd2)
	beq $s4,$t0 RationalElse2	#if gcd2 == 1, skip
	
	div $a1, $s4
	mflo $a1
	div $s3, $s4
	mflo $s3

RationalElse2:

	li $v0, 1
    	move $a0, $s5		#print old n1
    	syscall
	li $v0, 4 
	la $a0, slash		#print /
	syscall	
	li $v0, 1
    	move $a0, $s1		#print old d1
    	syscall
    	
	li $v0, 4 
	la $a0, plusSign		#print '+'
	syscall
    	
    	li $v0, 1
    	move $a0, $s6		#print old n2
    	syscall
	li $v0, 4 
	la $a0, slash		#print /
	syscall	
	li $v0, 1
    	move $a0, $s2		#print old d2
    	syscall
    	
    	
	li $v0, 4 
	la $a0, equalSign	#print '='
	syscall
	
	li $v0, 1
    	move $a0, $s3		#print newN
    	syscall
	li $v0, 4 
	la $a0, slash		#print /
	syscall	
	li $v0, 1
    	move $a0, $a1		#print d1
    	syscall
	
	lw $ra, 0($sp)
	lw $a0, 4($sp)
	lw $a1, 8($sp)
	lw $a2, 12($sp)
	lw $a3, 16($sp)
    	addi $sp, $sp, 20
    	jr $ra





GCD:
	addi $sp, $sp, -32
	sw $ra, 0($sp)
	sw $a0, 4($sp)
	sw $a1, 8($sp)
	sw $s4, 12($sp)
	sw $s0, 16($sp)
	sw $s1, 20($sp)
	sw $s2, 24($sp)
	sw $s3, 28($sp)
	
	li $s4, 0 #hcf = 0
	li $s0, 1   # i=1
GCDLoop:
	sle $t1, $s0, $a0	# if i <= a, t1 = 1
	sle $t2, $s0, $a1	# if i <= b, t2 = 1
	add $t3, $t1, $t2	
	li $t9, 2
	bne $t3, $t9, GCDforEnd	#if !(i<=a && i<=b) skip the loop
	
	div $a0, $s0
	mfhi $s1  #s1 = a%i
	
	div $a1, $s0
	mfhi $s2  #s2 = b%i
	
	add $s3, $s1, $s2
	bne $s3, $zero,GCDifEnd	#if  !(a%i==0 && b%i==0) skip
	move $s4, $s0		#hcf = i
GCDifEnd:
	
	addi $s0, $s0, 1 #i++
	j GCDLoop
GCDforEnd:
	move $v0, $s4
    	
	lw $ra, 0($sp)
	lw $a0, 4($sp)
	lw $a1, 8($sp)
	lw $s4, 12($sp)
	lw $s0, 16($sp)
	lw $s1, 20($sp)
	lw $s2, 24($sp)
	lw $s3, 28($sp)
    	addi $sp, $sp, 32
    	jr $ra

getNumber:
	li $v0,4        # Print string system call
	la $a0,msg1         
	syscall

	la $a0, empty
	li $a1, 33      # Max Length: 32 bit
	li $v0, 8                # syscall for string read
	move $s0, $a0
	syscall
	
getType:
	li $v0,4        # Print string system call
	la $a0,msg3        
	syscall
	
	li $v0,5                # syscall for int read
	syscall
	move  $s1, $v0 # Store output type (1=decimal, 2=hex)
	
	la $a0, 0($s0)
	
findLength:
	lb $t1, ($a0) 
        beq $t1, $zero, convert    # exit this part if byte is null 
        addi $t0, $t0, 1 
        addi $a0, $a0, 1 # go to next byte      
        j findLength 

convert: 
	subi  $t0, $t0, 2 #length is always have extra 2 we dont know why
	li $s2, 0 #sum to 0
	move $s3, $t0  #counter for multiply is set to length of string - 1
	subi $s3, $s3, 1
	move $t9, $s3 
	li $t3, 0 #i = 0
	la $t1, empty #address of start of string
	
forLoop:
	beq $t3, $t0, printSum #if i = length go to print
	add $t6, $t1, $t3 #address of character which will be checked
	lb $a0, ($t6)     # load byte
	addi $t3, $t3, 1          # increase offset by 1
	subi $a0, $a0, 48         # subtract 48 to convert to int value
	beq $a0, 1, isOne
	beq $a0, 0, isZero
	
isZero:
	subi $t9, $t9, 1
	j forLoop

isOne:                  
	beq $t3,1, negativeNumber  # if i = 0 and string{i] = '1'
	li $t8, 1             
	sllv $t5, $t8, $t9    # shift left by 2^counter
	add $s2, $s2, $t5         #  add sum to previous sum 
	subi $t9, $t9, 1          # decrement counter
	j forLoop
	
negativeNumber:
	li $t8, 1              
	sllv $t5, $t8, $t9    # shift left by 2^counter
	li $a3, -1 
	mult $t5 $a3 #multiply result with -1
	mflo $t5
	add $s2, $s2, $t5         # add sum to previous sum 
	subi $t9, $t9, 1          # decrement counter
	j forLoop

printSum:
	bne $t0, 31, skip
	li $t4, 2
	mult $s2 $a3 #multiply result with 2 if its length is 32 bit
	mflo $s2
skip:
	la $a0, sumMsg
	li $v0, 4
	syscall
	beq $s1,2,printHex
	beq $s1,1,printDec

printDec:
	move $a0, $s2     # load sum
	li $v0, 1      # print int
	syscall
	j exit
printHex:
	move $a0, $s2 
	li $v0, 34      # print hex
	syscall
	j exit

exit:
	li $v0, 10       
	syscall

