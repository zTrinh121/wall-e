-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: swp391_db_releasee
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t21_center_posts`
--

DROP TABLE IF EXISTS `t21_center_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t21_center_posts` (
  `C21_ID` int NOT NULL AUTO_INCREMENT,
  `C21_TITLE` varchar(255) NOT NULL,
  `c21_content` varchar(255) NOT NULL,
  `c21_post_status` tinyint NOT NULL,
  `C21_FILE_URL` varchar(255) DEFAULT NULL,
  `C21_CREATED_AT` datetime NOT NULL,
  `C21_UPDATED_AT` datetime DEFAULT NULL,
  `C21_SEND_TO_CENTER` varchar(255) NOT NULL,
  PRIMARY KEY (`C21_ID`),
  KEY `FK_T21_CENTER` (`C21_SEND_TO_CENTER`),
  CONSTRAINT `FK_T21_CENTER` FOREIGN KEY (`C21_SEND_TO_CENTER`) REFERENCES `t03_center` (`C03_CENTER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t21_center_posts`
--

LOCK TABLES `t21_center_posts` WRITE;
/*!40000 ALTER TABLE `t21_center_posts` DISABLE KEYS */;
INSERT INTO `t21_center_posts` VALUES (1,'Classroom Change','Due to renovations, classes in Room 101 will be moved to Room 202.',2,NULL,'2024-06-01 08:00:00',NULL,'CENTER1001'),(2,'Attendance System Upgrade','Our attendance system will be upgraded this weekend.',1,NULL,'2024-06-02 09:00:00',NULL,'CENTER1001'),(3,'New Grading System','We are introducing a new grading system for the next semester.',2,NULL,'2024-06-03 10:00:00',NULL,'CENTER1001'),(4,'Class Timing Adjustment','Class timings have been adjusted to better fit student schedules.',1,NULL,'2024-06-04 11:00:00',NULL,'CENTER1001'),(5,'Library Schedule Change','The library will now be open from 8 AM to 8 PM.',2,NULL,'2024-06-05 12:00:00',NULL,'CENTER1001'),(6,'New Attendance Policy','Please review our updated attendance policy.',1,NULL,'2024-06-06 13:00:00',NULL,'CENTER1001'),(7,'Exam Room Change','The exam room for Math 101 has been changed to Room 303.',2,NULL,'2024-06-07 14:00:00',NULL,'CENTER1001'),(8,'Student ID Cards','New student ID cards will be distributed next week.',1,NULL,'2024-06-08 15:00:00',NULL,'CENTER1001'),(9,'Online Class Schedule','The online class schedule for the next term is now available.',2,NULL,'2024-06-09 16:00:00',NULL,'CENTER1001'),(10,'New Student Portal','We are launching a new student portal for better access to resources.',2,NULL,'2024-06-10 17:00:00',NULL,'CENTER1001'),(11,'Project Submission Guidelines','Please follow the new guidelines for project submissions.',2,NULL,'2024-06-11 18:00:00',NULL,'CENTER1001'),(12,'Faculty Meeting','A faculty meeting is scheduled for next Monday.',2,NULL,'2024-06-12 19:00:00',NULL,'CENTER1001'),(13,'Extra-Curricular Activities','Sign up for new extra-curricular activities available this semester.',2,NULL,'2024-06-13 20:00:00',NULL,'CENTER1001'),(14,'Holiday Homework','Check the portal for holiday homework assignments.',1,NULL,'2024-06-14 21:00:00',NULL,'CENTER1001'),(15,'New Textbooks','New textbooks are now available in the bookstore.',3,NULL,'2024-06-15 22:00:00',NULL,'CENTER1001'),(16,'Health and Safety Guidelines','Review the updated health and safety guidelines for the campus.',3,NULL,'2024-06-16 23:00:00',NULL,'CENTER1001'),(17,'E-Learning Tools','Explore new e-learning tools introduced for this term.',3,NULL,'2024-06-17 08:00:00',NULL,'CENTER1001'),(18,'Campus Security Update','New security measures have been implemented on campus.',3,NULL,'2024-06-18 09:00:00',NULL,'CENTER1001'),(19,'Student Counseling','Student counseling services are now available every weekday.',3,NULL,'2024-06-19 10:00:00',NULL,'CENTER1001'),(20,'Lab Equipment Upgrade','The lab has been upgraded with new equipment.',3,NULL,'2024-06-20 11:00:00',NULL,'CENTER1001'),(21,'New Cafeteria Menu','The cafeteria has introduced a new menu for the summer.',3,NULL,'2024-06-21 12:00:00',NULL,'CENTER1001'),(22,'Class Participation Policy','Our class participation policy has been updated.',3,NULL,'2024-06-22 13:00:00',NULL,'CENTER1001'),(23,'Exam Preparation Tips','Here are some tips to help you prepare for exams.',3,NULL,'2024-06-23 14:00:00',NULL,'CENTER1001'),(24,'New Computer Lab','A new computer lab is now open for student use.',3,NULL,'2024-06-24 15:00:00',NULL,'CENTER1001');
/*!40000 ALTER TABLE `t21_center_posts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-22 22:24:39
