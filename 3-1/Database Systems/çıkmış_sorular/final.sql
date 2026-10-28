/*
4) (12 pts) Retrieve full name (first name and last name concatenated with a blank) of the owner, 
full name (first name and last name concatenated with a blank) of the renter, flat ID, 
and area of the flat for the flats who have amounts to be paid (use field AmountToBePoid in table SiteIncome) 
more than 49 Turkish Liras and 99 kurus (cent) with the index difference less than 10 in the period 2016-12. 
Rename the column name for owner and renter as "Owner's Name" and "Renter's Name", respectively. 
Have your list ordered by flat ID. Keep in mind that some flats may not have renters. 
*/

SELECT o.FirstName + ' ' + o.LastName ownerName, r.FirstName + ' ' + r.LastName renterName,
f.FlatID, f.AreaM2
FROM Person o inner join Flat f on o.PersonID = f.OwnerID
left outer join Person r on f.RenterID = r.PersonID,
(SELECT si.FlatID FROM SiteIncome si WHERE si.LastIndex - si.FirstIndex < 10 and si.PeriodID='2016-12'
and si.AmountToBePaid > 49.99) si WHERE
si.flatID = f.flatID ORDER BY f.flatID

SELECT o.FirstName + ' ' + o.LastName ownerName, r.FirstName + ' ' + r.LastName renterName,
f.FlatID, f.AreaM2
FROM Person o inner join Flat f on o.PersonID = f.OwnerID
left outer join Person r on f.RenterID = r.PersonID inner join SiteIncome si on si.flatID=f.FlatID
WHERE (si.LastIndex - si.FirstIndex) < 10 and si.PeriodID='2016-12'
and si.AmountToBePaid > 49.99 ORDER BY f.flatID


/*
5) (16 pts) Retrive flat ID, the last name of the owner, 
the percentage of the amount paid to the amount to be paid, 
and the cluster (Platinum, Gold, Bronze) for the flats which have at least 5 income records 
(consider the fields AmountroBePaid and AmountPaid in table SiteIncome). 
If the percentage is greater than 75, the cluster value will be Platinum; 
if it is between 75 and 50, the cluster will be Gold; and if it is less than 50, 
then the cluster will be Bronze. If any, discard redundant data. 
*/

SELECT f.flatID, o.LastName, si.Cluster
FROM Person o inner join Flat f on f.ownerID=p.PersonID,
(SELECT si.flatID, (100.0*AmountPaid/AmountToBePaid) PaidPercent,
IIF(PaidPercent>75, 'Platinum', IIF(PaidPercent > 50, 'Gold', 'Bronze')) Cluster
FROM SiteIncome si GROUP BY si.flatID HAVING count(*) >= 5) si WHERE si.flatID=f.flatID


/*
6) (20 pts) Implement a stored procedure such that: 
It takes one input parameter: a period (Ie. month). 
•Given a period, calculate the heating payment for the flats,
i.e. update the records in table Sitelncome for the given period with the following assumptions:
o There are totally 44 flats starting from 1 to 44. You may use table Plot. 
o Calculations will be done only for heating expense/income type for the given period. 
Income/Expense Type will be "Heating". 
o Fixed Cost (FC): For the given period, 30% of the amount (AmountroBePaid) of the expense 
will be distributed to the flats according to their areas (AreoM2). 
o Variable Cost (VC): For the given period, 70% of the amount (AmountToBePoid) of the expense 
will be distributed to the flats according to their Index differences (LostIndex-FirstIndex). 
o Total Cost, which is the amount to be paid for the flat, is equal to the sum of FC and VC. 
o You have the related records in table Income before your calculations. For the given period, the fields 
Income Type, PeriodiD, FlotID, Firstlndex, and Lostlndex are not null (there are values stored in those 
fields), and you are going to calculate the field AmountToBePoid. 
*/

CREATE PROCEDURE sp_UpdateSiteIncome
@period char(7)
AS
BEGIN
declare @totalPayment decimal(10,2) = (SELECT AmountToBePaid FROM SiteExpense WHERE PeriodID=@period and ExpenseType='Heating')
declare @totalArea decimal(7,2) = (SELECT sum(AreaM2) FROM Flat)
declare @totalIndex decimal(10,2) = (SELECT sum(LastIndex-FirstIndex) FROM SiteIncome)
UPDATE SiteIncome si SET AmountToBePaid = (SELECT
(0.3*@totalPayment)*(f.AreaM2/@totalArea) +
(0.7*@totalPayment)*((si.LastIndex-si.FirstIndex)/@totalIndex) totalCost,
FROM Flat f inner join si.FlatID=f.flatID
) WHERE si.IncomeType='Heating'
END

/*
7) (16 pts) Implement a trigger such that:
- It will be fired whenever there is an update on table SiteIncome.
- When the update operation takes place, you will update the fields TotalAmountToBePaid and TotalAmountPaid
in table Flat for those flats only.
*/

CREATE TRIGGER on SiteIncome
AFTER INSERT, DELETE, UPDATE
AS
BEGIN
IF EXISTS (SELECT * FROM inserted)
BEGIN
-- INSERT OR UPDATE
declare @flatID int = (SELECT FlatID FROM inserted)
END
ELSE
BEGIN
-- DELETE
declare @flatID int = (SELECT FlatID FROM deleted)
END
UPDATE Flat f SET TotalAmountToBePaid=(SELECT sum(AmountToBePaid) FROM SiteIncome si WHERE si.flatID=@flatID),
TotalAmountPaid=(SELECT sum(AmountPaid) FROM SiteIncome si WHERE si.flatID=@flatID)
END
