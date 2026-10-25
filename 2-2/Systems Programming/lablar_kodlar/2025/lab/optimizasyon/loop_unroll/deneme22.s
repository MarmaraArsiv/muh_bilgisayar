	.file	"unroll2.c"
	.text
	.p2align 4
	.globl	unroll
	.type	unroll, @function
unroll:
.LFB0:
	.cfi_startproc
	endbr64
	movl	$1, %eax
	movl	$1, %edx
.L2:
	imull	%edx, %eax
	leal	1(%rdx), %ecx
	addl	$2, %edx
	imull	%ecx, %eax
	imull	%edx, %eax
	leal	2(%rcx), %edx
	cmpl	$8, %ecx
	jne	.L2
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
