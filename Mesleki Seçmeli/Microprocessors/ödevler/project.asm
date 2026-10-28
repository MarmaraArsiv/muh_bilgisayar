.data
vga_x: 0xa00
vga_y: 0xa01
kb_data: 0x900
kb_status: 0x901
.code
        ldi 3 320
        ldi 4 240
        ldi 0 vga_x
        ld 0 0
        ldi 1 320
        st 0 1
        ldi 0 vga_y
        ld 0 0
        ldi 1 240
        st 0 1
        ldi 6 4
loop    ldi 0 kb_status
        ld 0 0
        ld 0 0
        mov 0 0
        jz loop
        ldi 0 kb_data
        ld 0 0
        ld 0 0
        mov 0 0
        jz loop
        ldi 1 0x1d
        sub 1 1 0
        jz press_w
        ldi 1 0x1c
        sub 1 1 0
        jz press_a
        ldi 1 0x1b
        sub 1 1 0
        jz press_s
        ldi 1 0x23
        sub 1 1 0
        jz press_d
        jmp loop
press_w ldi 0 vga_y
        ld 0 0
        sub 4 4 6
        st 0 4
        jmp lock
press_a ldi 0 vga_x
        ld 0 0
        sub 3 3 6
        st 0 3
        jmp lock
press_s ldi 0 vga_y
        ld 0 0
        add 4 4 6
        st 0 4
        jmp lock
press_d ldi 0 vga_x
        ld 0 0
        add 3 3 6
        st 0 3
        jmp lock
lock    ldi 0 kb_status
        ld 0 0
        ld 0 0
        mov 0 0
        jz lock
        ldi 0 kb_data
        ld 0 0
        ld 0 0
        mov 0 0
        jz lock
        ldi 1 0xf0
        sub 1 1 0
        jz lock2
        jmp lock
lock2   ldi 0 kb_status
        ld 0 0
        ld 0 0
        mov 0 0
        jz lock2
        ldi 0 kb_data
        ld 0 0
        ld 0 0
        ldi 1 0x1c
        sub 1 1 0
        jz loop
        ldi 1 0x1d
        sub 1 1 0
        jz loop
        ldi 1 0x1b
        sub 1 1 0
        jz loop
        ldi 1 0x23
        sub 1 1 0
        jz loop
        jump lock2