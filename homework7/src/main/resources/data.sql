insert into Authors (Name) values ('Дэн Браун');
insert into Authors (Name) values ('Гарри Гаррисон');
insert into Authors (Name) values ('Сергей Лукьяненко');
insert into Authors (Name) values ('Владимир Васильев');

insert into Genres (Name) values ('Детектив');
insert into Genres (Name) values ('Фантастика');
insert into Genres (Name) values ('Роман-Антиутопия');
insert into Genres (Name) values ('Фэнтези');

insert into Books(Name) values('Ангелы и демоны');
insert into BooksAuthors(Book_Id, Author_Id) values (1, 1);
insert into BooksGenres(Book_Id, Genre_Id) values (1, 1);
insert into BooksComments(Book_Id, CreateDate, Note) SELECT 1, Cast('2020-08-07' AS Date), 'Книга просто топчик!';
insert into BooksComments(Book_Id, CreateDate, Note) SELECT 1, Cast('2020-08-07' AS Date), 'Прочитал десять раз - рекомендую!';

insert into Books(Name) values('Код да Винчи');
insert into BooksAuthors(Book_Id, Author_Id) values (2, 1);
insert into BooksGenres(Book_Id, Genre_Id) values (2, 1);
insert into BooksComments(Book_Id, CreateDate, Note) SELECT 2, Cast('2020-08-07' AS Date), 'Продолжение Ангелов и Демонов';
insert into BooksComments(Book_Id, CreateDate, Note) SELECT 2, Cast('2020-08-07' AS Date), 'Ангелы и демоны лучше!';

insert into Books(Name) values('Крыса из нержавеющей стали');
insert into BooksAuthors(Book_Id, Author_Id) values (3, 2);
insert into BooksGenres(Book_Id, Genre_Id) values (3, 2);

insert into Books(Name) values('Рыцари сорока островов');
insert into BooksAuthors(Book_Id, Author_Id) values (4, 3);
insert into BooksGenres(Book_Id, Genre_Id) values (4, 2);

insert into Books(Name) values('Дневной дозор');
insert into BooksAuthors(Book_Id, Author_Id) values (5, 3);
insert into BooksAuthors(Book_Id, Author_Id) values (5, 4);
insert into BooksGenres(Book_Id, Genre_Id) values (5, 2);
insert into BooksGenres(Book_Id, Genre_Id) values (5, 4);



