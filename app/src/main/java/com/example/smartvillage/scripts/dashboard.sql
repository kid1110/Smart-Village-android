BEGIN;
CREATE DATABASE db_smart_agriculture;
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

CREATE TABLE `t_users`(
`uid` INT PRIMARY KEY  auto_increment,
`username` VARCHAR(12) UNIQUE KEY NOT  NULL,
`password` VARCHAR(120) NOT NULL,
`admin` INT DEFAULT 0 NOT NULL,
INDEX index_admin(admin)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4;

 CREATE TABLE `t_orders`(
 `oid` INT PRIMARY KEY auto_increment,
 `uid` INT NOT NULL,
 `adress` VARCHAR(50),
 `type_id` INT NOT NULL,
  `order_date` VARCHAR(10),
  INDEX index_uid(uid)
 )ENGINE = InnoDB
   DEFAULT CHARSET = utf8mb4;

CREATE TABLE `t_type`(
    `type_id` int primary key auto_increment,
    `type` varchar(120) NOT NULL
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4;

INSERT INTO t_type (`type`)
VALUE("上门核酸预约");
 COMMIT;