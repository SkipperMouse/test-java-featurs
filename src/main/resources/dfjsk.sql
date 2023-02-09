CREATE TABLE IF NOT EXISTS schedule
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    date TIMESTAMP NOT NULL,
    class INT NOT NULL,
    teacher VARCHAR(100) NOT NULL,
    subject VARCHAR(100) NOT NULL,
    classroom INT NOT NULL,
    CONSTRAINT pk_schedule PRIMARY KEY (id)

);

insert into schedule(date, class, teacher, subject, classroom)
values
    ('2022-02-01', 9, 'Nikollo Kopernico', 'Stars', 47),
    ('2022-02-01', 9, 'Albert Einstein', 'Math', 13),
    ('2022-02-01', 9, 'Steve Jobs', 'English', 13),
    ('2022-02-01', 9, 'Michael Jordan', 'Baseball', 13),
    ('2022-02-01', 9, 'Sergei Guriev', 'Economy', 34),
    ('2022-02-01', 9, 'Steve Jobs', 'English', 35),
    ('2022-02-01', 9, 'Steve Jobs', 'English', 36),
    ('2022-02-01', 9, 'Hannibal Lecter', 'Biolody', 37),
    ('2022-02-01', 9, 'Steve Jobs', 'English', 38),
    ('2022-02-01', 9, 'Hannibal Lecter', 'Biology', 39),
    ('2022-02-01', 9, 'Steve Jobs', 'Baseball', 40),
    ('2022-02-01', 11, 'Steve Jobs', 'English', 41),
    ('2022-02-01', 11, 'Sergei Guriev', 'Economy', 43),
    ('2022-02-01', 11, 'Michael Jordan', 'Baseball', 47),
    ('2022-02-01', 11, 'Steve Jobs', 'English', 36),
    ('2022-02-01', 11, 'Hannibal Lecter', 'Biology', 34),
    ('2022-02-01', 11, 'Steve Jobs', 'English', 13),
    ('2022-02-01', 9, 'Albert Einstein', 'Math', 35),
    ('2022-02-01', 9, 'Sergei Guriev', 'English', 47),
    ('2022-02-01', 9, 'Michael Jordan', 'Baseball', 40),
    ('2022-02-01', 9, 'Steve Jobs', 'English', 53),
    ('2022-02-01', 9, 'Hannibal Lecter', 'Biology', 53),
    ('2022-02-01', 9, 'Steve Jobs', 'English', 38),
    ('2022-02-01', 9, 'Michael Jordan', 'Baseball', 43),
    ('2022-02-01', 9, 'Sergei Guriev', 'Economy', 39),
    ('2022-02-01', 9, 'Steve Jobs', 'English', 34),
    ('2022-02-01', 9, 'Hannibal Lecter', 'Biology', 35),
    ('2022-02-01', 10, 'Steve Jobs', 'English', 36),
    ('2022-02-01', 10, 'Steve Jobs', 'Baseball', 37),
    ('2022-02-01', 10, 'Albert Einstein', 'Math', 38),
    ('2022-02-01', 10, 'Hannibal Lecter', 'Biology', 53),
    ('2022-02-01', 10, 'Steve Jobs', 'English', 40),
    ('2022-02-01', 10, 'Steve Jobs', 'English', 53),
    ('2022-02-01', 10, 'Michael Jordan', 'Baseball', 53),
    ('2022-02-01', 9, 'Albert Einstein', 'Math', 4),
    ('2022-02-01', 8, 'Sergei Guriev', 'Economy', 42),
    ('2022-02-01', 8, 'Hannibal Lecter', 'Biology', 43),
    ('2022-02-01', 8, 'Michael Jordan', 'Baseball', 42),
    ('2022-02-01', 8, 'Albert Einstein', 'Math', 43),
    ('2022-02-01', 8, 'Sergei Guriev', 'Economy', 43);

select classroom
FROM Schedule
GROUP BY classroom
HAVING COUNT(classroom) = (
    select count(classroom) as classroom
    FROM Schedule
    group by classroom
    order by classroom desc
    LIMIT 1
    );