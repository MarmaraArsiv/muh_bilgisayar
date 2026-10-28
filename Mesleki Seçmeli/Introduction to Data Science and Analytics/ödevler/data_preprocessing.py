import numpy as np
import pandas as pd

# Read the dataset from CSV
df = pd.read_csv('../dataset.csv', delimiter=';', decimal=',', dtype={
    'PRIORITY': 'category',
    'COMPONENT': 'category',
    'LABEL': 'category',
    'URGENCY': 'category',
    'IMPACT': 'category',
    'ISSUE_CATEGORY': 'category',
    'ASSIGNEE': 'category',
    'POZISYON': 'category',
    'IS_BILGISI': 'category',
    'Kademe': 'category',
    'Complition Time': 'float32',
    'Total_Assignee': 'int8',
    'Total_Worklog_Assginee': 'int8',
    'Total_Log_Hours_Assignee': 'float32',
    'COMMENTOR_COUNT': 'int8',
    'COMMENT_COUNT': 'int8',
    'WAITING_HOURS_AT_CUSTOMER': 'float32',
    'ISSUE_TYPE': 'category',
})

# Check the initial state of the dataset
print("Initial dataset:")
print(df.head())

numerical_features = [
    'Complition Time',
    'Total_Assignee',
    'Total_Worklog_Assginee',
    'Total_Log_Hours_Assignee',
    'COMMENTOR_COUNT',
    'COMMENT_COUNT',
    'WAITING_HOURS_AT_CUSTOMER'
]

feature_types = {
    'Complition Time': 'float',
    'Total_Assignee': 'int',
    'Total_Worklog_Assginee': 'int',
    'Total_Log_Hours_Assignee': 'float',
    'COMMENTOR_COUNT': 'int',
    'COMMENT_COUNT': 'int',
    'WAITING_HOURS_AT_CUSTOMER': 'float'
}

for numerical_feature in numerical_features:
    # Calculate mean by using values greater than zero
    mean = df.loc[df[numerical_feature] > 0, numerical_feature].mean()
    std = df[numerical_feature].std()
    # Set a threshold for outliers (e.g., 3 standard deviations)
    threshold = 3 * std

    if feature_types[numerical_feature] == "int":
        mean = int(np.ceil(mean))

    # Convert negative values to NaN
    df[numerical_feature] = np.where(df[numerical_feature] < 0, mean, df[numerical_feature])

    # Replace outliers with the mean
    df[numerical_feature] = np.where(
        np.abs(df[numerical_feature] - mean) > threshold,
        mean,
        df[numerical_feature]
    )

print(df.dtypes)

# Back-fill
df = df.fillna(method='bfill')
# Forward-fill
df = df.fillna(method='ffill')
# Alternatively, you can fill missing values with mean, median, or mode using df.fillna() method

# Remove duplicates
df = df.drop_duplicates()

# Filter outliers
# You can use statistical methods like z-score or interquartile range (IQR) to detect and remove outliers

# Transform data if needed
# Apply transformations like scaling, normalization, or log transformation using mathematical operations on columns

# Validate the cleaned dataset
print("Cleaned dataset:")
print(df.head())

df.to_csv('../dataset_clean_2.csv', index=False, sep=',', encoding='utf-8')
