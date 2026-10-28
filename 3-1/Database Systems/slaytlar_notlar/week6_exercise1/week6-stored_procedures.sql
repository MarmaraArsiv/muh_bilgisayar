-- ********************************************************************
--create database COMPANYx
--use COMPANYx

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
--	DNO tinyint NOT NULL,
--	CHECK (SALARY>0)
--);

--CREATE TABLE DEPARTMENT
--(
--	DNUMBER tinyint NOT NULL IDENTITY(1,1) PRIMARY KEY,
--	DNAME nvarchar(50) NOT NULL,
--	MGRSSN smallint NOT NULL,
--	MGRSTARTDATE datetime2 NULL,
--);

--CREATE TABLE PROJECT
--(
--	PNUMBER smallint NOT NULL IDENTITY(1,1) PRIMARY KEY,
--	PNAME nvarchar(25) NOT NULL UNIQUE,
--	PLOCATION nvarchar(30) DEFAULT 'Kadýköy',
--	DNUM tinyint NOT NULL
--);

--ALTER table EMPLOYEE add FOREIGN KEY (SUPERSSN) 
--REFERENCES EMPLOYEE (SSN);
 
--ALTER table EMPLOYEE add FOREIGN KEY  (DNO) 
--REFERENCES DEPARTMENT(DNUMBER); 

--ALTER table DEPARTMENT add FOREIGN KEY (MGRSSN) 
--REFERENCES EMPLOYEE (SSN);

--ALTER table PROJECT add FOREIGN KEY (DNUM) 
--REFERENCES DEPARTMENT(DNUMBER); 

--ALTER TABLE EMPLOYEE ALTER COLUMN DNO tinyint NULL

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, 
--ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (101, 'Ali', 'Küçük', CONVERT(datetime2, 
--'11.10.1980'), 'Istanbul', 'M', 2500, NULL, NULL ) 

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, 
--ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (102, 'Mehmet', 'Büyük', CONVERT(datetime2, 
--'11.10.1985'), 'Istanbul', 'M', 1500, 102, NULL ) 

--insert into EMPLOYEE (SSN, FNAME, LNAME, BDATE, 
--ADDRESS, GENDER, SALARY, SUPERSSN, DNO)
--values (103, 'Ayse', 'Temiz', CONVERT(datetime2, 
--'11.10.1982'), 'Istanbul', 'F', 2000, 103, NULL ) 

--insert into DEPARTMENT (DNAME, MGRSSN, MGRSTARTDATE)
-- values ('Muhasebe', 101, GETDATE())
 
--insert into DEPARTMENT (DNAME, MGRSSN, MGRSTARTDATE)
-- values ('Muhasebe', 102, GETDATE())
 
--update EMPLOYEE SET DNO = 1 WHERE SSN = 101
--update EMPLOYEE SET DNO = 1 WHERE SSN = 102
--update EMPLOYEE SET DNO = 2 WHERE SSN = 103

--ALTER TABLE EMPLOYEE DROP CONSTRAINT FK__EMPLOYEE__DNO__09DE7BCC

--DELETE FROM DEPARTMENT WHERE DNUMBER=2

-- *****************************************************************

--use COMPANY
--GO

----create a simple procedure
--CREATE PROCEDURE GetEmployee AS
--		SELECT SSN, FNAME, LNAME FROM Employee;
--	GO
	
----execute procedure
--exec GetEmployee


----create a procedure with input parameters
--CREATE PROCEDURE EmployeesByBirthDateAndGender
--	@BDate DATETIME, @Gender CHAR = 5
--AS
--SELECT FNAME,LNAME,ADDRESS FROM Employee AS e
--WHERE e.BDATE < @BDate
--AND e.GENDER = @Gender
--GO

--exec EmployeesByBirthDateAndGender '19810101','E';
--exec EmployeesByBirthDateAndGender '19860101','E';
--exec EmployeesByBirthDateAndGender '19810101','K';
--exec EmployeesByBirthDateAndGender '19810101';
--exec EmployeesByBirthDateAndGender @BDate = '20050713', @Gender = 'E';

----create a procedure with input and output parameters
--CREATE PROC GetEmployeeCountBySalary
--	@ESalary int, @ECount int OUTPUT
--AS
--  SELECT @ECount = COUNT(*)
--  FROM Employee AS e
--  WHERE e.SALARY > @ESalary;
--GO

--variable for output
--declare @EmpCount int;

--exec GetEmployeeCountBySalary 500, @EmpCount OUTPUT;

--select @EmpCount;