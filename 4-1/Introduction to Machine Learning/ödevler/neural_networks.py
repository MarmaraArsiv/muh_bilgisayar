# Define the number of input, output and hidden units
num_input = 10
num_output = 1
num_hidden = 36

# Initialize the maximum number of weights to 0
max_num_weights = 0

hidden_units = num_hidden

# loop through each possible number of units in layer 1
for l1_units in range(2, 34):
    # calculate number of units in layer 2
    l2_units = hidden_units - l1_units

    # initialize number of weights to 0
    num_weights = 0

    # add weights between input and layer 1 units
    num_weights += (l1_units-1)*10

    # add weights between layer 1 and layer 2 units
    num_weights += (l2_units-1)*l1_units

    # add weights between layer 2 and output unit
    num_weights += 1 * l2_units

    # print network configuration and total number of weights
    max_num_weights = max(max_num_weights, num_weights)

# Print the maximum number of weights
print(max_num_weights)