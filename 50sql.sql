
--50道sql基础操作练习题

--创建学生表`student`
CREATE TABLE `student` (
  `s_id` varchar(20) NOT NULL,
  `s_name` varchar(20) NOT NULL DEFAULT '',
  `s_birth` varchar(20) NOT NULL DEFAULT '',
  `s_sex` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--创建老师表`teacher`
CREATE TABLE `teacher` (
  `t_id` varchar(20) NOT NULL,
  `t_name` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--创建学生课程表`course`
CREATE TABLE `course` (
  `c_id` varchar(20) NOT NULL,
  `c_name` varchar(20) NOT NULL DEFAULT '',
  `t_id` varchar(20) NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--创建学生课程分数表`score`
CREATE TABLE `score` (
  `s_id` varchar(20) NOT NULL,
  `c_id` varchar(20) NOT NULL,
  `s_score` int(3) DEFAULT NULL,
  PRIMARY KEY (`s_id`,`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8




-- 1、查询"01"课程比"02"课程成绩高的学生的信息及课程分数
SELECT a.*,b.`s_score` AS "01",c.`s_score` AS "02"
FROM student a,score b,score c
WHERE	a.s_id = b.`s_id`
AND a.s_id = c.`s_id`
AND b.`c_id` = "01"
AND c.`c_id` = "02"
AND b.`s_score` > c.`s_score`;



-- 2、查询"01"课程比"02"课程成绩低的学生的信息及课程分数
SELECT a.*,b.`s_score` AS "01",c.`s_score` AS "02"
FROM student a,score b,score c
WHERE	a.s_id = b.`s_id`
AND a.s_id = c.`s_id`
AND b.`c_id` = "01"
AND c.`c_id` = "02"
AND b.`s_score` < c.`s_score`;



-- 3、查询平均成绩大于等于60分的同学的学生编号和学生姓名和平均成绩
SELECT a.s_id, a.s_name, ROUND(AVG(b.s_score),1) AS avg_score
FROM student a LEFT JOIN score b ON a.s_id = b.s_id 
GROUP BY a.s_id HAVING avg_score >= 60;



-- 4、查询平均成绩小于60分的同学的学生编号和学生姓名和平均成绩
SELECT a.s_id, a.s_name , ROUND(AVG(b.`s_score`), 1) AS avg_score  
FROM student a LEFT JOIN score b ON a.s_id = b.`s_id`
GROUP BY a.s_id
HAVING  avg_score <60;



-- 5、查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩
SELECT a.s_id, a.s_name , COUNT(b.c_id) AS c_count , SUM(b.s_score) AS s_all
FROM student a LEFT JOIN score b ON a.s_id = b.s_id
GROUP BY a.s_id;



-- 6、查询"李"姓老师的数量 
SELECT COUNT(t_id) 
FROM teacher
WHERE t_name LIKE '李%';



-- 7、查询学过"张三"老师授课的同学的信息 
SELECT * 
FROM student 
WHERE s_id IN(
	SELECT DISTINCT(b.s_id)
	FROM  score b LEFT JOIN course c ON c.`c_id` = b.`c_id` LEFT JOIN teacher d ON c.`t_id`=d.t_id
	WHERE  d.t_name = '张三'
);



-- 8、查询没学过"张三"老师授课的同学的信息 
SELECT * 
FROM student 
WHERE s_id NOT IN(
	SELECT DISTINCT(b.s_id)
	FROM  score b LEFT JOIN course c ON c.`c_id` = b.`c_id` LEFT JOIN teacher d ON c.`t_id`=d.t_id
	WHERE  d.t_name = '张三'
);



-- 9、查询学过编号为"01"并且也学过编号为"02"的课程的同学的信息
SELECT * 
FROM student
WHERE s_id IN(
	SELECT  a.s_id
	FROM score a LEFT JOIN score b ON a.`s_id`=b.`s_id`
	WHERE a.`c_id` = '01'
	AND b.`c_id` = '02'
);



-- 10、查询学过编号为"01"但是没有学过编号为"02"的课程的同学的信息
SELECT *
FROM student
WHERE s_id IN(
	SELECT s_id
	FROM score
	WHERE c_id = '01'
	AND s_id NOT IN(
		SELECT s_id
		FROM score
		WHERE c_id = '02'
	)
);



-- 11、查询没有学全所有课程的同学的信息 
SELECT a.*
FROM student a LEFT JOIN score b ON a.s_id = b.`s_id`
GROUP BY a.s_id
HAVING COUNT(DISTINCT b.`c_id`) < (
	SELECT COUNT(DISTINCT c_id)
	FROM course);


SELECT *
FROM student
WHERE s_id NOT IN (
	SELECT s_id
	FROM score
	GROUP BY s_id
	HAVING COUNT(DISTINCT c_id) = (
		SELECT	COUNT(DISTINCT c_id)
		FROM course		
	));
	


-- 12、查询至少有一门课与学号为"01"的同学所学相同的同学的信息 
SELECT  DISTINCT a.* 
FROM student a LEFT JOIN score b ON a.s_id=b.`s_id`
WHERE b.`c_id` IN(
	SELECT DISTINCT c.c_id
	FROM score c
	WHERE s_id = '01'
);


SELECT * 
FROM student 
WHERE s_id IN(
	SELECT DISTINCT a.s_id 
	FROM score a 
	WHERE a.c_id IN(
		SELECT a.c_id 
		FROM score a 
		WHERE a.s_id='01')
	);



-- 13、查询和"01"号的同学学习的课程完全相同的其他同学的信息 
SELECT  * 
FROM student
WHERE s_id IN(
	SELECT s_id
	FROM score
	GROUP BY s_id
	HAVING COUNT(s_id)=(
		SELECT COUNT(c_id)
		FROM score
		WHERE s_id = '01'
	)
)
AND s_id NOT IN(
	SELECT s_id
	FROM score
	WHERE c_id IN(
		SELECT DISTINCT c_id
		FROM score 
		WHERE c_id NOT IN(
			SELECT c_id
			FROM score
			WHERE s_id = '01'
		)
	)
)
AND s_id NOT IN ('01');


SELECT tb1.*
FROM student tb1,(
	SELECT GROUP_CONCAT(c_id ORDER BY c_id) AS g1,s_id
	FROM score
	GROUP BY s_id
) AS tb2,(
	SELECT GROUP_CONCAT(c_id ORDER BY c_id) AS g2
	FROM score
	WHERE s_id ='01'
	GROUP BY s_id
) AS tb3 
WHERE tb1.s_id  = tb2.s_id
AND tb2.g1=tb3.g2
AND tb1.s_id !='01';
	


-- 14、查询没学过"张三"老师讲授的任一门课程的学生姓名 
SELECT s_name
FROM student
WHERE s_id NOT IN(
	SELECT DISTINCT a.s_id
	FROM score AS a,course AS b,teacher AS c
	WHERE a.`c_id`=b.`c_id`
	AND b.`t_id`=c.`t_id`
	AND c.`t_name` ='张三'
);



-- 15、查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩 
SELECT a.s_id, a.s_name, ROUND(AVG(b.s_score)) AS avg_score
FROM student a, score b
WHERE a.`s_id`=b.`s_id`
AND a.`s_id` IN(
	SELECT c.`s_id`
	FROM score c
	WHERE c.`s_score` <60
	GROUP BY c.`s_id`
	HAVING COUNT(1) >1
)
GROUP BY a.`s_id`;



-- 16、检索"01"课程分数小于60，按分数降序排列的学生信息
SELECT a.*, b.`c_id`, b.`s_score`
FROM student a, score b
WHERE a.`s_id`=b.`s_id`
AND b.`s_score`<60
AND b.`c_id`='01'
ORDER BY b.`s_score` DESC;



-- 17、按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩
SELECT b.`s_id`, a.`s_name`,
(SELECT c.`s_score` FROM score c WHERE b.`s_id`=c.s_id AND c.`c_id`='01') AS '语文',
(SELECT c.`s_score` FROM score c WHERE b.`s_id`=c.s_id AND c.`c_id`='02') AS '数学',
(SELECT c.`s_score` FROM score c WHERE b.`s_id`=c.s_id AND c.`c_id`='03') AS '英语',
ROUND(AVG(b.`s_score`),1) AS '平均分'
FROM student a, score b
WHERE a.`s_id`=b.`s_id`
GROUP BY b.`s_id`
ORDER BY 6 DESC;


SELECT  a.`s_name`,a.`s_id`,
MAX(CASE b.`c_id` WHEN '01' THEN b.`s_score` ELSE 0 END) 语文,
MAX(CASE b.`c_id` WHEN '02' THEN b.`s_score` ELSE 0 END) 数学,
MAX(CASE b.`c_id` WHEN '03' THEN b.`s_score` ELSE 0 END) 英语,
ROUND(AVG(b.`s_score`))
FROM student a LEFT JOIN score b ON a.`s_id`=b.`s_id`	
GROUP BY a.`s_id`
ORDER BY 5 DESC;



-- 18.查询各科成绩最高分、最低分和平均分：以如下形式显示：课程ID，课程name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
-- 及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
SELECT b.`c_id` 课程ID,a.`c_name` 课程name,MAX(b.`s_score`) 最高分,MIN(b.`s_score`) 最新分,
ROUND(AVG(b.`s_score`),2) 平均分,
ROUND(100*(SUM(CASE WHEN b.`s_score` >=60 THEN 1 ELSE 0 END)/SUM(CASE WHEN b.`s_score` THEN 1 ELSE 0 END)),2) 及格率,
ROUND(100*(SUM(CASE WHEN b.`s_score` >=70 AND b.`s_score` <=80 THEN 1 ELSE 0 END)/SUM(CASE WHEN b.`s_score` THEN 1 ELSE 0 END)),2) 中等率,
ROUND(100*(SUM(CASE WHEN b.`s_score` >=90 THEN 1 ELSE 0 END)/SUM(CASE WHEN b.`s_score` THEN 1 ELSE 0 END)),2) 优秀率
FROM course a LEFT JOIN score b ON a.`c_id`=b.`c_id`
GROUP BY a.`c_id`, a.`c_name`;



-- 19、按各科成绩进行排序，并显示排名
-- mysql没有rank函数	
(SELECT a.`s_id`,a.`c_id`,a.`s_score`,@i:= @i+1 AS 排名
FROM score a,(SELECT @i:=0) b
WHERE a.c_id='01'
GROUP BY a.s_id
ORDER BY a.s_score DESC)
UNION
(SELECT a.`s_id`,a.`c_id`,a.`s_score`,@j:= @j+1 AS 排名
FROM score a,(SELECT @j:=0) b
WHERE a.c_id='02'
GROUP BY a.s_id
ORDER BY a.s_score DESC)
UNION
(SELECT a.`s_id`,a.`c_id`,a.`s_score`,@k:= @k+1 AS 排名
FROM score a,(SELECT @k:=0) b
WHERE a.c_id='03'
GROUP BY a.s_id
ORDER BY a.s_score DESC);


SELECT a.`s_id`,a.`c_id`, 
@i:=@i+1 AS 排名,
@j:=(CASE WHEN @score=a.`s_score` THEN @j ELSE @i END) AS 并列排名1,
@k:=(CASE WHEN @score=a.`s_score` THEN @k ELSE @k:=@k+1 END) AS 并列排名2,
@score:=a.`s_score` AS 分数
FROM (SELECT @i:=0,@j:=0,@k:=0,@score:=0) s,score a
GROUP BY a.`s_id`,a.`c_id`
ORDER BY a.`s_score` DESC;



-- 20、查询学生的总成绩并进行排名
SELECT a.`s_id`,a.`s_name`,
@i:=(@i +1) AS paiming,
@sc:=SUM(b.`s_score`) AS zongfen
FROM student a  LEFT JOIN score b ON a.`s_id`=b.`s_id`,(SELECT @i:=0,@sc:=0) s
GROUP BY a.`s_id`
ORDER BY @sc DESC;



-- 21、查询不同老师所教不同课程平均分从高到低显示 
SELECT t.`t_name`,c.`c_name`,ss.pg
FROM teacher t LEFT JOIN course c ON t.`t_id`=c.`t_id` LEFT JOIN
(SELECT s.`c_id` AS id,ROUND(AVG(s.`s_score`),2) AS pg FROM score s GROUP BY s.`c_id`) AS ss ON c.`c_id`=ss.id
ORDER BY ss.pg DESC;



-- 22、查询所有课程的成绩第2名到第3名的学生信息及该课程成绩
(SELECT s.*, ss.c_id,ss.s_score
FROM student s,score ss
WHERE s.s_id=ss.s_id
AND ss.c_id = '01'
ORDER BY ss.s_score DESC
LIMIT 1,2
)
UNION
(SELECT s.*, ss.c_id,ss.s_score
FROM student s,score ss
WHERE s.s_id=ss.s_id
AND ss.c_id = '02'
ORDER BY ss.s_score DESC
LIMIT 1,2
)
UNION
(SELECT s.*, ss.c_id,ss.s_score
FROM student s,score ss
WHERE s.s_id=ss.s_id
AND ss.c_id = '03'
ORDER BY ss.s_score DESC
LIMIT 1,2
);



-- 23、统计各科成绩各分数段人数：课程编号,课程名称,[100-85],[85-70],[70-60],[0-60]及所占百分比
SELECT b.`c_id` 课程编号,a.`c_name` 课程名称,
SUM(CASE WHEN b.`s_score` >85 AND b.`s_score` <=100 THEN 1 ELSE 0 END) '[100-85]人数',
ROUND(100*(SUM(CASE WHEN b.`s_score` >85 AND b.`s_score` <=100 THEN 1 ELSE 0 END)/SUM(CASE WHEN b.`s_score` THEN 1 ELSE 0 END)),2) '[100-85]百分比',
SUM(CASE WHEN b.`s_score` >70 AND b.`s_score` <=85 THEN 1 ELSE 0 END) '[85-70]人数',
ROUND(100*(SUM(CASE WHEN b.`s_score` >70 AND b.`s_score` <=85 THEN 1 ELSE 0 END)/SUM(CASE WHEN b.`s_score` THEN 1 ELSE 0 END)),2) '[85-70]百分比',
SUM(CASE WHEN b.`s_score` >60 AND b.`s_score` <=70 THEN 1 ELSE 0 END) '[70-60]人数',
ROUND(100*(SUM(CASE WHEN b.`s_score` >60 AND b.`s_score` <=70 THEN 1 ELSE 0 END)/SUM(CASE WHEN b.`s_score` THEN 1 ELSE 0 END)),2) '[70-60]百分比',
SUM(CASE WHEN b.`s_score` >=0 AND b.`s_score` <=60 THEN 1 ELSE 0 END) '[0-60]人数',
ROUND(100*(SUM(CASE WHEN b.`s_score` >=0 AND b.`s_score` <=60 THEN 1 ELSE 0 END)/SUM(CASE WHEN b.`s_score` THEN 1 ELSE 0 END)),2) '[0-60]百分比'
FROM course a LEFT JOIN score b ON a.`c_id`=b.`c_id`
GROUP BY a.`c_id`, a.`c_name`;



-- 24、查询学生平均成绩及其名次 
SELECT st.`s_id`,st.`s_name`, @sc:=(AVG(s.`s_score`)) AS pg,@i:=@i+1 AS pm
FROM student st LEFT JOIN score s ON st.`s_id`=s.`s_id`,
(SELECT @i:=0,@sc:=0) s
GROUP BY st.`s_id`
ORDER BY @sc DESC;



-- 25、查询各科成绩前三名的记录
SELECT a.s_id,a.c_id,a.s_score FROM score a 
LEFT JOIN score b ON a.c_id = b.c_id AND a.s_score>b.s_score
GROUP BY a.s_id,a.c_id HAVING COUNT(a.s_id)<3
ORDER BY a.c_id,a.s_score DESC;
	
			
SELECT a.`s_id`,a.`c_id`,a.`s_score`
FROM score a LEFT JOIN score b ON a.`c_id`=b.`c_id`
AND a.`s_score` >=b.`s_score`
GROUP BY a.`s_id`,a.`c_id` 
HAVING COUNT(a.`s_id`) <4
ORDER BY a.`c_id`, a.`s_score` DESC ;	



-- 26、查询每门课程被选修的学生数 
SELECT s.`c_id`,COUNT(s.`s_id`)
FROM score s
GROUP BY s.`c_id`;



-- 27、查询出只有两门课程的全部学生的学号和姓名 
SELECT s.`s_id`,st.`s_name`
FROM student st,score s
WHERE st.`s_id`=s.`s_id`
GROUP BY s.`s_id`
HAVING COUNT(s.`c_id`) =2;



-- 28、查询男生、女生人数 
SELECT st.`s_sex`,COUNT(st.`s_id`)
FROM student st
GROUP BY st.`s_sex`;



-- 29、查询名字中含有"风"字的学生信息
SELECT st.*
FROM student st
WHERE st.`s_name` LIKE '%风%';



-- 30、查询同名同性学生名单，并统计同名人数 
SELECT st.`s_name`,st.`s_sex`,COUNT(1)
FROM student st,student st1
WHERE st.`s_name`=st1.`s_name`
AND st.`s_sex`=st1.`s_sex`
AND st.`s_id`!=st1.`s_id`
GROUP BY st.`s_name`;



-- 31、查询1990年出生的学生名单
SELECT  * 
FROM student 
WHERE s_birth LIKE '1990%';



-- 32、查询每门课程的平均成绩，结果按平均成绩降序排列，平均成绩相同时，按课程编号升序排列 
SELECT s.`c_id`,ROUND(AVG(s.`s_score`),2) AS pj 
FROM score s
GROUP BY s.`c_id`
ORDER BY pj DESC, s.`c_id` ASC;



-- 33、查询平均成绩大于等于85的所有学生的学号、姓名和平均成绩 
SELECT st.`s_id`,st.`s_name`,ROUND(AVG(s.`s_score`),2) AS avgsc
FROM student st,score s
WHERE st.`s_id`=s.`s_id`
GROUP BY s.`s_id`
HAVING avgsc >=85;



-- 34、查询课程名称为"数学"，且分数低于60的学生姓名和分数 
SELECT st.`s_name`,s.`s_score`
FROM student st,course c,score s
WHERE st.`s_id`=s.`s_id`
AND c.`c_id`=s.`c_id`
AND c.`c_name`='数学'
AND s.`s_score` <60;



-- 35、查询所有学生的课程及分数情况 
SELECT st.`s_id`,st.`s_name`,c.`c_id`,c.`c_name`,s.`s_score`
FROM student st LEFT JOIN score s ON st.`s_id`=s.`s_id`
LEFT JOIN course c ON s.`c_id`=c.`c_id`
ORDER BY st.`s_id`;


SELECT st.`s_id`,st.`s_name`,
SUM(CASE WHEN c.`c_name`='语文' THEN s.`s_score` ELSE 0 END)  '语文',
SUM(CASE WHEN c.`c_name`='数学' THEN s.`s_score` ELSE 0 END)  '数学',
SUM(CASE WHEN c.`c_name`='英语' THEN s.`s_score` ELSE 0 END)  '英语',
SUM(s.`s_score`) '总分'
FROM student st LEFT JOIN score s ON st.`s_id`=s.`s_id`
LEFT JOIN course c ON s.`c_id`=c.`c_id`
GROUP BY st.`s_id`,st.`s_name`;



-- 36、查询任何一门课程成绩在70分以上的姓名、课程名称和分数 
SELECT st.`s_name`,c.`c_name`,s.`s_score`
FROM student st,score s,course c
WHERE st.`s_id`=s.`s_id`
AND s.`c_id`=c.`c_id`
AND s.`s_score` >=70;



-- 37、查询不及格的课程
SELECT st.`s_id`,st.`s_name`,c.`c_name`,s.`s_score`
FROM student st,score s,course c
WHERE st.`s_id`=s.`s_id`
AND s.`c_id`=c.`c_id`
AND s.`s_score` <60;



-- 38、查询课程编号为01且课程成绩在80分以上的学生的学号和姓名 
SELECT st.`s_id`,st.`s_name`
FROM student st,score s
WHERE st.`s_id`=s.`s_id`
AND s.`c_id`='01'
AND s.`s_score` >80;



-- 39、求每门课程的学生人数 
SELECT c.`c_name`, c.`c_id`,COUNT(1)
FROM course c LEFT JOIN score s ON c.`c_id`=s.`c_id`
GROUP BY c.`c_id`;



-- 40、查询选修"张三"老师所授课程的学生中，成绩最高的学生信息及其成绩
SELECT st.*,s.`s_score`
FROM student st,score s,course c,teacher t
WHERE st.`s_id`=s.`s_id`
AND s.`c_id`=c.`c_id`
AND c.`t_id`=t.`t_id`
AND t.`t_name`='张三'
ORDER BY s.`s_score` DESC
LIMIT 1;



-- 41、查询不同课程成绩相同的学生的学生编号、课程编号、学生成绩 
SELECT DISTINCT s.`s_id`,s.`c_id`,s.`s_score`
FROM score s,score s1
WHERE  s.`c_id`!=s1.`c_id`
AND s.`s_score`=s1.`s_score`;



-- 42、查询每门课程成绩最好的前两名 
SELECT a.`s_id`,a.`c_id`,a.`s_score`
FROM score a
WHERE (SELECT COUNT(1) FROM score b WHERE a.`c_id`=b.`c_id` AND b.`s_score`>=a.`s_score`) <3
ORDER BY a.`c_id`;		

		

-- 43、统计每门课程的学生选修人数（超过5人的课程才统计）。要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列  
SELECT s.`c_id`,COUNT(s.`c_id`) AS total
FROM score s
GROUP BY s.`c_id`
HAVING total > 5
ORDER BY total DESC,s.`c_id` ASC;



-- 44、检索至少选修两门课程的学生学号 
SELECT s.`s_id`
FROM score s
GROUP BY s.`s_id`
HAVING (COUNT(s.`c_id`))>1;



-- 45、查询选修了全部课程的学生信息 
SELECT st.*
FROM student st,score s
WHERE st.`s_id`=s.`s_id`
GROUP BY st.`s_id`
HAVING (COUNT(s.`c_id`)) = (SELECT COUNT(1) AS total FROM course );



-- 46、查询各学生的年龄
SELECT TIMESTAMPDIFF(YEAR,st.`s_birth`,CURDATE()) AS age
FROM student st;



-- 47、查询本周过生日的学生
SELECT st.*
FROM student st
WHERE WEEK(CONCAT(YEAR(CURRENT_DATE),RIGHT(st.`s_birth`,6)),1)=WEEK(st.`s_birth`,1);



-- 48、查询下周过生日的学生
SELECT st.*
FROM student st
WHERE WEEK(CONCAT(YEAR(CURRENT_DATE),RIGHT(st.`s_birth`,6)),1)+1=WEEK(st.`s_birth`,1);



-- 49、查询本月过生日的学生
SELECT st.*
FROM student st
WHERE MONTH(CURRENT_DATE)=MONTH(st.`s_birth`);



-- 50、查询下月过生日的学生
SELECT st.*
FROM student st
WHERE MONTH(CURRENT_DATE)+1=MONTH(st.`s_birth`);

