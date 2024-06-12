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
-- Table structure for table `t20_public_notifications`
--

DROP TABLE IF EXISTS `t20_public_notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t20_public_notifications` (
  `C20_ID` int NOT NULL AUTO_INCREMENT,
  `C20_TITLE` varchar(255) NOT NULL,
  `c20_content` varchar(255) NOT NULL,
  `C20_CREATED_AT` datetime NOT NULL,
  `C20_UPDATED_AT` datetime DEFAULT NULL,
  `C20_SEND_TO_CENTER` varchar(255) NOT NULL,
  PRIMARY KEY (`C20_ID`),
  KEY `FK_T20_CENTER` (`C20_SEND_TO_CENTER`),
  CONSTRAINT `FK_T20_CENTER` FOREIGN KEY (`C20_SEND_TO_CENTER`) REFERENCES `t03_center` (`C03_CENTER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t20_public_notifications`
--

LOCK TABLES `t20_public_notifications` WRITE;
/*!40000 ALTER TABLE `t20_public_notifications` DISABLE KEYS */;
INSERT INTO `t20_public_notifications` VALUES (1,'New Course Announcement','We are excited to introduce a new course on Data Science starting next month.','2024-06-01 08:00:00',NULL,'CENTER1001'),(2,'Summer Camp Enrollment','Enroll now for our summer camp program for students aged 10-15.','2024-06-02 09:00:00',NULL,'CENTER1001'),(3,'Guest Lecture','Join us for a guest lecture on Machine Learning by Dr. John Doe.','2024-06-03 10:00:00',NULL,'CENTER1001'),(4,'Library Upgrade','Our library has been upgraded with the latest educational resources.','2024-06-04 11:00:00',NULL,'CENTER1001'),(5,'Exam Schedule','The exam schedule for the upcoming semester is now available online.','2024-06-05 12:00:00',NULL,'CENTER1001'),(6,'Workshop on AI','Attend our hands-on workshop on Artificial Intelligence next weekend.','2024-06-06 13:00:00',NULL,'CENTER1001'),(7,'Parent-Teacher Meeting','We invite all parents to attend the upcoming parent-teacher meeting.','2024-06-07 14:00:00',NULL,'CENTER1001'),(8,'Scholarship Opportunities','Explore new scholarship opportunities for the academic year 2024-2025.','2024-06-08 15:00:00',NULL,'CENTER1001'),(9,'Science Fair','Participate in our annual science fair and showcase your projects.','2024-06-09 16:00:00',NULL,'CENTER1001'),(10,'Math Olympiad','Register now for the upcoming Math Olympiad competition.','2024-06-10 17:00:00',NULL,'CENTER1001'),(11,'Coding Bootcamp','Join our intensive coding bootcamp to enhance your programming skills.','2024-06-11 18:00:00',NULL,'CENTER1001'),(12,'Student Orientation','Attend the orientation session for new students on July 1st.','2024-06-12 19:00:00',NULL,'CENTER1001'),(13,'Art Exhibition','Visit our art exhibition to see the creative works of our students.','2024-06-13 20:00:00',NULL,'CENTER1001'),(14,'Online Learning Platform','Access our new online learning platform for a variety of courses.','2024-06-14 21:00:00',NULL,'CENTER1001'),(15,'Educational Trip','Join us for an educational trip to the National Science Museum.','2024-06-15 22:00:00',NULL,'CENTER1001'),(16,'Career Counseling','Book a session with our career counselors to plan your future.','2024-06-16 23:00:00',NULL,'CENTER1001'),(17,'Cultural Fest','Participate in our annual cultural fest and showcase your talents.','2024-06-17 08:00:00',NULL,'CENTER1001'),(18,'Debate Competition','Register for the inter-school debate competition next month.','2024-06-18 09:00:00',NULL,'CENTER1001'),(19,'Health and Wellness','Attend our workshop on health and wellness for students.','2024-06-19 10:00:00',NULL,'CENTER1001'),(20,'Environmental Awareness','Join our campaign to promote environmental awareness.','2024-06-20 11:00:00',NULL,'CENTER1001'),(21,'Student Exchange Program','Apply for our student exchange program to study abroad.','2024-06-21 12:00:00',NULL,'CENTER1001'),(22,'New Faculty','Welcome our new faculty members joining us this semester.','2024-06-22 13:00:00',NULL,'CENTER1001'),(23,'Sports Day','Participate in our annual sports day events.','2024-06-23 14:00:00',NULL,'CENTER1001'),(24,'Music Workshop','Join our music workshop to learn new instruments.','2024-06-24 15:00:00',NULL,'CENTER1001'),(25,'Alumni Meet','Attend the alumni meet and reconnect with old friends.','2024-06-25 16:00:00',NULL,'CENTER1001'),(26,'Internship Fair','Explore internship opportunities at our internship fair.','2024-06-26 17:00:00',NULL,'CENTER1001'),(27,'Robotics Club','Join our robotics club to learn about building robots.','2024-06-27 18:00:00',NULL,'CENTER1001'),(28,'Literary Fest','Participate in our literary fest and showcase your writing skills.','2024-06-28 19:00:00',NULL,'CENTER1001'),(29,'Quiz Competition','Take part in our general knowledge quiz competition.','2024-06-29 20:00:00',NULL,'CENTER1001'),(30,'Graduation Ceremony','Attend the graduation ceremony to celebrate your achievements.','2024-06-30 21:00:00',NULL,'CENTER1001');
/*!40000 ALTER TABLE `t20_public_notifications` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-12 19:35:52
