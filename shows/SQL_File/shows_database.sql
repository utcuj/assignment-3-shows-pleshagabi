-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';



-- -----------------------------------------------------
-- Schema showdb2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `showdb2` DEFAULT CHARACTER SET utf8 ;
USE `showdb2` ;

-- -----------------------------------------------------
-- Table `showdb2`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`user` (
  `idUser` INT(11) NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NULL DEFAULT NULL,
  `lastname` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(5) NULL DEFAULT NULL,
  `userType` ENUM('regular', 'premium', 'admin') NULL DEFAULT NULL,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `showdb2`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`notification` (
  `idnotification` INT NOT NULL AUTO_INCREMENT,
  `showName` VARCHAR(45) NULL,
  `user_idUser` INT(11) NOT NULL,
  PRIMARY KEY (`idnotification`),
  INDEX `fk_notification_user_idx` (`user_idUser` ASC),
  CONSTRAINT `fk_notification_user`
    FOREIGN KEY (`user_idUser`)
    REFERENCES `showdb2`.`user` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `showdb2`.`show_tb`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`show_tb` (
  `idShow` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Description` VARCHAR(300) NULL DEFAULT NULL,
  `ReleaseDate` DATE NULL DEFAULT NULL,
  `IMDBRating` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`idShow`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `showdb2`.`interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`interest` (
  `idinterest` INT NOT NULL,
  `user_idUser` INT(11) NOT NULL,
  `show_tb_idShow` INT(11) NOT NULL,
  PRIMARY KEY (`idinterest`),
  INDEX `fk_interest_user1_idx` (`user_idUser` ASC),
  INDEX `fk_interest_show_tb1_idx` (`show_tb_idShow` ASC),
  CONSTRAINT `fk_interest_user1`
    FOREIGN KEY (`user_idUser`)
    REFERENCES `showdb2`.`user` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interest_show_tb1`
    FOREIGN KEY (`show_tb_idShow`)
    REFERENCES `showdb2`.`show_tb` (`idShow`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `showdb2`.`show_tb`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`show_tb` (
  `idShow` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Description` VARCHAR(300) NULL DEFAULT NULL,
  `ReleaseDate` DATE NULL DEFAULT NULL,
  `IMDBRating` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`idShow`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `showdb2`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`user` (
  `idUser` INT(11) NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NULL DEFAULT NULL,
  `lastname` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(5) NULL DEFAULT NULL,
  `userType` ENUM('regular', 'premium', 'admin') NULL DEFAULT NULL,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `showdb2` ;

-- -----------------------------------------------------
-- Table `showdb2`.`actor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`actor` (
  `idActor` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Age` INT(5) NULL DEFAULT NULL,
  `Role` VARCHAR(45) NULL DEFAULT NULL,
  `Oscars` INT(5) NULL DEFAULT NULL,
  `show_idShow` INT(11) NOT NULL,
  PRIMARY KEY (`idActor`),
  INDEX `fk_actor_show1_idx` (`show_idShow` ASC),
  CONSTRAINT `fk_actor_show1`
    FOREIGN KEY (`show_idShow`)
    REFERENCES `showdb2`.`show_tb` (`idShow`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `showdb2`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`comment` (
  `idComment` INT(11) NOT NULL AUTO_INCREMENT,
  `Comment` VARCHAR(300) NULL DEFAULT NULL,
  `user_idUser` INT(11) NOT NULL,
  `show_idShow` INT(11) NOT NULL,
  PRIMARY KEY (`idComment`),
  INDEX `fk_comment_user1_idx` (`user_idUser` ASC),
  INDEX `fk_comment_show1_idx` (`show_idShow` ASC),
  CONSTRAINT `fk_comment_show1`
    FOREIGN KEY (`show_idShow`)
    REFERENCES `showdb2`.`show_tb` (`idShow`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_idUser`)
    REFERENCES `showdb2`.`user` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `showdb2`.`history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`history` (
  `idHistory` INT(11) NOT NULL AUTO_INCREMENT,
  `user_idUser` INT(11) NOT NULL,
  `show_tb_idShow` INT(11) NOT NULL,
  PRIMARY KEY (`idHistory`),
  INDEX `fk_history_user1_idx` (`user_idUser` ASC),
  INDEX `fk_history_show_tb1_idx` (`show_tb_idShow` ASC),
  CONSTRAINT `fk_history_show_tb1`
    FOREIGN KEY (`show_tb_idShow`)
    REFERENCES `showdb2`.`show_tb` (`idShow`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_history_user1`
    FOREIGN KEY (`user_idUser`)
    REFERENCES `showdb2`.`user` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `showdb2`.`rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `showdb2`.`rating` (
  `idrating` INT(11) NOT NULL AUTO_INCREMENT,
  `rating` INT(11) NULL DEFAULT NULL,
  `show_tb_idShow` INT(11) NOT NULL,
  `user_idUser` INT(11) NOT NULL,
  PRIMARY KEY (`idrating`),
  INDEX `fk_rating_show_tb_idx` (`show_tb_idShow` ASC),
  INDEX `fk_rating_user1_idx` (`user_idUser` ASC),
  CONSTRAINT `fk_rating_show_tb`
    FOREIGN KEY (`show_tb_idShow`)
    REFERENCES `showdb2`.`show_tb` (`idShow`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rating_user1`
    FOREIGN KEY (`user_idUser`)
    REFERENCES `showdb2`.`user` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


ALTER TABLE `showdb2`.`notification` 
ADD COLUMN `fromUser` VARCHAR(45) NULL AFTER `user_idUser`;

ALTER TABLE `showdb2`.`interest` 
DROP FOREIGN KEY `fk_interest_show_tb1`;
ALTER TABLE `showdb2`.`interest` 
DROP COLUMN `show_tb_idShow`,
ADD COLUMN `showName` VARCHAR(45) NULL AFTER `user_idUser`,
DROP INDEX `fk_interest_show_tb1_idx` ;



INSERT INTO `showdb2`.`show_tb` (`idShow`, `Name`, `Description`, `ReleaseDate`, `IMDBRating`) VALUES ('1', 'Steaua-CFR', 'liga 1 romania', '2018-07-23', '5');
INSERT INTO `showdb2`.`show_tb` (`idShow`, `Name`, `Description`, `ReleaseDate`, `IMDBRating`) VALUES ('2', 'insula fericirii', 'fericire, insula bla bla,  sinistrati', '2018-05-02', '7');
INSERT INTO `showdb2`.`show_tb` (`idShow`, `Name`, `Description`, `ReleaseDate`, `IMDBRating`) VALUES ('3', 'iubirii', 'bagaboante fain misto tare cool', '2018-05-02', '4');
INSERT INTO `showdb2`.`show_tb` (`idShow`, `Name`, `Description`, `ReleaseDate`, `IMDBRating`) VALUES ('4', 'Lucifer', 'crazy, satan, god best movie', '2018-05-02', '10');
INSERT INTO `showdb2`.`show_tb` (`idShow`, `Name`, `Description`, `ReleaseDate`, `IMDBRating`) VALUES ('5', 'Californication', 'scriitor, babardeste tot ce pote fain', '2017-12-22', '9.5');
INSERT INTO `showdb2`.`show_tb` (`idShow`, `Name`, `Description`, `ReleaseDate`, `IMDBRating`) VALUES ('6', 'Top chef', 'cooking show, antena 1', '2016-10-10', '8.2');
INSERT INTO `showdb2`.`show_tb` (`idShow`, `Name`, `Description`, `ReleaseDate`, `IMDBRating`) VALUES ('7', 'U cluj - CFR', 'meci din cupa romanieii', '2018-05-19', '5');


INSERT INTO `showdb2`.`user` (`idUser`, `firstname`, `lastname`, `age`, `userType`,`userName`,`password`,`email`) VALUES ('1', 'Plesa', 'Gabriel', '22', 'regular','plesha','plesha','plesha.gabi@gmail.com');
INSERT INTO `showdb2`.`user` (`idUser`, `firstname`, `lastname`, `age`, `userType`,`userName`,`password`,`email`) VALUES ('2', 'Roxana', 'Petro', '23', 'premium','roxana','petro','roxana.petro@gmail.com');
INSERT INTO `showdb2`.`user` (`idUser`, `firstname`, `lastname`, `age`, `userType`,`userName`,`password`,`email`) VALUES ('3', 'Antonia', 'Madalina', '21', 'admin','antonia','antonia','antonia.mada@gmail.com');
INSERT INTO `showdb2`.`user` (`idUser`, `firstname`, `lastname`, `age`, `userType`,`userName`,`password`,`email`) VALUES ('4', 'Ilas', 'Alexandra', '20', 'regular','ilas','ilas','ilas.ale@gmail.com');
INSERT INTO `showdb2`.`user` (`idUser`, `firstname`, `lastname`, `age`, `userType`,`userName`,`password`,`email`) VALUES ('5', 'Pocol', 'Alexandru', '22', 'premium','pocol','pocol','pocol.alex@gmail.com');


INSERT INTO `showdb2`.`history` (`idHistory`, `user_idUser`, `show_tb_idShow`) VALUES ('1', '1', '1');
INSERT INTO `showdb2`.`history` (`idHistory`, `user_idUser`, `show_tb_idShow`) VALUES ('2', '2', '1');
INSERT INTO `showdb2`.`history` (`idHistory`, `user_idUser`, `show_tb_idShow`) VALUES ('3', '3', '2');
INSERT INTO `showdb2`.`history` (`idHistory`, `user_idUser`, `show_tb_idShow`) VALUES ('4', '4', '2');
INSERT INTO `showdb2`.`history` (`idHistory`, `user_idUser`, `show_tb_idShow`) VALUES ('5', '5', '2');


INSERT INTO `showdb2`.`comment` (`idComment`, `Comment`, `user_idUser`, `show_idShow`) VALUES ('1', 'show fain tare misto blabla', '1', '1');
INSERT INTO `showdb2`.`comment` (`idComment`, `Comment`, `user_idUser`, `show_idShow`) VALUES ('2', 'super tare imi place', '2', '2');
INSERT INTO `showdb2`.`comment` (`idComment`, `Comment`, `user_idUser`, `show_idShow`) VALUES ('3', 'misto, recomand onta 10 pluss', '3', '1');
INSERT INTO `showdb2`.`comment` (`idComment`, `Comment`, `user_idUser`, `show_idShow`) VALUES ('4', 'cel mai fain serial de genul', '4', '3');
INSERT INTO `showdb2`.`comment` (`idComment`, `Comment`, `user_idUser`, `show_idShow`) VALUES ('5', 'mi-a placut meciul a fost frumos', '5', '7');
