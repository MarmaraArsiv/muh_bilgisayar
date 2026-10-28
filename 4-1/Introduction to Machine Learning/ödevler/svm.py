import numpy as np
from sklearn.model_selection import cross_val_score
from sklearn.svm import SVC

# Load training and test data
data_train = np.loadtxt("features.train")
data_test = np.loadtxt("features.test")


def get_n_vs_all_dataset(n, data):
    is_n = data[:, 0] == n
    X = data.copy()
    X[:, 0] = 1.0
    y = 2 * is_n - 1
    return X, y

# Initialize dictionaries to store modified datasets and number of support vectors for each digit
vs_all_datasets = {}
vs_all_support_vectors = {}

# Create modified datasets for each digit
for n in range(10):
    vs_all_datasets[n] = get_n_vs_all_dataset(n, data_train)

X, y = vs_all_datasets[1]

# Create an SVM classifier with a polynomial kernel of degree 2 and a regularization parameter of 0.01
clf = SVC(C=0.01, kernel="poly", degree=2, gamma=1.0)

print("Question 2-3")

for n in range(10):
    X, y = vs_all_datasets[n]
    clf.fit(X, y)
    vs_all_support_vectors[n] = len(clf.support_)
    y_predict = clf.predict(X)
    E_in = np.mean(y != y_predict)
    print(n, E_in)

print("Question 4")

print(
    f"Difference between number of support vectors for 0 and 1: {vs_all_support_vectors[0] - vs_all_support_vectors[1]}")

print("Question 5")

for C in (0.001, 0.01, 0.1, 1.0):
    clf = SVC(C=C, kernel="poly", degree=2, gamma=1.0)
    X, y = vs_all_datasets[1]
    clf.fit(X, y)
    y_predict = clf.predict(X)
    E_in = np.mean(y != y_predict)
    print(f"C:{C}, E_in:{E_in}")

print("Question 6")
for C in (0.001, 0.01, 0.1, 1.0):
    X, y = vs_all_datasets[1]
    clf = SVC(C=C, kernel="poly", degree=2, gamma=1.0)
    clf.fit(X, y)
    y_predict = clf.predict(X)
    E_in = np.mean(y != y_predict)
    print(f"C:{C}, Q:2, E_in: {E_in}, # of SVs:{len(clf.support_)}")
    clf = SVC(C=C, kernel="poly", degree=5, gamma=1.0)
    clf.fit(X, y)
    y_predict = clf.predict(X)
    E_in = np.mean(y != y_predict)
    print(f"C:{C}, Q:5, E_in: {E_in}, # of SVs:{len(clf.support_)}")

print("Question 7")

for C in (0.001, 0.01, 0.1, 1.0):
    X, y = vs_all_datasets[1]
    clf = SVC(C=C, kernel="poly", degree=2, gamma=1.0)
    cross_validation = cross_val_score(clf, X, y, cv=10)
    clf.fit(X, y)
    y_predict = clf.predict(X)
    E_in = np.mean(y != y_predict)
    print(f"C:{C}, Q:2, E_in: {E_in}, CV:{np.mean(cross_validation)}")

print("Question 9-10")

for C in (0.01, 1, 100, 10000, 1000000):
    clf = SVC(C=C, kernel="rbf", gamma=1.0)
    X, y = vs_all_datasets[1]
    clf.fit(X, y)
    E_in = np.mean(clf.predict(X) != y_predict)
    X_test, y_test = get_n_vs_all_dataset(1, data_test)
    E_out = np.mean(clf.predict(X_test) != y_test)
    print(f"C:{C}, E_in:{E_in}, E_out:{E_out}")
