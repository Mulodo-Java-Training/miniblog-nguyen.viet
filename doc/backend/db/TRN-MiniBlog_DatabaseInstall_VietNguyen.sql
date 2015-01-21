-- Create database
CREATE DATABASE mini_blog DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Use database
USE mini_blog;

--
-- Character encoding setting
--
SET NAMES utf8;

--
-- 'users' table structure
--
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(100) NOT NULL,
  `lastname` VARCHAR(100) NOT NULL,
  `birthday` DATE NULL,
  `email` VARCHAR(45) NOT NULL,
  `status` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `u_username_UNIQUE` (`username` ASC));
--
-- 'articles' table structure
--
 CREATE TABLE IF NOT EXISTS `articles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `users_id` INT NOT NULL,
  `title` VARCHAR(250) NOT NULL,
  `description` TEXT(1000) NULL,
  `status` INT NOT NULL DEFAULT 0,
  `date_create` DATETIME NOT NULL,
  `date_modify` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_articles_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_articles_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

--
-- 'comments' table structure
--
CREATE TABLE IF NOT EXISTS `comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `articles_id` INT NOT NULL,
  `users_id` INT NOT NULL,
  `description` TEXT(1000) NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  `date_create` DATETIME NOT NULL,
  `date_modify` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_articles1_idx` (`articles_id` ASC),
  INDEX `fk_comment_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_comment_articles1`
    FOREIGN KEY (`articles_id`)
    REFERENCES `articles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

--
-- 'likes' table structure
--
CREATE TABLE IF NOT EXISTS `likes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `users_id` INT NOT NULL,
  `types` VARCHAR(1) NOT NULL,
  `typeid` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_likes_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_likes_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

