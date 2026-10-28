"""
The code imports the numpy module, loads the in.dta and out.dta data files, and separates the data and labels for
each. It then defines the nonlinear_transform function, which applies a nonlinear transform to the given dataset.

Next, it defines the linear_regression function, which finds the weights for a linear regression model on the given
dataset. The run_linreg function applies linear regression to the given train and test sets, and returns the
predictions for each.

The find_ein_eout function calculates the error rate (the proportion of misclassified points) on the train and test
sets, given their predictions.

Finally, the code applies the nonlinear transform to the train and test sets, runs linear regression on the
transformed sets, and prints the error rates on the train and test sets.
"""

import numpy as np

"""
Load data
"""
in_dta = np.loadtxt("in.dta")
out_dta = np.loadtxt("out.dta")
in_data, in_label = in_dta[:, :2], in_dta[:, 2]
out_data, out_label = out_dta[:, :2], out_dta[:, 2]

"""
The nonlinear_transform function takes in a dataset and applies a series of nonlinear transformations to it. It
squares the values of the first and second columns, computes the product of these two columns, and then calculates
the absolute differences and sums of these two columns. Finally, it concatenates these new columns to the original
dataset and returns the result.
"""


def nonlinear_transform(dataset):
    # Compute square of each element in the x1 column
    x1_square = (dataset[:, 1] ** 2).reshape(len(dataset), 1)
    # Compute square of each element in the x2 column
    x2_square = (dataset[:, 2] ** 2).reshape(len(dataset), 1)
    # Element-wise multiplication of x1 and x2
    x1_x2 = (dataset[:, 1] * dataset[:, 2]).reshape(len(dataset), 1)
    # Element-wise absolute difference of x1 and x2
    x1_x2_abs_diff = (np.absolute(dataset[:, 1] - dataset[:, 2])).reshape(len(dataset), 1)
    # Element-wise absolute sum of x1 and x2
    x1_x2_abs_sum = (np.absolute(dataset[:, 1] + dataset[:, 2])).reshape(len(dataset), 1)
    # Concatenate computed columns to the dataset
    dataset = np.concatenate((dataset, x1_square, x2_square, x1_x2, x1_x2_abs_diff, x1_x2_abs_sum), axis=1)
    return dataset


"""
This function performs linear regression on the given dataset using the given sign list. It first computes the 
pseudo-inverse of the dataset using the numpy.linalg.pinv() function, and then calculates the weight vector by 
multiplying the pseudoinverse with the sign list. It returns the calculated weight vector.
"""


def linear_regression(dataset, sign_list):
    x_pseudo = np.linalg.pinv(dataset)
    weight = np.matmul(x_pseudo, sign_list)
    return weight


def run_lin_regression(train_set, train_label, test_set):
    # Compute the weight vector using the linear regression formula
    weight = linear_regression(train_set, train_label)

    # Use the weight vector to compute the predictions for the training and test sets
    predict_train = np.sign(np.matmul(weight, train_set.T))
    predict_test = np.sign(np.matmul(weight, test_set.T))

    # Return the predictions for the training and test sets
    return predict_train, predict_test


def find_ein_eout(p_train, p_test):
    # Count the number of miss classifications
    num_miss_train = np.count_nonzero(p_train != in_label)
    num_miss_predict = np.count_nonzero(p_test != out_label)
    # Calculate the error rates
    ein = num_miss_train / len(p_train)
    eout = num_miss_predict / len(p_test)
    return ein, eout


nonlin_train = nonlinear_transform(np.insert(in_data, 0, 1, axis=1))
nonlin_test = nonlinear_transform(np.insert(out_data, 0, 1, axis=1))

p_train, p_test = run_lin_regression(nonlin_train, in_label, nonlin_test)

# print(find_ein_eout(p_train, p_test))

"""
The code is a function that performs linear regression on the given dataset with a weight decay parameter. The 
function first applies the linear regression weight function with the weight decay to the dataset. It then finds a 
new vector based on the linear regression formula. Finally, it calculates the final weight vector with the weight 
decay and returns it. The function then uses this weight vector to make predictions on the train and test sets, 
and returns these predictions. Finally, the code prints the error rates on the train and test sets using the 
find_ein_eout() function. 
"""


def linear_regression_with_weight_decay(dataset, sign_list, k):
    # Compute the inverse of the dot product of the dataset with its transpose
    # and add 10^k times the identity matrix to it
    inverse = np.linalg.inv(np.dot(dataset, dataset.T) + 10 ** k * np.identity(len(dataset)))
    # Multiply the inverse by the dataset
    tmp = np.matmul(inverse, dataset)
    # Multiply the transpose of the dataset by the sign list
    weight = np.matmul(tmp.T, sign_list)
    return weight


def run_lin_regression_weight_decay(train_set, train_label, test_set, k):
    # Compute the weights with weight decay
    weight = linear_regression_with_weight_decay(train_set, train_label, k)
    # Compute the predictions for the training set
    predict_train = np.sign(np.matmul(weight, train_set.T))
    # Compute the predictions for the test set
    predict_test = np.sign(np.matmul(weight, test_set.T))
    return predict_train, predict_test


pw_train, pw_test = run_lin_regression_weight_decay(nonlin_train, in_label, nonlin_test, -3)
# print(find_ein_eout(pw_train, pw_test))


pw_train, pw_test = run_lin_regression_weight_decay(nonlin_train, in_label,nonlin_test, 3)
#print(find_ein_eout(pw_train,pw_test))


k_values = [-2, -1, 0, 1, 2]
min_eout = 100
min_k = k_values[0]
for k in k_values:
    pw_train, pw_test = run_lin_regression_weight_decay(nonlin_train, in_label, nonlin_test, k)
    ein, eout = find_ein_eout(pw_train, pw_test)
    if eout <= min_eout:
        min_eout = eout
        min_k = k

print(min_eout, min_k)