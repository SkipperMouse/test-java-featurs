DROP TABLE IF EXISTS book;

CREATE TABLE book
(
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title   VARCHAR(50),
    author  VARCHAR(30),
    price   BIGINT,
    amount  INT
);

INSERT INTO book(title, author, price, amount)
VALUES ('Белая гвардия', 'Булгаков М.А.', 54000, 5);
INSERT INTO book(title, author, price, amount)
VALUES ('Идиот', 'Достоевский Ф.М.', 46000, 10);
INSERT INTO book(title, author, price, amount)
VALUES ('Братья Карамазовы', 'Достоевский Ф.М.', 79900, 2);
INSERT INTO book(title, author, price, amount)
VALUES ('Капитанская Дочка', 'Пушкин А.С', 32600, 2);

-- ПРАВИЛЬНЫЙ ОТВЕТ:
-- 1,Белая гвардия,Булгаков М.А.,54000,5
-- 2,Идиот,Достоевский Ф.М.,46000,10


-- Вывести информацию о тех книгах, количество экземпляров (amount) которых в таблице book не дублируется.

SELECT *
FROM book
WHERE amount NOT IN (SELECT amount
                     FROM book
                     GROUP BY amount
                     HAVING COUNT(amount) > 1)








