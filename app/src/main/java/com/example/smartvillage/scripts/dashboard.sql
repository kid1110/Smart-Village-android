BEGIN;
USE db_smart_agriculture;

CREATE TABLE `t_dashboard`(
`did` INT PRIMARY KEY auto_increment,
`title` VARCHAR(200) DEFAULT "标题",
`author` VARCHAR(150) DEFAULT "作者",
`content` LONGTEXT,
`image` VARCHAR(200),
`timestamp` VARCHAR(200)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4;

 COMMIT;