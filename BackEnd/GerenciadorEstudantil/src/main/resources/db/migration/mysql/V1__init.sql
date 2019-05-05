CREATE TABLE `gerenciador_estudantil`.`Aluno` (
	`id` INT NOT NULL AUTO_INCREMENT , 
	`email` VARCHAR(200) NOT NULL ,
	`nome` VARCHAR(255) NOT NULL , 
	`senha` VARCHAR(255) NOT NULL , 
	PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;