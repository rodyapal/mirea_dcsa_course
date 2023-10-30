CREATE DATABASE IF NOT EXISTS appDb;
CREATE USER IF NOT EXISTS 'user'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON appDb.* TO 'user'@'%';
FLUSH PRIVILEGES;
USE appDb;

CREATE TABLE IF NOT EXISTS users
(
    id         INT(11)                         NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(20) CHARACTER SET ascii NOT NULL,
    `password` VARCHAR(45) CHARACTER SET ascii NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;

CREATE TABLE IF NOT EXISTS valuables
(
    id            INT(10)      NOT NULL AUTO_INCREMENT,
    title         VARCHAR(32)  NOT NULL,
    `description` VARCHAR(256) NOT NULL,
    cost          INT(6)       NOT NULL,
    PRIMARY KEY (id)
);

-- htpasswd -bns admin admin
INSERT INTO users (`name`, `password`)
SELECT *
FROM (SELECT 'admin', '{SHA}0DPiKuNIrrVmD8IUCuw1hQxNqZc=') AS temp
WHERE NOT EXISTS(
        SELECT `name` FROM users WHERE `name` = 'admin' AND `password` = '{SHA}0DPiKuNIrrVmD8IUCuw1hQxNqZc='
    )
LIMIT 1;

INSERT INTO valuables (title, `description`, cost)
SELECT *
FROM (SELECT 'Motherboard',
             'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
             1000) AS temp
WHERE NOT EXISTS(
        SELECT title
        FROM valuables
        WHERE title = 'Motherboard'
          AND `description` =
              'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
          AND cost = 1000
    )
LIMIT 1;

INSERT INTO valuables (title, `description`, cost)
SELECT *
FROM (SELECT 'Power supply',
             'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur',
             500) AS temp
WHERE NOT EXISTS(
        SELECT title
        FROM valuables
        WHERE title = 'Power supply'
          AND `description` =
              'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur'
          AND cost = 500
    )
LIMIT 1;