	.file	"unroll3.c"
	.text
	.p2align 4,,15
	.globl	unroll
	.type	unroll, @function
unroll:
.LFB0:
	.cfi_startproc
	leaq	4(%rdi), %rdx
	movl	$1, %eax
	addq	$40, %rdi
	.p2align 4,,10
	.p2align 3
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
