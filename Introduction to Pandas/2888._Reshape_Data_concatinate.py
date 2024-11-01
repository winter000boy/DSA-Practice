import pandas as pd

def concatenateTables(df1: pd.DataFrame, df2: pd.DataFrame) -> pd.DataFrame:
    return pd.concat([df1,df2], axis=0) 
# default axis = 0 & axis =1 for horszintal join
