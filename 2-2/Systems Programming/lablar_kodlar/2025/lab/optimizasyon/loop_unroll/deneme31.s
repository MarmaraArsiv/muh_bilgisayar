	.file	"unroll3.c"
	.text
	.globl	unroll
	.type	unroll, @function
unroll:
.LFB0:
	.cfi_startproc
	endbr64
	leaq	4(%rdi), %rax
	addq	$40, %rdi
	movl	$1, %edx
.L2:
	imull	(%rax), %edx
	addq	$4, %rax
	cmpq	%rdi, %rax
	jne	.L2
	movl	%edx, %eax
	ret
	.cfi_endproc
.LFE0:
	.size	unroll, .-unroll
	.ident	"GCC: (Ubuntu 13.3.0-6ubuntu2~24.04.1) 13.3.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 8
	.long	1f - 0f
	.long	4f - 1f
	.long	5
0:
	.string	"GNU"
1:
	.align 8
	.long	0xc0000002
	.long	3f - 2f
2:
	.long	0x3
3:
	.align 8
4:
