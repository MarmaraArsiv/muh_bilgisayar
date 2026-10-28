import math


def coordinate_descent(learning_rate, num_iterations):
    # Initialize the parameters
    u = 1
    v = 1

    # Perform coordinate descent for the specified number of iterations
    for i in range(num_iterations):
        # Update the value of u in order to minimize the error
        error = (u * math.exp(v) - 2 * v * math.exp(-u)) ** 2
        du = 2 * (u * math.exp(v) - 2 * v * math.exp(-u)) * (math.exp(v) + 2 * v * math.exp(-u))
        u = u - learning_rate * du

        # Update the value of v in order to minimize the error
        error = (u * math.exp(v) - 2 * v * math.exp(-u)) ** 2
        dv = 2 * (u * math.exp(v) - 2 * v * math.exp(-u)) * (u * math.exp(v) - 2 * math.exp(-u))
        v = v - learning_rate * dv

    return (u * math.exp(v) - 2 * v * math.exp(-u)) ** 2


print(coordinate_descent(0.1, 15))

