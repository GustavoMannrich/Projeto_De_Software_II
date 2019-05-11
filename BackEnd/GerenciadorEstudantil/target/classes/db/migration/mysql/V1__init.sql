CREATE TABLE `aluno` (
	`id` INT NOT NULL AUTO_INCREMENT , 
	`email` VARCHAR(200) NOT NULL ,
	`nome` VARCHAR(255) NOT NULL , 
	`senha` VARCHAR(255) NOT NULL , 
	PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `evento` (
	`id` INT NOT NULL AUTO_INCREMENT , 
	`titulo` VARCHAR(30) NOT NULL ,
	`descricao` VARCHAR(255) NOT NULL , 
	`data` DATETIME  NOT NULL ,
	`aluno_id` INT(20) DEFAULT NULL, 
	PRIMARY KEY (`id`),
	KEY `FK46i4k5vl8wah7feutye9kbpi4` (`aluno_id`),
	CONSTRAINT `FK46i4k5vl8wah7feutye9kbpi4` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;