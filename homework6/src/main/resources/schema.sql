DROP TABLE IF EXISTS BooksGenres;
DROP TABLE IF EXISTS BooksAuthors;
DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS Authors;
DROP TABLE IF EXISTS Genres;

CREATE TABLE Authors
(
    Id          Int            auto_increment           NOT NULL,
    Name        VarChar(255)                            NOT NULL,
    PRIMARY KEY (Id)
);

CREATE TABLE Genres
(
    Id          Int            auto_increment           NOT NULL,
    Name        VarChar(255)                            NOT NULL,
    PRIMARY KEY (Id)
);

CREATE TABLE Books
(
    Id          Int            auto_increment           NOT NULL,
    Name        VarChar(255)                            NOT NULL,
    PRIMARY KEY (Id)
);

CREATE TABLE BooksAuthors
(
    Book_Id     Int                                     NOT NULL,
    Author_Id   Int                                     NOT NULL,
    PRIMARY KEY (Book_Id, Author_Id),
    FOREIGN KEY (Book_Id) REFERENCES Books(Id) ON DELETE CASCADE,
    FOREIGN KEY (Author_Id) REFERENCES Authors(Id)
);

CREATE TABLE BooksGenres
(
    Book_Id     Int                                     NOT NULL,
    Genre_Id    Int                                     NOT NULL,
    PRIMARY KEY (Book_Id, Genre_Id),
    FOREIGN KEY (Book_Id) REFERENCES Books(Id) ON DELETE CASCADE,
    FOREIGN KEY (Genre_Id) REFERENCES Genres(Id)
);