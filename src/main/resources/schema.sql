CREATE TABLE IF NOT EXISTS users(
                      user_id INTEGER DEFAULT 1  AUTO_INCREMENT,
                      user_name VARCHAR(100),
                      user_login VARCHAR(100) NOT NULL,
                      user_email VARCHAR(100) NOT NULL ,
                      user_birthday DATE,
                      CONSTRAINT user_id PRIMARY KEY (user_id)
);
CREATE UNIQUE INDEx IF NOT EXISTS USERS_LOGIN_UNQ ON  USERS(user_login);

CREATE UNIQUE INDEX IF NOT EXISTS USERS_EMAIL_UNQ ON USERS(user_email);

CREATE TABLE IF NOT EXISTS mpa(
                                  mpa_id INT DEFAULT 1  NOT NULL PRIMARY KEY  AUTO_INCREMENT,
                                  mpa ENUM ('G','PG','PG-13','R','NC-17') UNIQUE
);

CREATE TABLE  IF NOT EXISTS films(
                      film_id INTEGER DEFAULT 1  AUTO_INCREMENT,
                      film_name VARCHAR(100),
                      film_description VARCHAR(100),
                      film_release_date DATE,
                      film_duration INTEGER,
                      film_mpa INTEGER,
                      film_rate INTEGER,
                    CONSTRAINT film_id PRIMARY KEY(film_id),
                    FOREIGN KEY(film_mpa) REFERENCES mpa(mpa_id)
);
CREATE TABLE IF NOT EXISTS genres(
                                  genre_id INT DEFAULT 1 NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                  genre ENUM ('Комедия','Драма','Мультфильм','Триллер','Документальный','Боевик') UNIQUE
);
CREATE TABLE IF NOT EXISTS genres_of_film(
    genre_id INT,
    film_id INT,
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id),
    FOREIGN KEY (film_id) REFERENCES  films(film_id),
    PRIMARY KEY (genre_id, film_id)
);
CREATE UNIQUE INDEX IF NOT EXISTS film_name ON fILMS(film_name);

CREATE TABLE  IF NOT EXISTS user_friends(
    user_id INTEGER,
    friend_id INTEGER,
    status BOOLEAN,
     FOREIGN KEY (user_id) REFERENCES users(user_id),
     FOREIGN KEY (friend_id) REFERENCES users(user_id),
    PRIMARY KEY (user_id, friend_id));

CREATE TABLE IF NOT EXISTS  film_who_liked(
      film_id INTEGER NOT NULL ,
      user_id INTEGER NOT NULL,
      FOREIGN KEY (film_id) REFERENCES  films(film_id),
      FOREIGN KEY (user_id) REFERENCES  users(user_id)
);

