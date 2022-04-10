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
    Author_Id   Int                                     NOT NULL,
    Genre_Id    Int                                     NOT NULL,
    PRIMARY KEY (Id),
    FOREIGN KEY (Author_Id) REFERENCES Authors(Id),
    FOREIGN KEY (Genre_Id) REFERENCES Genres(Id)
);