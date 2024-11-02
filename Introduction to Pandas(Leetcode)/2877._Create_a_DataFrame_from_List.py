# def function_name(parameter : datatype) -> returntype:
#     pass

import pandas as pd

def createDataframe(student_data: List[List[int]]) -> pd.DataFrame:
    return pd.DataFrame(student_data, columns = ['student_id', 'age'])

#     rowName = ['student_id', 'age']
#     columnNames = student_data
#     pd.dataFrame(nameOfList, rowName, columnNames)