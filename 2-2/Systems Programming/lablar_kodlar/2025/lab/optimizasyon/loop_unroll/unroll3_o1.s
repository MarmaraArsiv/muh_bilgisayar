	.file	"unroll3.c"
	.text
	.globl	unroll
	.type	unroll, @function
unroll:
.LFB0:
	.cfi_startproc
	leaq	4(%rdi), %rdx
	addq	$40, %rdi
	movl	$1, %eax
.L2:
	imull	(%rdx), %eax
	addq	$4, %rdx
	cmpq	%rdi, %rdx
	jne	.L2
	rep ret
	.cfi_endproc
.LFE0:
	.size	unroll, .-unroll
	.ident	"GCC: (Ubuntu 7.5.0-3ubuntu1~18.04) 7.5.0"
	.section	.note.GNU-stack,"",@progbits
