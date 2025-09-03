# Write your MySQL query statement below
select W1.id from Weather W1
Left join Weather W2
On Datediff(W1.recordDate, W2.recordDate) = 1
where W2.temperature is not NULL and W1.temperature > W2.temperature
