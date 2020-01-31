select user(), database();

select deptno, deptname, floor from department;
desc employee;

select empno, empname, title, manager, salary, dno 
	from employee 
	where dno = 2;
	

select e.empno, e.empname, e.title, m.empname as manager_name, m.empno as manager_no, e.salary, e.dno, d.deptname 
	from employee e left join employee m on e.manager = m.empno join department d on e.dno = d.deptno
	where e.dno = 2; 
	

select * from department;

INSERT INTO department VALUES(5, '마케팅', 0);
update department set deptname = '마케팅2', floor = 10 where deptno = 5;
delete from department where deptno = 6;