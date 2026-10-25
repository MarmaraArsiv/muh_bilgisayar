	.file	"unroll2.c"
	.text
	.globl	unroll
	.type	unroll, @function
unroll:
.LFB0:
	.cfi_startproc
	endbr64
	movl	$1, %edx
	movl	$1, %eax
.L2:
	imull	%eax, %edx
	addl	$1, %eax
	cmpl	$10, %eax
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
