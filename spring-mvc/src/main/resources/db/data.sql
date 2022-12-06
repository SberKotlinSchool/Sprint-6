CREATE TABLE USERS (
                      username          VARCHAR2(255) PRIMARY KEY,
                      password VARCHAR(255) NOT NULL,
                      enabled   boolean NOT NULL);

CREATE TABLE AUTHORITIES  (
                       username          VARCHAR2(255),
                       AUTHORITY  VARCHAR(255) NOT NULL,);