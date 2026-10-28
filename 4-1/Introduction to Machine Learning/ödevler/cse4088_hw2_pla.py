import random
import numpy as np


def random_point():
    # assume X = [-1, 1] x [-1, 1] with uniform probability of picking each x∈X
    x, y = random.uniform(-1, 1), random.uniform(-1, 1)
    return x, y


def random_line():
    # generate two random points to construct a line
    p0, p1 = random_point(), random_point()
    target_a = (p1[1] - p0[1]) / (p1[0] - p0[0])
    target_b = p0[1] - target_a * p0[0]
    return target_a, target_b


def target_func(target_a, target_b, p):
    return -1 if target_a * p[0] + target_b > p[1] else 1


class Dataset:
    def __init__(self, num_points):
        self.target_a, self.target_b = random_line()
        self.xs, self.ys = [], []

        # generate multiple random points according to given number
        for i in range(num_points):
            xn = random_point()
            self.xs.append(xn)
            self.ys.append(target_func(self.target_a, self.target_b, xn))


class PLA:
    def __init__(self, dataset: Dataset):
        self.w = np.array([0, 0, 0])  # weight vector w being all zeros
        self.dataset = dataset  # set the current dataset

    def candidate_func(self, p):
        return int(np.sign(self.w[0] * 1 + self.w[1] * p[0] + self.w[2] * p[1]))

    def fit(self):
        self.w = np.array([0, 0, 0])  # weight vector w being all zeros
        num_iters = 0

        while True:
            misclassified_points = []  # all points are initially misclassified
            for (x, y) in zip(self.dataset.xs, self.dataset.ys):
                if self.candidate_func(x) != y:
                    # add current point to misclassified points
                    misclassified_points.append((np.array([1, x[0], x[1]]), y))
            if len(misclassified_points) > 0:
                num_iters += 1
                x, y = random.choice(misclassified_points)
                self.w = self.w + y * x
            else:
                return num_iters


def experiment(num_steps: int, num_points: int):
    num_iters = []  # store number of iterations for each run
    num_correct = 0  # number of correct classified points
    num_wrong = 0  # number of wrong classified points
    for i in range(num_steps):
        ds = Dataset(num_points=num_points)  # generate dataset with 10 points
        pla = PLA(dataset=ds)  # construct perceptron learning algorithm
        num_iters.append(pla.fit())  # add the current number of iterations to the list
        # iterate over each point to check they are correctly classified or not
        for j in range(num_steps):
            p = random_point()
            # if two results are equal the point is correctly classified
            if target_func(ds.target_a, ds.target_b, p) == pla.candidate_func(p):
                num_correct += 1
            else:
                num_wrong += 1

    return num_iters, num_correct, num_wrong


total_num_iters, total_num_correct, total_num_wrong = experiment(num_steps=1000, num_points=100)

print("Mean number of iterations for each run: ", np.mean(total_num_iters))
rate_wrong = float(total_num_wrong) / (total_num_wrong + total_num_correct)
print("P[f(x)≠g(x)]: ", rate_wrong)
print("P[f(x)=g(x)]: ", 1.0 - rate_wrong)
