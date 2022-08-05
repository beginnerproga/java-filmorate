CREATE TABLE IF NOT EXISTS users(
                      user_id INTEGER AUTO_INCREMENT,
                      user_name VARCHAR(100),
                      user_login VARCHAR(100) NOT NULL,
                      user_email VARCHAR(100) NOT NULL ,
                      user_birthday DATE,
                      CONSTRAINT user_id PRIMARY KEY (user_id)
);
CREATE UNIQUE INDEx IF NOT EXISTS USERS_LOGIN_UNQ ON  USERS(user_login);

CREATE UNIQUE INDEX IF NOT EXISTS USERS_EMAIL_UNQ ON USERS(user_email);

CREATE TABLE  IF NOT EXISTS films(
                      film_id INTEGER AUTO_INCREMENT,
                      film_name VARCHAR(100),
                      film_description VARCHAR(100),
                      film_release_date DATE,
                      film_duration INTEGER,
                      film_genre ENUM ('COMEDY','DRAMA','CARTOON','THRILLER','DOCUMENTARY','ACTION'),
                      film_mpa ENUM('G','PG','PG_13','R','NC_17'),
                      CONSTRAINT film_id PRIMARY KEY(film_id)
);
CREATE UNIQUE INDEX IF NOT EXISTS film_name ON fILMS(film_name);

CREATE TABLE  IF NOT EXISTS user_friends(
    from_whom_user_id INTEGER,
    to_whom_user_id INTEGER,
     FOREIGN KEY (from_whom_user_id) REFERENCES users(user_id),
     FOREIGN KEY (to_whom_user_id) REFERENCES users(user_id)
);
CREATE TABLE IF NOT EXISTS  film_who_liked(
      film_id INTEGER NOT NULL ,
      user_id INTEGER NOT NULL,
      FOREIGN KEY (film_id) REFERENCES  films(film_id),
      FOREIGN KEY (user_id) REFERENCES  users(user_id)
);

