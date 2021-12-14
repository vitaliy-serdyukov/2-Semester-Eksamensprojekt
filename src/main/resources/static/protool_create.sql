CREATE DATABASE  IF NOT EXISTS `protool`;
USE `protool`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `projects`;
DROP TABLE IF EXISTS `subprojects`;
DROP TABLE IF EXISTS `tasks`;
DROP TABLE IF EXISTS `teammates`;

CREATE TABLE `users` (
  `user_email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `company_name` varchar(100) NOT NULL,
  `phone_number` varchar(100) NOT NULL,
  PRIMARY KEY (`user_email`),
  UNIQUE KEY `email_UNIQUE` (`user_email`));



CREATE TABLE `projects` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `project_name` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  `project_hours_total` int DEFAULT NULL,
  `project_start_date` date DEFAULT NULL,
  `project_end_date` date DEFAULT NULL,
  `owner_email` varchar(100) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `owner_email_idx` (`owner_email`),
  CONSTRAINT `owner_email` FOREIGN KEY (`owner_email`) REFERENCES `users` (`user_email`) ON DELETE CASCADE ON UPDATE NO ACTION);



CREATE TABLE `subprojects` (
  `subproject_id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL,
  `subproject_name` varchar(100) NOT NULL,
  `subproject_hours_total` int DEFAULT NULL,
  `subproject_start_date` date DEFAULT NULL,
  `subproject_end_date` date DEFAULT NULL,
  `subproject_description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`subproject_id`),
  KEY `project_id_idx` (`project_id`),
  CONSTRAINT `project_id_subproject` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE ON UPDATE NO ACTION);


CREATE TABLE `tasks` (
  `subproject_id` int NOT NULL,
  `task_name` varchar(100) NOT NULL,
  `task_hours_total` int DEFAULT NULL,
  `task_start_date` date DEFAULT NULL,
  `task_end_date` date DEFAULT NULL,
  `task_description` varchar(100) DEFAULT NULL,
  KEY `subproject_id_idx` (`subproject_id`),
  CONSTRAINT `subproject_id` FOREIGN KEY (`subproject_id`) REFERENCES `subprojects` (`subproject_id`) ON DELETE CASCADE ON UPDATE NO ACTION);


CREATE TABLE `teammates` (
  `project_id` int NOT NULL,
  `member_email` varchar(100) NOT NULL,
  `teammate_hours` int NOT NULL,
  KEY `project_id_idx` (`project_id`),
  CONSTRAINT `project_id_members` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE ON UPDATE NO ACTION);










