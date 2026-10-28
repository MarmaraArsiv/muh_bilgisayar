.data
switch: 0xa12
display: 0xa10
btn1: 0xa08
btn2: 0xa06
input1: 0xa04
input2: 0xa02
time_addr: 0xa00
time: 0
sum: 0
data1: 0
data2: 0
.code
        ldi 7 0xe00
        ldi 0 0x7f0
        ldi 1 isr0
        st 0 1
        sti
loop    ldi 0 switch
		ld 0 0
		ld 0 0
		mov 0 0
		jz sw_down
				
		ldi 0 time
		ld 0 0
		ldi 1 display
		ld 1 1
		st 1 0

loop2	ldi 0 input1
        ld 0 0
        ld 1 0
		ldi 0 data1
		st 0 1
		
		ldi 0 input2
        ld 0 0
        ld 1 0
		ldi 0 data2
		st 0 1
		
		ldi 0 btn1
        ld 0 0
        ld 0 0
        dec 0
        jz pb1
		
		ldi 0 btn2
        ld 0 0
        ld 0 0
        dec 0
        jz pb2
		
        jmp loop	

sw_down	ldi 0 sum
		ld 0 0
		ldi 1 display
		ld 1 1
		st 1 0
		jmp loop2

pb1     ldi 0 data1
        ld 0 0
		ldi 1 sum
		ld 1 1
		add 1 1 1
		add 1 1 0
		ldi 0 sum
		st 0 1
        jmp loop

pb2		ldi 0 0
		ldi 1 data2
		ld 1 1
		mov 1 1
		jz save
		ldi 2 sum
		ld 2 2
mult	add 0 0 2
		dec 1
		jz save
		jmp mult
save	ldi 2 sum
		st 2 0
		jmp loop

isr0    ldi 0 time_addr
        ld 0 0
        ld 3 0
		ldi 0 time
		st 0 3
        sti
        iret