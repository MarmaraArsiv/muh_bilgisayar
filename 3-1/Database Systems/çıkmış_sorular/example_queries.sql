--Query 1: Retrieve ssn values of the employees. 
Select e.SSN From Employee e
--Query 2: Retrieve ssn, first name and last name of the employees.
Select e.Ssn, e.FirstName, e.LastName From Employee e
--Query 3: Retrieve ssn, first and last name, birthdate and address of the employee whose ssn is 101. 
Select e.Ssn, e.FirstName, e.LastName, e.BirthDate, e.Address From Employee e Where e.Ssn=101
--Query 4: Retrieve the birthdate and address of the employee(s) whose name is “Ali Yılmaz”.
Select e.BirthDate, e.Address From Employee e Where e.FirstName='Ali' and e.LastName='Yılmaz'
--Query 5: Retrieve ssn, full name (first & last concatenated) and salary of female employees whose salary is between 600 and 700. Rename “first & last concatenated” as FullName.
Select e.Ssn, e.FirstName + ' ' + e.LastName fullName, e.Salary From Employee e Where e.Gender='F' and e.Salary>=600 and e.Salary<=700
--Query 6: Retrieve ssn and department number of employees in departments 4 and 6.
Select e.Ssn, e.Dno From Employee e Where e.Dno=4 or e.Dno=6
--Query 7: Retrieve department name and the ssn, first and last name of their managers. 
Select distinct m.Ssn, m.FirstName, m.LastName, d.DName From Employee e inner join Employee m on e.SuperSsn=m.Ssn inner join Department d on m.Dno=D.Dno
--Query 8: Retrieve ssn, first name and department name of employees who work for the department “Marketing”. 
Select e.Ssn, e.FirstName, d.DName From Employee e inner join Department d on e.Dno=d.Dno Where d.DName='Marketing'
--Query 9: Retrieve ssn, first name, department number and department name of employees who work for the department “Marketing”. 
Select e.Ssn, e.FirstName, d.DName, d.Dno From Employee e inner join Department d on e.Dno=d.Dno Where d.DName='Marketing'
--Query 10: Rewrite the previous query using aliases. 
--Query 11: Retrieve ssn, first name and department name of all employees. 
--Query 12: Retrieve ssn, first name and department name of all employees even in the case that the employee does not belong to any department.
Select e.Ssn, e.FirstName, d.DName From Employee e left outer join Department d on e.Dno=d.Dno
--Query 13: For each employee, retrieve the employee’s ssn, first and last name and the ssn, first and last name of his/her immediate supervisor. If he/she has not a supervisor, leave his/her supervisor’s info blank. 
Select e.Ssn, e.FirstName, e.LastName, m.FirstName From Employee e left outer join Employee m on e.SuperSsn=m.Ssn
--Query 14: Retrieve all fields and rows of table Employee. 
Select * From Employee
--Query 15: Retrieve all the fields and rows of tables Department and Employee for managers. 
Select * From Employee e, Department d Where e.Ssn=d.MgrSsn
--Query 16: For every project located in “Kadıköy”, list the project number, the controlling department number and the department manager’s last name, address and birthdate. 
Select * From Project p, Department d, Employee e Where p.Location='Kadıköy' and d.Dno=p.Dno and e.Ssn=d.MgrSsn
--Query 17: Retrieve ssn, last name of the employees who work for the departments 1, 2, and 4. 
Select * From Employee e Where e.Dno in (1,2,4)
--Query 18: Retrieve ssn, last name of the employees who work for the departments “Central Office”, “R&D”, and “Marketing”. 
Select * From Employee e inner join Department d on e.Dno=d.Dno Where d.DName in ('Central Office', 'R&D', 'Marketing')
--Query 19: Retrieve the salary of every employee. 
Select e.Salary From Employee e
--Query 20: Retrieve all distinct salary values. 
Select distinct e.Salary From Employee e
--Query 21: Make a list of all project numbers for projects that involve an employee whose last name is “Şirin” either as a manager of the department that controls the project or as a worker. 
Select p.Pno From (Project p inner join Department d on p.Dno=d.Dno) inner join Employee m on m.Ssn=d.MgrSsn Where m.LastName='Şirin'
--Query 22: Retrieve ssn, first & last name of employees whose address is in “Kadıköy”. 
Select * From Employee e Where e.Address='Kadıköy'
--Query 23: Retrieve ssn, first & last name of employees whose first name starts with the letter “a” and the third character of last name is “r”. 
Select * From Employee e Where e.FirstName like 'a%' and e.LastName like '__r%'
--Query 24: Show the resulting salaries in addition to ssn and last name if every employee working on the “Computer Education” project is given a 10 percent raise. 
Select e.Salary*1.1 newSalary From Employee e inner join WorksOn w on e.Ssn=w.Ssn inner join Project p on w.Pno=p.Pno Where p.PName='Computer Education'
--Query 25: Retrieve a list of employees (ssn, first & last name) and the projects they are working on, ordered by department and; within each department, ordered alphabetically by last name, first name. 
Select * From Employee e inner join WorksOn w on e.Ssn=w.Ssn inner join Project p on w.Pno=p.Pno Order By e.Dno, e.LastName, e.FirstName	
--Query 26: Retrieve the ssn, first & last names of all employees who do not have supervisors.
Select * From Employee e Where e.SuperSsn is NULL
--Query 27: Retrieve the ssn, first & last names of all employees who do not work on any projects. 
Select * From Employee e Where e.Ssn not in (Select distinct w.Ssn From WorksOn w)
--Query 28: Retrieve the name of each employee who has a dependent with the same first name and same gender as the employee. 
Select * From Employee e inner join Dependent d on d.Ssn=e.Ssn Where e.Gender=d.Gender and e.FirstName=d.DependentName
--Query 29: Retrieve the names of employees who have no dependents. 
Select * From Employee e Where e.Ssn not in (Select distinct d.Ssn From Dependent d)
--Query 30: List the ssn and names of employees who have at least one dependent. 
Select distinct e.FirstName From Employee e inner join Dependent d on e.Ssn=d.Ssn
--Query 31: List the names of managers who have at least one dependent.
Select distinct e.FirstName From Department d inner join Employee e on d.MgrSsn=e.Ssn inner join Dependent dp on e.Ssn=dp.Ssn
--Query 32: Find the sum of the salaries of all employees, the maximum salary, the minimum salary and the average salary. 
Select sum(e.Salary) salarySum, max(e.Salary) maxSalary, avg(e.Salary) avgSalary From Employee e
--Query 33: Find the sum of the salaries of all employees of the department “Central Office”, as well as the maximum salary, the minimum salary and the average salary in this department. 
--Query 34: How many employees are there in the company? 
Select count(*) From Employee e
--Query 35: What is the number of employees in the department “Central Office”? 
--Query 36: Count the number of distinct salary values in the database. 
Select count(distinct e.Salary) From Employee e
--Query 37: For each department; retrieve the department number and the number of employees in the department. 
Select d.Dno, count(e.Ssn) From Employee e left outer join Department d on e.Dno=d.Dno Group By d.Dno
--Query 38: For each department; retrieve the department number, the number of employees in the department and their average salary. 
Select d.Dno, count(*), avg(e.Salary) From Employee e left outer join Department d on e.Dno=d.Dno Group By d.Dno
--Query 39: For each project; retrieve the project number, the project name and the number of employees who work on that project. 
Select p.Pno, p.PName, count(*) workers From Project p inner join WorksOn w on p.Pno=w.Pno Group By p.Pno, p.PName
--Query 40: For each employee; retrieve ssn, last name and total number of hours worked on all projects. 
Select e.Ssn, e.LastName, sum(w.Hours) From Employee e left outer join WorksOn w on e.Ssn=w.Ssn Group By e.Ssn, e.LastName
--Query 41: For the employees that have dependents; retrieve ssn and the number of his/her dependents. 
Select d.Ssn, count(*) From Employee e inner join Dependent d on e.Ssn=d.Ssn Group By d.Ssn
--Query 42: For each employee; retrieve ssn, last name, total number of hours worked on all projects, and the number of his/her dependents. 
Select e.Ssn, e.LastName, sum(w.Hours) workHours, count(distinct d.Ssn) numOfDep From Employee e left outer join WorksOn w on e.Ssn=w.Ssn left outer join Dependent d on e.Ssn=d.Ssn Group By e.Ssn, e.LastName
--Query 43: For each project located in “Üsküdar”; retrieve ssn, project number and the total number of hours worked on. 
Select e.Ssn, p.Pno, sum(w.Hours) From Project p inner join WorksOn w on p.Pno=w.Pno left outer join Employee e on w.Ssn=e.Ssn Where p.Location='Üsküdar' Group By e.Ssn, p.Pno
--Query 44: For each project on which at least one employee works; retrieve the project number, the project name and the number of employees who work on the project. 
Select p.Pno, p.PName, count(distinct w.Ssn) From Employee e inner join WorksOn w on e.Ssn=w.Ssn inner join Project p on w.Pno=p.Pno Group By p.Pno, p.PName
--Query 45: For each project on which more than one employee works; retrieve the project number, the project name and the number of employees who work on the project. 
Select p.Pno, p.PName, count(distinct w.Ssn) From Employee e inner join WorksOn w on e.Ssn=w.Ssn inner join Project p on w.Pno=p.Pno Group By p.Pno, p.PName Having count(*) > 1
--Query 46: For each project on which more than one employee works, retrieve the project number. 
--Query 47: For each department that has at least three employees with salary greater than $500; retrieve the department number, department name and the number of its employees with salary greater than $500. 
Select d.Dno, d.DName, count(distinct e.Ssn) eCount From Department d inner join Employee e on d.Dno=e.Dno Where e.Salary >= 500 Group By d.Dno, d.DName Having count(*)>3
--Query 48: For each department that has at least three employees; retrieve the department number, department name and the number of its employees. 
Select d.Dno, d.DName, count(distinct e.Ssn) eCount From Department d inner join Employee e on d.Dno=e.Dno Group By d.Dno, d.DName Having count(*)>=3
--Query 49: For each department that has at least three employees; retrieve the department number, department name and the number of its employees with salary greater than $500. 
--Query 50: For each employee who has at least two dependents; retrieve ssn, first & last name (concatenated, rename as “Full Name”), 
--department name that s/he works for, total project hours worked, number of projects worked on and average project hours worked on. 
--Discard the employees who does not work on at least three projects. Order the list by department name ascending and average hours descending.
Select e.Ssn, e.FirstName + ' ' + e.LastName "Full Name", d.Dname, sum(w.Hours) TotalHrs, 
count(w.Ssn) NoOfProj, avg(w.Hours*1.0) AvgHrs From Employee e left outer join Department d on e.Dno=d.Dno left outer join WorksOn w on e.Ssn=w.Ssn
Where e.Ssn in (Select dpn.Ssn From Dependent dpn Group By dpn.Ssn Having count(*)>=2) 
Group By e.Ssn, e.FirstName + ' ' + e.LastName, d.Dname
--Query 51: Insert a new record to table WorksOn with values Ssn=505, Pno=7, and Hours=8. 
Insert into WorksOn (Ssn, Pno, Hours) Values (505,7,8) 
--Query 52: Delete the records on table WorksOn with values Ssn=505 and Pno=7. 
Delete From WorksOn Where Ssn=505 and Pno=7
--Query 53: Delete the employees who work on projects at most 3 hours. 
Delete e From Employee e Where e.Ssn in (Select e.Ssn From Employee e inner join WorksOn w on e.Ssn=w.Ssn Group By e.Ssn Having sum(w.Hours)<= 3)
--Query 54: Update the address of “Meryem Beyza” as “Ataşehir”. 
Update Employee Set Address='Ataşehir' Where FirstName='Meryem' and LastName='Beyza'
--Query 55: Update the salaries by 20% of the employees of department “Sales” those work on projects more than 5 hours. 
Update Employee Set Salary=Salary*1.2 Where Ssn in (Select e.Ssn From Employee e inner join Department d on e.Dno=d.Dno left outer join WorksOn w on e.Ssn=w.Ssn Where d.DName='Sales' Group By e.Ssn Having sum(w.Hours)>5)
--Query 56: Create a table UserLog with fields LogID, UserID, and LogDescription. LogID will be unique, and UserID will reference Ssn of Employee. 
CREATE TABLE UserLog (
	LogId int,
	UserId int,
	PRIMARY KEY (LogId),
	FOREIGN KEY (UserId) REFERENCES Employee(Ssn)
)
--Query 57: Add a new field LogDate to table UserLog.
ALTER TABLE UserLog
ADD LogDate smalldatetime;
--Query 58
ALTER TABLE DEPARTMENT
ADD FOREIGN KEY (managerID) REFERENCES MANAGER(staffID);