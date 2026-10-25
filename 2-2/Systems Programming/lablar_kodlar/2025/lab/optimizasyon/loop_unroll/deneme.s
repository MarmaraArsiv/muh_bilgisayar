	.file	"unroll1.c"
	.text
	.globl	unroll
	.type	unroll, @function
unroll:
.LFB0:
	.cfi_startproc
	movl	$1, %eax
	movl	$0, %edx
.L2:
	imull	%edx, %eax
	addl	$1, %edx
	cmpl	$10, %edx
	jne	.L2
	rep ret
	.cfi_endproc
.LFE0:
	.size	unroll, .-unroll
	.ident	"GCC: (Ubuntu 7.5.0-3ubuntu1~18.04) 7.5.0"
	.section	.note.GNU-stack,"",@progbits
