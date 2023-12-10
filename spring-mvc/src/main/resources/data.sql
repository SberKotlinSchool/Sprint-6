INSERT INTO users (name, passwd, roles, cred_expired, enabled, expired, locked)
VALUES ('admin', '{bcrypt}$2a$14$i/T/R5pyxUbJP2eiZSYBYOobtj2VxcnbgPax40QkZXJQJsB/DdGau', ['ADMIN'], false, true, false, false),
       ('api', '{bcrypt}$2a$14$i/T/R5pyxUbJP2eiZSYBYOobtj2VxcnbgPax40QkZXJQJsB/DdGau', ['API'], false, true, false, false);