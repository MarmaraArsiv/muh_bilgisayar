	.file	"unroll3.c"
	.text
	.p2align 4,,15
	.globl	unroll
	.type	unroll, @function
unroll:
.LFB0:
	.cfi_startproc
	movl	4(%rdi), %eax
	imull	8(%rdi), %eax
	imull	12(%rdi), %eax
	imull	16(%rdi), %eax
	imull	20(%rdi), %eax
	imull	24(%rdi), %eax
	imull	28(%rdi), %eax
	imull	32(%rdi), %eax
	imull	36(%rdi), %eax
	ret
	.cfi_endproc
.LFE0:
	.size	unroll, .-unroll
	.ident	"GCC: (Ubuntu 7.5.0-3ubuntu1~18.04) 7.5.0"
	.section	.note.GNU-stack,"",@progbits
