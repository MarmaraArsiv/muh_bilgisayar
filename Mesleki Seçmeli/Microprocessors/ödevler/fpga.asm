.data
seven_segment: 0xb00
btn1: 0xa08
input1: 0xa04
kb_data: 0x900
kb_status: 0x901
.code
loop    ldi 0 kb_status
        ld 0 0
        ld 0 0
        mov 0 0
        jz status_end
        ldi 0 kb_data
        ld 0 0
        ld 0 0
        mov 0 0
        jz loop
        ldi 1 seven_segment
        ld 1 1
        st 1 0
status_end
        jmp loop