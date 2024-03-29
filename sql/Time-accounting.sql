-- MySQL Script generated by MySQL Workbench
-- Mon Sep 20 23:00:35 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Time-accounting
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Time-accounting
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Time-accounting` DEFAULT CHARACTER SET utf8 ;
USE `Time-accounting` ;

-- -----------------------------------------------------
-- Table `Time-accounting`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Time-accounting`.`user_roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_id_UNIQUE` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Time-accounting`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Time-accounting`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_login` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(255) NOT NULL,
  `salt` VARCHAR(100) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `user_login_UNIQUE` (`user_login` ASC) VISIBLE,
  INDEX `role_id_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `Time-accounting`.`user_roles` (`role_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Time-accounting`.`activity_categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Time-accounting`.`activity_categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `category_id_UNIQUE` (`category_id` ASC) VISIBLE,
  UNIQUE INDEX `category_name_UNIQUE` (`category_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Time-accounting`.`activities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Time-accounting`.`activities` (
  `activity_id` INT NOT NULL AUTO_INCREMENT,
  `activity_name` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`activity_id`),
  UNIQUE INDEX `activity_id_UNIQUE` (`activity_id` ASC) VISIBLE,
  UNIQUE INDEX `activity_name_UNIQUE` (`activity_name` ASC) VISIBLE,
  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `Time-accounting`.`activity_categories` (`category_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Time-accounting`.`users_activities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Time-accounting`.`users_activities` (
  `user_id` INT NOT NULL,
  `activity_id` INT NOT NULL,
  `approved` ENUM('Yes', 'No') NOT NULL,
  `start_datetime` DATETIME NULL,
  `finish_time` TIME NULL,
  PRIMARY KEY (`user_id`, `activity_id`),
  INDEX `activity_id_idx` (`activity_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `Time-accounting`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `activity_id`
    FOREIGN KEY (`activity_id`)
    REFERENCES `Time-accounting`.`activities` (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO user_roles VALUES (1, 'admin'), (2, 'user');
INSERT INTO users VALUES (0, 'admin', 'uWepWDRfjj5DQWjuNBuVww2koCMvd1rruog4ySmzlDgjlKcJY/Erwp93cgVsOXp6J2hx/ROCaYdZQJ635XRYAg==', 'lKt6hYQ5wDf1fn1dTtqNZw==', 1);

DELIMITER //
CREATE TRIGGER check_user_role BEFORE INSERT ON users_activities FOR EACH ROW
BEGIN
	DECLARE user_role_id INT DEFAULT 0;
	SELECT role_id INTO user_role_id FROM users 
	WHERE user_id = NEW.user_id;
	IF user_role_id = 1 THEN 
		SET NEW.approved = 'Yes';
	END IF;
END //
DELIMITER ;