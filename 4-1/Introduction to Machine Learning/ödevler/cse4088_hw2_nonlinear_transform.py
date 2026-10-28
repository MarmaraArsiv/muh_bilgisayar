import numpy as np


class LinearRegression:
    def __init__(self, dataset):
        self.E_in = 0
        self.dataset = dataset
        pass

    def fit(self):
        # Implement linear regression here
        pass


def generate_circular_dataset(sample_size: int, min_val: int, max_val: int):
    # Generate uniformly distributed dataset here
    return []


N = 1000  # sample size
E_ins = []  # store E_in values

# run the experiment 1000 times
for i in range(1000):
    # Generate uniformly distributed linearly separable dataset for the current run
    dataset = generate_circular_dataset(N, -1, 1)
    # run linear regression on the current dataset
    linear_classifier = LinearRegression(dataset=dataset)
    linear_classifier.fit()
    # save the current E_in value to calculate average later
    E_ins.append(linear_classifier.E_in)

# calculate the average of all results
average_E_in = np.mean(E_ins)

print(average_E_in)

