
-- -----------------------------------------------------
-- Schema epam_airlines
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `epam_airlines`;
CREATE DATABASE IF NOT EXISTS `epam_airlines` DEFAULT CHARACTER SET utf8 ;
USE `epam_airlines` ;

-- -----------------------------------------------------
-- Table `epam_airlines`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_airlines`.`staff` (
  `staff_id` INT NOT NULL AUTO_INCREMENT,
  `staff_name` VARCHAR(45) NOT NULL,
  `staff_surname` VARCHAR(45) NOT NULL,
  `profession` TINYINT NOT NULL,
  `isBusyOrDeleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`staff_id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `epam_airlines`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_airlines`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_login` VARCHAR(30) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `user_surname` VARCHAR(45) NOT NULL,
  `user_email` VARCHAR(30) NOT NULL,
  `user_role` TINYINT NOT NULL,
  `isBusyOrDeleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`user_login` ASC))
  ENGINE = InnoDB;

insert into `epam_airlines`.`user` (user_login, user_password, user_name, user_surname, user_email, user_role) values
  ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Roman', 'Lymar','r.v.lymar@gmail.com',0);
insert into `epam_airlines`.`user` (user_login, user_password, user_name, user_surname, user_email, user_role) values
  ('disp', '0ffe1abd1a08215353c233d6e009613e95eec4253832a761af28ff37ac5a150c', 'Tanya', 'Ziagun','kramchaninovat@gmail.com',1);

-- -----------------------------------------------------
-- Table `epam_airlines`.`crew`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_airlines`.`crew` (
  `crew_id` INT NOT NULL AUTO_INCREMENT,
  `crew_name` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  `isBusyOrDeleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`crew_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `user_id`
  FOREIGN KEY (`user_id`)
  REFERENCES `epam_airlines`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `epam_airlines`.`flight`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_airlines`.`flight` (
  `flight_id` INT NOT NULL AUTO_INCREMENT,
  `flight_name` VARCHAR(45) NOT NULL,
  `flight_departureCity` VARCHAR(45) NOT NULL,
  `flight_destinationCity` VARCHAR(45) NOT NULL,
  `flight_date` DATE NOT NULL,
  `flight_time` TIME NOT NULL,
  `flight_statatus` TINYINT NOT NULL,
  `flight_crew_id` INT NULL,
  `isBusyOrDeleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`flight_id`),
  INDEX `flight_crew_id_idx` (`flight_crew_id` ASC),
  CONSTRAINT `flight_crew_id`
  FOREIGN KEY (`flight_crew_id`)
  REFERENCES `epam_airlines`.`crew` (`crew_id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `epam_airlines`.`crew_staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_airlines`.`crew_staff` (
  `crew_id` INT NOT NULL,
  `staff_id` INT NOT NULL,
  INDEX `crew_id_idx` (`crew_id` ASC),
  INDEX `staff_id_idx` (`staff_id` ASC),
  CONSTRAINT `crew_id`
  FOREIGN KEY (`crew_id`)
  REFERENCES `epam_airlines`.`crew` (`crew_id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `staff_id`
  FOREIGN KEY (`staff_id`)
  REFERENCES `epam_airlines`.`staff` (`staff_id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Test insert into staff
-- -----------------------------------------------------
INSERT INTO `epam_airlines`.`staff` (`staff_name`,`staff_surname`,`profession`) VALUES 
('Roman','Lymar',0),('Ivan','Ivanov',1),('Petr','Petrov',2),('Sidor','Sidorov',3),('Masha','Mashechkina',4),('Anna','Anechkina',4);
INSERT INTO `epam_airlines`.`staff` (`staff_name`,`staff_surname`,`profession`) VALUES 
('Luke','Skywalker',0),('Han','Solo',1),('Chewbacca','Chewbacca',2),('Obi-Wan','Kenobi',3),('Leia','Organa',4),('Padme','Amidala',4);

-- -----------------------------------------------------
-- Test insert into flight
-- -----------------------------------------------------
INSERT INTO `epam_airlines`.`flight` (`flight_name`,`flight_departureCity`,`flight_destinationCity`,`flight_date`,`flight_time`,`flight_statatus`) VALUES ('79339','Myanmar','Dominica','16.01.18','18:12:45',0),('88170','Norfolk Island','Brazil','17.09.18','00:07:24',0),('57670','Mali','Christmas Island','25.01.18','11:40:42',0),('42514','Congo','British Indian Ocean Territory','07.01.18','09:41:46',0),('40655','Zambia','Mauritania','21.07.18','09:48:05',0),('74467','Greenland','Nicaragua','22.10.17','15:44:59',0),('84799','El Salvador','Costa Rica','15.07.17','23:41:28',0),('39301','Marshall Islands','Tokelau','23.03.18','17:59:25',0),('82228','Lebanon','Romania','31.07.18','08:41:48',0),('71099','Cambodia','Wallis and Futuna','30.05.17','04:47:04',0);
INSERT INTO `epam_airlines`.`flight` (`flight_name`,`flight_departureCity`,`flight_destinationCity`,`flight_date`,`flight_time`,`flight_statatus`) VALUES ('11590','Pakistan','Congo, the Democratic Republic of the','16.05.17','19:49:06',0),('25395','Montenegro','Algeria','02.10.17','19:25:15',0),('57395','Tokelau','Swaziland','31.07.17','05:02:28',0),('28655','Palestine','Bulgaria','01.08.17','01:24:28',0),('05539','Sudan','Canada','08.07.17','08:37:45',0),('36014','Greenland','United Kingdom (Great Britain)','08.03.19','15:14:15',0),('06524','Cocos Islands','Swaziland','23.02.18','10:20:30',0),('46564','Guadeloupe','Bahrain','01.05.19','03:52:42',0),('56118','Ecuador','Denmark','23.05.18','07:09:05',0),('28387','Bulgaria','Nicaragua','12.06.17','20:28:53',0);

-- -----------------------------------------------------
-- Table `epam_airlines`.`severity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_airlines`.`severity` (
  `severity_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(30) NOT NULL,
  `isEnable` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`severity_id`));

  -- -----------------------------------------------------
  -- Test insert into severity
  -- -----------------------------------------------------

  INSERT INTO `epam_airlines`.`severity` (`description`,  `isEnable`) VALUES ('URGENT', 1);
  INSERT INTO `epam_airlines`.`severity` (`description`,  `isEnable`) VALUES ('NORMAL', 1);
  INSERT INTO `epam_airlines`.`severity` (`description`,  `isEnable`) VALUES ('CRITICAL', 1);
  INSERT INTO `epam_airlines`.`severity` (`description`,  `isEnable`) VALUES ('TRIVIAL', 1);

-- -----------------------------------------------------
-- Table `epam_airlines`.`messages`
-- -----------------------------------------------------

  CREATE TABLE IF NOT EXISTS `epam_airlines`.`messages`
   ( `message_id` INT NOT NULL AUTO_INCREMENT,
    `dispatcher_id` INT NOT NULL,
    `severity_id` INT NOT NULL,
    `message_text`  VARCHAR(500) NOT NULL,
    `issue_date`  DATE NOT NULL,
    `status`  VARCHAR(20),
     PRIMARY KEY (`message_id`),
     CONSTRAINT `dispatcher_id`
     FOREIGN KEY (`dispatcher_id`)
     REFERENCES `epam_airlines`.`user` (`user_id`),
     CONSTRAINT `severity_id`
     FOREIGN KEY (`severity_id`)
     REFERENCES `epam_airlines`.`severity` (`severity_id`))
