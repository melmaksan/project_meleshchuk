-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema beauty_salon_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema beauty_salon_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `beauty_salon_db` DEFAULT CHARACTER SET utf8 ;
USE `beauty_salon_db` ;

-- -----------------------------------------------------
-- Table `beauty_salon_db`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`role` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`role` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`user` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone_num` VARCHAR(45) NOT NULL,
  `rate` FLOAT NULL,
  `role_id` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `beauty_salon_db`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`service` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`service` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` MEDIUMTEXT NULL DEFAULT NULL,
  `price` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`status` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`status` (
  `id` TINYINT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`payment_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`payment_status` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`payment_status` (
  `id` TINYINT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`orders` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`orders` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `time` DATETIME NOT NULL,
  `status_id` TINYINT UNSIGNED NOT NULL,
  `payment_status_id` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_orders_payment_status1_idx` (`payment_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `beauty_salon_db`.`status` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_payment_status1`
    FOREIGN KEY (`payment_status_id`)
    REFERENCES `beauty_salon_db`.`payment_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`user_to_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`user_to_service` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`user_to_service` (
  `user_id` BIGINT UNSIGNED NOT NULL,
  `service_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `service_id`),
  INDEX `fk_user_has_service_service1_idx` (`service_id` ASC) VISIBLE,
  INDEX `fk_user_has_service_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_service_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `beauty_salon_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_service_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `beauty_salon_db`.`service` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`orders_to_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`orders_to_service` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`orders_to_service` (
  `orders_id` BIGINT UNSIGNED NOT NULL,
  `service_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`orders_id`, `service_id`),
  INDEX `fk_orders_has_service_service1_idx` (`service_id` ASC) VISIBLE,
  INDEX `fk_orders_has_service_orders1_idx` (`orders_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_has_service_orders1`
    FOREIGN KEY (`orders_id`)
    REFERENCES `beauty_salon_db`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_has_service_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `beauty_salon_db`.`service` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`user_to_orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`user_to_orders` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`user_to_orders` (
  `user_id` BIGINT UNSIGNED NOT NULL,
  `orders_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `orders_id`),
  INDEX `fk_user_has_orders_orders1_idx` (`orders_id` ASC) VISIBLE,
  INDEX `fk_user_has_orders_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_orders_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `beauty_salon_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_orders_orders1`
    FOREIGN KEY (`orders_id`)
    REFERENCES `beauty_salon_db`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`respond`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`respond` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`respond` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `respond` MEDIUMTEXT NOT NULL,
  `user_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_respond_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_respond_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `beauty_salon_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
