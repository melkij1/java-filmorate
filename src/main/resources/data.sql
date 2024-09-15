SET REFERENTIAL_INTEGRITY FALSE;

TRUNCATE TABLE FILM_GENRES;

TRUNCATE TABLE FRIENDSHIP;

TRUNCATE TABLE LIKES;

TRUNCATE TABLE FILMS RESTART IDENTITY;

TRUNCATE TABLE USERS RESTART IDENTITY;

TRUNCATE TABLE GENRES RESTART IDENTITY;

TRUNCATE TABLE MPA_RATING RESTART IDENTITY;

TRUNCATE TABLE STATUS RESTART IDENTITY;

SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO MPA_RATING (NAME, DESCRIPTION)
VALUES ('G',      'У фильма нет возрастных ограничений'),                     -- 1
    ('PG',    'Детям рекомендуется смотреть фильм с родителями'),              -- 2
    ('PG-13', 'Детям до 13 лет просмотр не желателен'),                   -- 3
    ('R',     'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),  -- 4
    ('NC-17', 'Лицам до 18 лет просмотр запрещён');                     -- 5

INSERT INTO GENRES (NAME)
VALUES ('Комедия'),     -- 1
    ('Драма'),       -- 2
    ('Мультфильм'),    -- 3
    ('Триллер'),    -- 4
    ('Документальный'), -- 5
    ('Боевик'),      -- 6
    ('Фантастика'),    -- 7
    ('Фэнтези'),    -- 8
    ('Приключения'),  -- 9
    ('Криминал'),    -- 10
    ('Семейный'),    -- 11
    ('Детектив');    -- 12

INSERT INTO FILMS (NAME , DESCRIPTION , RELEASEDATE , DURATION , RATING_ID)
VALUES ('Тень', '30-ые годы XX века, город Нью-Йорк...', '1994-07-01', 108 , 3),
    ('Звёздные войны: Эпизод 4 – Новая надежда', 'Татуин. Планета-пустыня. Уже постаревший рыцарь Джедай ...', '1997-05-25', 121 , 2),
    ('Зеленая миля', 'Пол Эджкомб — начальник блока смертников в тюрьме «Холодная гора» ...', '1999-12-06', 189 , 4),
    ('Гадкий я', 'Гадкий снаружи, но добрый внутри Грю намерен, тем не менее, ...', '2010-06-27', 95 , 2);

INSERT INTO FILM_GENRES (FILM_ID , GENRE_ID)
VALUES (1, 6), (1, 7), (1, 12),
    (2, 6), (2, 7), (2, 8), (2, 9),
    (3, 2), (3, 8), (3, 10),
    (4, 1), (4, 3), (4, 7), (4, 9), (4, 10), (4, 11);

INSERT INTO USERS (EMAIL , LOGIN , NAME , BIRTHDAY)
VALUES ('Capitan@yandex.ru', 'Capitan', 'Capitan', '2001-01-01'),   -- 1
    ('Jack@yandex.ru', 'Jack', 'Jack', '2002-02-02'),       -- 2
    ('Sparrow@yandex.ru', 'Sparrow', 'Sparrow', '2003-03-03');   -- 3

INSERT INTO LIKES (FILM_ID, USER_ID)
VALUES (1, 1), (1, 3),
    (2, 1), (2, 2), (2, 3),
    (3, 2),
    (4, 1), (4, 2), (4, 3);

INSERT INTO STATUS (NAME)
VALUES ('Подтверждённая'),     -- 1
    ('Неподтверждённая');   -- 2

INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID, STATUS_ID)
VALUES (1, 2, 1), (1, 3, 1),
    (2, 1, 2), (2, 3, 1),
    (3, 1, 2), (3, 2, 1);