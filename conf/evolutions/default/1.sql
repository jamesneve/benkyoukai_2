# Posts schema

# --- !Ups

CREATE TABLE posts (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    date_published TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE IF EXISTS posts;
