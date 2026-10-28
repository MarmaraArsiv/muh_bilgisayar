from functions import *

INSTRUCTION_BITS = 18

operand_types = {
    'R': {'bits': 4, 'negative': False},
    'IMM': {'bits': 6, 'negative': True},
    'ADDR7': {'bits': 7, 'negative': False},
    'ADDR10': {'bits': 10, 'negative': False},
    'PCoffset6': {'bits': 6, 'negative': True},
    'PCoffset14': {'bits': 14, 'negative': True},
    'nzp': {'bits': 3, 'negative': False},
}

instruction_set = {
    'AND': {'opcode': '0000', 'operands': ['R', 'R', 'R']},
    'ADD': {'opcode': '0010', 'operands': ['R', 'R', 'R']},
    'OR': {'opcode': '0001', 'operands': ['R', 'R', 'R']},
    'XOR': {'opcode': '0011', 'operands': ['R', 'R', 'R']},
    'ANDI': {'opcode': '0100', 'operands': ['R', 'R', 'IMM']},
    'ORI': {'opcode': '0101', 'operands': ['R', 'R', 'IMM']},
    'ADDI': {'opcode': '0110', 'operands': ['R', 'R', 'IMM']},
    'XORI': {'opcode': '0111', 'operands': ['R', 'R', 'IMM']},
    'BEQ': {'opcode': '1000', 'operands': ['R', 'R', 'PCoffset6']},
    'BGT': {'opcode': '1001', 'operands': ['R', 'R', 'PCoffset6']},
    'BLT': {'opcode': '1010', 'operands': ['R', 'R', 'PCoffset6']},
    'BGE': {'opcode': '1011', 'operands': ['R', 'R', 'PCoffset6']},
    'BLE': {'opcode': '1100', 'operands': ['R', 'R', 'PCoffset6']},
    'ST': {'opcode': '1101', 'nzp': ['000', 1], 'operands': ['R', 'ADDR7']},
    'LD': {'opcode': '1110', 'operands': ['R', 'ADDR10']},
    'JUMP': {'opcode': '1111', 'operands': ['PCoffset14']},
}

instructions = read_file('input.txt')
output = ['v2.0 raw']

for instruction_line in instructions:
    instruction, operands = read_instruction(instruction_line)
    if instruction in instruction_set:
        has_error = False
        binary_string = instruction_set[instruction]['opcode']
        i = 0
        used_bits = len(binary_string)
        print(instruction, operands)
        for operand in operands:
            if 'nzp' in instruction_set[instruction] and i == instruction_set[instruction]['nzp'][1]:
                binary_string += instruction_set[instruction]['nzp'][0]
                used_bits += 3
            operand_type = instruction_set[instruction]['operands'][i]
            operand_bits = operand_types[operand_type]['bits']
            if operand_type == 'R':
                operand = operand[1:]  # Remove the 'R'
            try:
                operand = int(operand)
            except ValueError:
                print('Error: Operand "%s" is not valid (%s)' % (operand, instruction_line))
                has_error = True
                continue
            min_val, max_val = dec_range(operand_bits, operand_types[operand_type]['negative'])
            if operand < min_val or operand > max_val:
                print('Error: Operand %d out of range (%s)' % (operand, instruction_line))
                has_error = True
                continue
            binary_string += dec_to_bin(operand, operand_bits, operand_types[operand_type]['negative'])
            i += 1
            used_bits += operand_bits
        while used_bits < INSTRUCTION_BITS:
            binary_string += '0'
            used_bits += 1
        if not has_error:
            print(binary_string)
            output.append(bin_to_hex(binary_string, 5))

save_to_file('output.hex', output)
