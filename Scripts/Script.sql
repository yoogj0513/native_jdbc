select user(), database();

select * from employee;
select * from department;

select * from department where deptno = 3;

select deptno, deptname, floor from department where deptno=3;

select empno, empname, title, manager, salary, dno, pic from employee where empno = 1004;