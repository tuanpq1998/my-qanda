CREATE database myaskfm;
USE myaskfm;

CREATE TABLE user (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(10) NOT NULL,
    `password` char(80) NOT NULL,
    `email` varchar(50) default null,
    `fullname` varchar(40) default null,
    `join_date` datetime,
    `location` varchar(20) default null,
    `workplace` varchar(20) default null,
    PRIMARY KEY (`id`)
);

CREATE TABLE question (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `question_content` nvarchar(400) NOT NULL,
    `answer_content` nvarchar(400) default NULL,
    `ask_date` DATETIME NOT NULL,
    `answer_date` DATETIME default NULL,
    `user_id_ask` INT default NULL,
    `user_id_answer` INT NOT NULL,
    `question_seen` boolean default FALSE,
    `answer_seen` boolean default FALSE,
    PRIMARY KEY (`id`),
    constraint `FK_user_id_ask` foreign key (`user_id_ask`) references `user`(`id`),
    constraint `FK_user_id_answer` foreign key (`user_id_answer`) references `user`(`id`)
)
