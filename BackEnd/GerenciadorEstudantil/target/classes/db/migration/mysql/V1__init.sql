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
	CONSTRAINT `FK9ufw9h2374hfuwh983fh8hf93` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `curso` (
	`id` INT NOT NULL AUTO_INCREMENT , 
	`nome` VARCHAR(30) NOT NULL ,
	`aluno_id` INT(20) DEFAULT NULL, 
	PRIMARY KEY (`id`),
	KEY `FK87f8ash3j4r7f8dsfyhe8rwh8` (`aluno_id`),
	CONSTRAINT `FK123uhesfhr3fgs78fdgf42i3i` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `disciplina` (
	`id` INT NOT NULL AUTO_INCREMENT , 
	`nome` VARCHAR(30) NOT NULL ,
	`aluno_id` INT(20) DEFAULT NULL, 
	`curso_id` INT(20) DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY `FK46gdhjasahd1372412g4i3h42` (`aluno_id`),
	CONSTRAINT `FK83hfsjkf3f4fsfidufsudf475` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`),
	CONSTRAINT `FK312bdbfosd8fhf4tn4t4fhf89` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `files` (
	`id` INT NOT NULL AUTO_INCREMENT , 
	`file_Name` VARCHAR(200) NOT NULL ,
	`file_Type` VARCHAR(200) NOT NULL , 
	`data` LONGBLOB NOT NULL , 
	PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;