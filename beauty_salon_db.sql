-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema testdb1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema testdb1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `testdb1` DEFAULT CHARACTER SET utf8 ;
USE `testdb1` ;

-- -----------------------------------------------------
-- Table `testdb1`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`role` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`role` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `testdb1`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`user` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`user` (
  `id` INT NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone_num` VARCHAR(45) NOT NULL,
  `rate` FLOAT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `phone_num_UNIQUE` (`phone_num` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `testdb1`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `testdb1`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`service` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`service` (
  `id` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `description` MEDIUMTEXT NULL,
  `price` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `testdb1`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`status` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`status` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `testdb1`.`payment_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`payment_status` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`payment_status` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `testdb1`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`order` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`order` (
  `id` INT NOT NULL,
  `status_id` INT NOT NULL,
  `payment_status_id` INT NOT NULL,
  `time` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_appointment_state1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_appointment_payment_status1_idx` (`payment_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_appointment_state1`
    FOREIGN KEY (`status_id`)
    REFERENCES `testdb1`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_payment_status1`
    FOREIGN KEY (`payment_status_id`)
    REFERENCES `testdb1`.`payment_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `testdb1`.`user_to_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`user_to_service` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`user_to_service` (
  `user_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `service_id`),
  INDEX `fk_user_has_service_service1_idx` (`service_id` ASC) VISIBLE,
  INDEX `fk_user_has_service_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_service_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `testdb1`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_service_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `testdb1`.`service` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `testdb1`.`order_to_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`order_to_service` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`order_to_service` (
  `order_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  PRIMARY KEY (`order_id`, `service_id`),
  INDEX `fk_order_has_service_service1_idx` (`service_id` ASC) VISIBLE,
  INDEX `fk_order_has_service_order1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_has_service_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `testdb1`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_service_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `testdb1`.`service` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `testdb1`.`user_to_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `testdb1`.`user_to_order` ;

CREATE TABLE IF NOT EXISTS `testdb1`.`user_to_order` (
  `user_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `order_id`),
  INDEX `fk_user_has_order_order1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_user_has_order_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `testdb1`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_order_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `testdb1`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
