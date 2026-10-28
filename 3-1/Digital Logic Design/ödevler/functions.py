# Read txt file line by line
def read_file(file_name):
    file = open(file_name, "r")
    lines = file.readlines()
    file.close()
    return lines


# Convert binary string to hexadecimal with fixed length
def bin_to_hex(binary, length):
    return '{:0{}x}'.format(int(binary, 2), length)


# Calculate binary range
def dec_range(bits, twos_complement=False):
    if not twos_complement:
        return 0, pow(2, bits) - 1
    else:
        return -pow(2, bits - 1), pow(2, bits - 1) - 1


# Convert decimal number to binary n bits
def dec_to_bin(number, bits, twos_complement=False):
    if twos_complement:
        s = bin(number & int("1" * bits, 2))[2:]
        return ("{0:0>%s}" % bits).format(s)
    else:
        return bin(number)[2:].zfill(bits)


# Save list to file with new line
def save_to_file(file_name, data):
    file = open(file_name, "w")
    for line in data:
        # If it is the last line, don't add new line
        if line != data[-1]:
            file.write(line + '\n')
        else:
            file.write(line)
    file.close()


# Read instruction line
def read_instruction(instruction_line):
    split_line = instruction_line.split()
    instruction = split_line[0]
    # Get parameters
    parameters = split_line[1:]
    # Convert list to string
    parameters = ' '.join(parameters)
    parameters = parameters.split(',')
    return instruction, parameters
