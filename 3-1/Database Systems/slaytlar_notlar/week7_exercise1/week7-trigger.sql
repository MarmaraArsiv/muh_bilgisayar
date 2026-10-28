
----1 - creating a new database

--create database COMPANY1


----2 - creating the tables

--CREATE TABLE EMPLOYEE
--(
--	SSN smallint NOT NULL PRIMARY KEY,
--	FNAME nvarchar(25) NOT NULL,
--	LNAME nvarchar(15) NOT NULL,
--	BDATE datetime2 NULL,
--	ADDRESS nvarchar(50) NULL,
--	GENDER char(1) NOT NULL,
--	SALARY smallint NULL,
--	SUPERSSN smallint,
--	DNO tinyint 
--);

--CREATE TABLE DEPARTMENT
--(
--	DNUMBER tinyint NOT NULL IDENTITY(1,1) PRIMARY KEY,
--	DNAME nvarchar(50) NOT NULL,
--	MGRSSN smallint NOT NULL,
--	MGRSTARTDATE datetime2 NULL
--);


----3 - creating a log table for employee

--CREATE TABLE EMPLOYEELOG
--(
--	SSN smallint NOT NULL,
--	ACTIONPERFORMED char(1),
--	ACTIONOCCURREDAT datetime2
--);


----4 - defining foreign keys

--ALTER table EMPLOYEE add FOREIGN KEY (SUPERSSN) REFERENCES EMPLOYEE (SSN); 
--ALTER table EMPLOYEE add FOREIGN KEY (DNO) REFERENCES DEPARTMENT(DNUMBER); 
--ALTER table DEPARTMENT add FOREIGN KEY (MGRSSN) REFERENCES EMPLOYEE (SSN);


----5 - insert some data into EMPLOYEE table

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (101, 'Ali', 'Kucuk', CONVERT(datetime2, '11.10.1980'), 'Istanbul', 'M', 2500, NULL, NULL ) 

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (102, 'Mehmet', 'Buyuk', CONVERT(datetime2, '11.10.1985'), 'Istanbul', 'M', 1500, 102, NULL ) 

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (103, 'Ayse', 'Temiz', CONVERT(datetime2, '11.10.1982'), 'Istanbul', 'F', 2000, 103, NULL ) 


----6 - inserting some data into DEPARTMENT table

--insert into DEPARTMENT (DNAME, MGRSSN, MGRSTARTDATE) values ('Muhasebe', 101, GETDATE())
--insert into DEPARTMENT (DNAME, MGRSSN, MGRSTARTDATE) values ('Muhasebe', 102, GETDATE())


----7 - updating EMPLOYEE table to fill the null values

--update EMPLOYEE SET DNO = 1 WHERE SSN = 101
--update EMPLOYEE SET DNO = 1 WHERE SSN = 102
--update EMPLOYEE SET DNO = 2 WHERE SSN = 103



----8 - creating a trigger after inserting into EMPLOYEE 

--CREATE TRIGGER Employee_Insert ON EMPLOYEE
--AFTER INSERT AS 
--BEGIN--optional
--  INSERT INTO EMPLOYEELOG 
--      (SSN, ACTIONPERFORMED, ACTIONOCCURREDAT)
--      SELECT i.SSN, 'I', SYSDATETIME()
--      FROM inserted AS i;
--END;--optional


----9 - insert new employees

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (104, 'Fatma', 'Sezer', CONVERT(datetime2, '11.10.1980'), 'Istanbul', 'F', 3000, 103, 2 ) 

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (105, 'Ahmet', 'Yesil', CONVERT(datetime2, '11.10.1980'), 'Istanbul', 'F', 3000, 103, 2 ) 


----10 - inserting an employee whose SSN exists in the table

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (103, 'Samet', 'Tonyali', CONVERT(datetime2, '07.21.1988'), 'Istanbul', 'M', 3000, 103, 2 )


----11 - creating an after delete trigger

--CREATE TRIGGER Employee_Delete ON EMPLOYEE
--AFTER DELETE AS 
--BEGIN
--  IF (SELECT d.SSN FROM deleted AS d) = 105
--  BEGIN
--	RAISERROR('You cannot delete Employee 105.',16, 1)
--	ROLLBACK TRANSACTION --rollback delete operation
--  END
--  ELSE
--  INSERT INTO EMPLOYEELOG 
--      (SSN, ACTIONPERFORMED, ACTIONOCCURREDAT)
--      SELECT d.SSN,
--               'D',
--               SYSDATETIME()
--      FROM deleted AS d;
--END;


----12 - try to delete employee 104

--delete from EMPLOYEE WHERE SSN = 104


----13 - try to delete employee 105

--delete from EMPLOYEE WHERE SSN = 105


----14 - creating an after update trigger

--CREATE TRIGGER Employee_Update
--ON EMPLOYEE
--AFTER UPDATE AS 
--	INSERT INTO EMPLOYEELOG 
--      (SSN, ACTIONPERFORMED, ACTIONOCCURREDAT)
--      SELECT i.SSN,
--               'U',
--               SYSDATETIME()
--      FROM inserted AS i;


--15 - updating address of an employee

--update EMPLOYEE SET ADDRESS = 'Ankara' WHERE SSN = 102


--16 - preparing for INSTEAD OF DELETE trigger

--ALTER TABLE DEPARTMENT ADD Status int default 1


--17 - inserting some data into DEPARTMENT table

--insert into DEPARTMENT (DNAME, MGRSSN, MGRSTARTDATE) values('Pazarlama', 101, getdate())
--insert into DEPARTMENT (DNAME, MGRSSN, MGRSTARTDATE) values('Pazarlama', 101, getdate())


--18 - deleting a referenced Department

--delete from DEPARTMENT where DNUMBER = 1


----19 - creating INSTEAD OF DELETE trigger for department

--CREATE TRIGGER Department_Delete
--ON DEPARTMENT
--INSTEAD OF DELETE AS 
--  UPDATE dep SET dep.Status = 0
--  FROM DEPARTMENT dep, deleted d
--  WHERE dep.DNUMBER = d.DNUMBER;


----20 - deleting Departments

--delete from DEPARTMENT where DNUMBER = 4
--delete from DEPARTMENT where DNUMBER = 1


----21 - modifying trigger Employee_Update by adding SALARY control

--ALTER TRIGGER Employee_Update
--ON EMPLOYEE
--AFTER UPDATE AS 
--	SET NOCOUNT ON;
--	IF UPDATE(SALARY)
--	BEGIN
--		RAISERROR('You cannot update Salary data.',16, 1)
--		ROLLBACK TRANSACTION	--rollback update operation
--	END
--	ELSE
--	INSERT INTO EMPLOYEELOG 
--      (SSN, ACTIONPERFORMED, ACTIONOCCURREDAT)
--      SELECT i.SSN,
--               'U',
--               SYSDATETIME()
--      FROM inserted AS i;


----22 - altered Employee_Update examples

--update EMPLOYEE SET ADDRESS = 'Ankara' WHERE SSN = 102
--update EMPLOYEE SET SALARY = 1000 WHERE SSN = 102


----23 - cerating a log table for department

--CREATE TABLE DEPLOG
--(
--	DNO tinyint NOT NULL,
--	ActionPerformed char(1),
--	ActionOccurredAt datetime2
--);


----24 - a trigger with multiple actions

--CREATE TRIGGER Dep_Trigger ON DEPARTMENT
--AFTER INSERT, UPDATE, DELETE AS 
--BEGIN
--  IF @@ROWCOUNT = 0
--	RETURN;
	
--  DECLARE @action as char(1);
--  DECLARE @dno as tinyint;
  
--  IF EXISTS(SELECT * FROM deleted)
--	BEGIN
--		IF EXISTS(SELECT * FROM inserted)
--			SET @action = 'U';
--		ELSE
--			SET @action = 'D';
			
--		SELECT @dno = d.DNUMBER FROM deleted d;
--	END
--  ELSE
--	BEGIN
--		SET @action = 'I';
--		SELECT @dno = i.DNUMBER FROM inserted i;
--	END
	
--  INSERT INTO DEPLOG 
--      (DNO, ActionPerformed, ActionOccurredAt)
--      values(@dno, @action, SYSDATETIME())     
--END;


----25 - Dep_Trigger examples

--insert into DEPARTMENT (DNAME, MGRSSN, MGRSTARTDATE) values ('Muhasebe', 101, GETDATE())

--update DEPARTMENT set DNAME='Satýn Alma' where DNUMBER=5

--delete DEPARTMENT  where DNUMBER=5
