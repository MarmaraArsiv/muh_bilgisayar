import numpy as np

# Generate a random line in the 2-dimensional space
# by selecting two points at random from the X space
x1, y1 = np.random.uniform(-1, 1, 2)
x2, y2 = np.random.uniform(-1, 1, 2)

# Compute the slope and intercept of the line
slope = (y2 - y1) / (x2 - x1)
intercept = y1 - slope * x1


# Define the boundary line between the y = +1 and y = -1 regions
def boundary(x):
    return slope * x + intercept


# Generate a dataset of N = 100 points, drawn uniformly at random from the X space
N = 100
X = np.random.uniform(-1, 1, (N, 2))

# Evaluate the output yn for each point xn
yn = np.where(X[:, 1] > boundary(X[:, 0]), 1, -1)

# Initialize the weight vector to zeros
w = np.zeros(3)

# Set the learning rate to 0.01
eta = 0.01

# Use the SGD algorithm to train a logistic regression model
# on the training data
w_prev = w
epoch = 0
while True:
    # Randomly shuffle the training data
    epoch += 1
    perm = np.random.permutation(N)
    X, yn = zip(*[(X[i], yn[i]) for i in perm])

    # Iterate over the training data
    for xn, y in zip(X, yn):
        # Add a bias term to the input xn
        xn = np.insert(xn, 0, 1)

        # Compute the predicted probability of y = +1
        p = 1 / (1 + np.exp(-w.dot(xn)))

        # Update the weight vector using the gradient of the cross entropy loss
        gradient = -y * xn * (1 - p)
        w -= eta * gradient

    # Check if the difference between the current and previous weights is less than 0.01
    if np.linalg.norm(w - w_prev) < 0.01:
        break
    w_prev = w

# Generate a separate set of points from the X space
# to evaluate the error of the trained model
X_test = np.random.uniform(-1, 1, (N, 2))

# Compute the cross entropy error on the test data
error = 0
for xn in X_test:
    # Add a bias term to the input xn
    xn = np.insert(xn, 0, 1)

    # Compute the predicted probability of y = +1
    p = 1 / (1 + np.exp(-w.dot(xn)))

    # Compute the cross entropy loss
    error += -np.log(p)

# Compute the average error
error /= N

# Print the estimated Eout error
print(f"Eout = {error:.3f}")
print(f"Epoch = {epoch:.3f}")
