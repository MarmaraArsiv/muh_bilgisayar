.data
seven_segment: 0xa10
btn1: 0xa08
input1: 0xa04
.code
loop    ldi 0 seven_segment
        ld 0 0
        ldi 1 0xa08
        ld 1 1
        dec 1
        jz loop
        ldi 1 input1
        ld 1 1
        ld 1 1
        st 0 1
        jmp loop