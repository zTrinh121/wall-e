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
-- Table structure for table `t11_system_notification`
--

DROP TABLE IF EXISTS `t11_system_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t11_system_notification` (
  `C11_ID` int NOT NULL AUTO_INCREMENT,
  `C11_TITLE` varchar(255) NOT NULL,
  `c11_content` varchar(255) NOT NULL,
  `C11_CREATED_AT` datetime NOT NULL,
  `C11_UPDATED_AT` datetime DEFAULT NULL,
  PRIMARY KEY (`C11_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t11_system_notification`
--

LOCK TABLES `t11_system_notification` WRITE;
/*!40000 ALTER TABLE `t11_system_notification` DISABLE KEYS */;
INSERT INTO `t11_system_notification` VALUES (1,'System Update','The system will undergo maintenance on Saturday.','2024-06-01 08:00:00',NULL),(2,'New Feature Released','We have released a new feature that allows users to track their expenses.','2024-06-02 09:00:00',NULL),(3,'Downtime Alert','There will be a scheduled downtime for database optimization.','2024-06-03 10:00:00',NULL),(4,'Security Update','A new security patch has been applied to the system.','2024-06-04 11:00:00',NULL),(5,'User Survey','Please take a moment to fill out our user satisfaction survey.','2024-06-05 12:00:00',NULL),(6,'Bug Fixes','Several bugs have been fixed in the latest update.','2024-06-06 13:00:00',NULL),(7,'Weekly Report','The weekly report is now available in your dashboard.','2024-06-07 14:00:00',NULL),(8,'New Tutorial','Check out our new tutorial on how to use the advanced features.','2024-06-08 15:00:00',NULL),(9,'Feature Request','We value your feedback! Submit your feature requests to help us improve.','2024-06-09 16:00:00',NULL),(10,'Holiday Announcement','The office will be closed for the upcoming holiday.','2024-06-10 17:00:00',NULL),(11,'Performance Update','System performance has been improved in the latest update.','2024-06-11 18:00:00',NULL),(12,'New Integration','We have integrated with a new third-party service for better functionality.','2024-06-12 19:00:00',NULL),(13,'Feedback Request','We would love to hear your thoughts on the recent changes.','2024-06-13 20:00:00',NULL),(14,'Scheduled Maintenance','Scheduled maintenance will occur this weekend. Expect some downtime.','2024-06-14 21:00:00',NULL),(15,'Data Migration','We are migrating data to a new server. There may be some service interruptions.','2024-06-15 22:00:00',NULL),(16,'Account Verification','Please verify your account to continue using our services.','2024-06-16 23:00:00',NULL),(17,'Newsletter Subscription','Subscribe to our newsletter to stay updated with the latest news.','2024-06-17 08:00:00',NULL),(18,'Privacy Policy Update','Our privacy policy has been updated. Please review the changes.','2024-06-18 09:00:00',NULL),(19,'New Dashboard','The new dashboard layout is now live. Enjoy the enhanced user experience.','2024-06-19 10:00:00',NULL),(20,'System Health Check','A system health check will be performed tonight. Some services may be temporarily unavailable.','2024-06-20 11:00:00',NULL),(21,'The lastest public notification !!!!!!!!!!!! Ronaldo SIUUUUUUUUUUUUUUU','Nội dung thông báo công khai của bạn ở đây.','2024-06-22 09:19:06',NULL);
/*!40000 ALTER TABLE `t11_system_notification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-22 22:24:40
