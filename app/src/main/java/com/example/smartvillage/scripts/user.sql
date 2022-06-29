BEGIN;
USE db_smart_agriculture;

CREATE TABLE `t_users`(
`uid` INT PRIMARY KEY  auto_increment,
`username` VARCHAR(12) UNIQUE KEY NOT  NULL,
`password` VARCHAR(120) NOT NULL,
`admin` INT DEFAULT 0 NOT NULL,
INDEX index_admin(admin)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4;

 COMMIT;