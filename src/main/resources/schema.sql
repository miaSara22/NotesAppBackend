CREATE database notesserverdb IF NOT EXISTS;
USE notesserverdb;
--
CREATE TABLE IF NOT EXISTS `notes` (
  `note_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `note_name` varchar(30) NOT NULL,
  `note_description` varchar(50) DEFAULT NULL,
   PRIMARY KEY (`note_id`)
   --FOREIGN KEY(`owner_id`) REFERENCES lists(`list_id`)
);
--
CREATE TABLE IF NOT EXISTS `lists` (
  `list_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `list_name` varchar(30) NOT NULL,
  PRIMARY KEY(`list_id`)
--FOREIGN KEY(`owner_id`) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `full_name` varchar(30) NOT NULL,
  `user_pwd` varchar(100) NOT NULL,
   PRIMARY KEY (`user_id`)
);