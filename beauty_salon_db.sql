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
  `password` VARCHAR(65) NOT NULL,
  `phone_num` VARCHAR(45) NOT NULL,
  `rate` DOUBLE(5,1) NULL,
  `role_id` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `beauty_salon_db`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`service` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`service` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `price` DECIMAL(9,2) NOT NULL,
  `image` VARCHAR(255) NOT NULL,
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
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_payment_status1`
    FOREIGN KEY (`payment_status_id`)
    REFERENCES `beauty_salon_db`.`payment_status` (`id`)
    ON DELETE NO ACTION
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
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_service_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `beauty_salon_db`.`service` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
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
  CONSTRAINT `fk_orders_has_service_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `beauty_salon_db`.`service` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`user_to_orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`user_to_orders` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`user_to_orders` (
  `user_id` BIGINT UNSIGNED NOT NULL,
  `orders_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `orders_id`),
  INDEX `fk_user_has_orders_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_orders_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `beauty_salon_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`respond`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`respond` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`respond` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `datetime` DATETIME NOT NULL,
  `mark` TINYINT NOT NULL,
  `respond` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `beauty_salon_db`.`user_to_responds`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `beauty_salon_db`.`user_to_responds` ;

CREATE TABLE IF NOT EXISTS `beauty_salon_db`.`user_to_responds` (
  `respond_id` BIGINT UNSIGNED NOT NULL,
  `user_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`respond_id`, `user_id`),
  INDEX `fk_respond_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_respond_has_user_respond1_idx` (`respond_id` ASC) VISIBLE,
  CONSTRAINT `fk_respond_has_user_respond1`
    FOREIGN KEY (`respond_id`)
    REFERENCES `beauty_salon_db`.`respond` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_respond_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `beauty_salon_db`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

INSERT into role (id, name) VALUES(1, 'USER');
INSERT into role (id, name) VALUES(2, 'ADMIN');
INSERT into role (id, name) VALUES(3, 'SPECIALIST');
INSERT into status (id, name) VALUES(1, 'BOOKED');
INSERT into status (id, name) VALUES(2, 'DONE');
INSERT into status (id, name) VALUES(3, 'CANCELED');
INSERT into payment_status (id, name) VALUES(1, 'UNPAID');
INSERT into payment_status (id, name) VALUES(2, 'PAID');
INSERT into user (id, first_name, last_name, login, password, phone_num, role_id) VALUES(1, 'Max', 'Pain', 'admin@mma.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '+380449379992', 2);
INSERT into user (id, first_name, last_name, login, password, phone_num, rate, role_id) VALUES(2, 'Alex', 'Jones', 'alex.spec@mma.com', '92984524c562dcc97f4939ba3c903d3e7aa880e3cf1060557de8175b2dab53e8', '+380449379992', 4.4, 3);
INSERT into user (id, first_name, last_name, login, password, phone_num, rate, role_id) VALUES(3, 'Yulia', 'Gerasimova', 'yulia.spec@mma.com', '92984524c562dcc97f4939ba3c903d3e7aa880e3cf1060557de8175b2dab53e8', '+380449379992', 4.2, 3);
INSERT into user (id, first_name, last_name, login, password, phone_num, rate, role_id) VALUES(4, 'Anna', 'Yakovenko', 'anna.spec@mma.com', '92984524c562dcc97f4939ba3c903d3e7aa880e3cf1060557de8175b2dab53e8', '+380449379992', 4.0, 3);
INSERT into user (id, first_name, last_name, login, password, phone_num, rate, role_id) VALUES(5, 'Iryna', 'Tydnyuk', 'iryna.spec@mma.com', '92984524c562dcc97f4939ba3c903d3e7aa880e3cf1060557de8175b2dab53e8', '+380449379992', 5.0, 3);
INSERT into user (id, first_name, last_name, login, password, phone_num, rate, role_id) VALUES(6, 'Regina', 'Romanova', 'regina.spec@mma.com', '92984524c562dcc97f4939ba3c903d3e7aa880e3cf1060557de8175b2dab53e8', '+380449379992', 4.8, 3);
INSERT into user (id, first_name, last_name, login, password, phone_num, rate, role_id) VALUES(7, 'Nataliya', 'Novitska', 'nataliya.spec@mma.com', '92984524c562dcc97f4939ba3c903d3e7aa880e3cf1060557de8175b2dab53e8', '+380449379992', 4.5, 3);
INSERT into user (first_name, last_name, login, password, phone_num, role_id) VALUES('Sergiy', 'Sternenko', 'sergiy.user@mma.com', 'e606e38b0d8c19b24cf0ee3808183162ea7cd63ff7912dbb22b5e803286b4446', '+380445566789', 1);
INSERT into user (first_name, last_name, login, password, phone_num, role_id) VALUES('Kolya', 'Petroshchuk', 'kolya.user@mma.com', 'e606e38b0d8c19b24cf0ee3808183162ea7cd63ff7912dbb22b5e803286b4446', '+380445566789', 1);
INSERT into user (first_name, last_name, login, password, phone_num, role_id) VALUES('Lena', 'Holowach', 'lena.user@mma.com', 'e606e38b0d8c19b24cf0ee3808183162ea7cd63ff7912dbb22b5e803286b4446', '+380445566789', 1);
INSERT into service (title, description, price, image) VALUES('Crew Cut', 'Man Military Haircut', 110.00, '/images/service_img/man_hc_1.jpg');
INSERT into service (title, description, price, image) VALUES('Easy Waves', 'Woman Classic Haircut', 8770.00, '/images/service_img/woman_hc_1.jpg');
INSERT into service (title, description, price, image) VALUES('Neat Side Fade', 'Kids Haircut', 190.00, '/images/service_img/kid_hc_1.jpg');
INSERT into service (title, description, price, image) VALUES('Crew Cut', 'Man Haircut', 670.00, '/images/service_img/man_hc_1.jpg');
INSERT into service (title, description, price, image) VALUES('Easy Waves', 'Woman Haircut', 540.00, '/images/service_img/woman_hc_1.jpg');
INSERT into service (title, description, price, image) VALUES('Neat Side Fade', 'Kids Haircut', 110.00, '/images/service_img/kid_hc_1.jpg');
INSERT into service (title, description, price, image) VALUES('Crew Cut', 'Man Military Haircut', 130.00, '/images/service_img/man_hc_1.jpg');
INSERT into service (title, description, price, image) VALUES('Easy Waves', 'Woman Haircut', 970.00, '/images/service_img/woman_hc_1.jpg');
INSERT into service (title, description, price, image) VALUES('Neat Side Fade', 'Kids Haircut', 390.00, '/images/service_img/kid_hc_1.jpg');
INSERT into user_to_service (user_id, service_id) VALUES(2, 1);
INSERT into user_to_service (user_id, service_id) VALUES(2, 8);
INSERT into user_to_service (user_id, service_id) VALUES(2, 9);
INSERT into user_to_service (user_id, service_id) VALUES(3, 1);
INSERT into user_to_service (user_id, service_id) VALUES(3, 2);
INSERT into user_to_service (user_id, service_id) VALUES(3, 8);
INSERT into user_to_service (user_id, service_id) VALUES(4, 2);
INSERT into user_to_service (user_id, service_id) VALUES(4, 5);
INSERT into user_to_service (user_id, service_id) VALUES(4, 6);
INSERT into user_to_service (user_id, service_id) VALUES(4, 7);
INSERT into user_to_service (user_id, service_id) VALUES(5, 5);
INSERT into user_to_service (user_id, service_id) VALUES(5, 6);
INSERT into user_to_service (user_id, service_id) VALUES(5, 7);
INSERT into user_to_service (user_id, service_id) VALUES(6, 3);
INSERT into user_to_service (user_id, service_id) VALUES(6, 4);
INSERT into user_to_service (user_id, service_id) VALUES(6, 9);
INSERT into user_to_service (user_id, service_id) VALUES(7, 3);
INSERT into user_to_service (user_id, service_id) VALUES(7, 4);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
