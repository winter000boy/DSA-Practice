import pandas as pd

def selectFirstRows(employees: pd.DataFrame) -> pd.DataFrame:
    return employees.head(3)




# Example
employees = pd.DataFrame({
    'name': ['John Doe', 'Jane Smith', 'Ralph Johnson'],
    'age': [34, 56, 23],
    'department': ['Accounting', 'Marketing', 'Engineering']
})


