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
-- Table structure for table `t09_attendance`
--

DROP TABLE IF EXISTS `t09_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t09_attendance` (
  `C09_ATTENDANCE_ID` int NOT NULL AUTO_INCREMENT,
  `C09_ATTENDANCE_STATUS` bit(1) NOT NULL,
  `C09_STUDENT_ID` int NOT NULL,
  `C09_SLOT_ID` int NOT NULL,
  PRIMARY KEY (`C09_ATTENDANCE_ID`),
  UNIQUE KEY `CK` (`C09_STUDENT_ID`,`C09_SLOT_ID`),
  UNIQUE KEY `CK_ATTENDANCE_UNQ` (`C09_STUDENT_ID`,`C09_SLOT_ID`),
  KEY `FK_ATTENDANCE_SLOT` (`C09_SLOT_ID`),
  CONSTRAINT `FK_ATTENDANCE_SLOT` FOREIGN KEY (`C09_SLOT_ID`) REFERENCES `t02_slot` (`C02_SLOT_ID`),
  CONSTRAINT `FK_ATTENDANCE_STUDENT` FOREIGN KEY (`C09_STUDENT_ID`) REFERENCES `t14_user` (`C14_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t09_attendance`
--

LOCK TABLES `t09_attendance` WRITE;
/*!40000 ALTER TABLE `t09_attendance` DISABLE KEYS */;
INSERT INTO `t09_attendance` VALUES (1,_binary '',1,1),(2,_binary '',6,2),(3,_binary '',11,3),(4,_binary '',16,4),(5,_binary '',1,5),(6,_binary '',6,6),(7,_binary '',11,7),(8,_binary '',16,8),(9,_binary '',1,9),(10,_binary '',6,10),(11,_binary '',11,11),(12,_binary '',16,12),(13,_binary '',1,13),(14,_binary '',6,14),(15,_binary '',11,15),(16,_binary '',16,16),(17,_binary '',1,17),(18,_binary '',6,18),(19,_binary '',11,19),(20,_binary '',10,1),(21,_binary '',10,2),(22,_binary '',10,3),(23,_binary '',10,4),(24,_binary '',10,5),(25,_binary '',10,6),(26,_binary '',10,7),(27,_binary '',10,8),(28,_binary '',10,9),(29,_binary '',10,10),(30,_binary '',10,11),(31,_binary '',10,12),(32,_binary '',10,13),(33,_binary '',10,14),(34,_binary '',10,15),(35,_binary '',10,16),(36,_binary '',10,17),(37,_binary '',10,18),(38,_binary '',10,19),(39,_binary '',15,1),(40,_binary '',15,2),(41,_binary '',15,3),(42,_binary '',15,4),(43,_binary '',15,5),(44,_binary '',15,6),(45,_binary '',15,7),(46,_binary '',15,8),(47,_binary '',15,9),(48,_binary '',15,10),(49,_binary '',15,11),(50,_binary '',15,12),(51,_binary '',15,13),(52,_binary '',15,14),(53,_binary '',15,15),(54,_binary '',15,16),(55,_binary '',15,17),(56,_binary '',15,18),(57,_binary '',15,19),(58,_binary '',20,1),(59,_binary '',20,2),(60,_binary '',20,3),(61,_binary '',20,4),(62,_binary '',20,5),(63,_binary '',20,6),(64,_binary '',20,7),(65,_binary '',20,8),(66,_binary '',20,9),(67,_binary '',20,10),(68,_binary '',20,11),(69,_binary '',20,12),(70,_binary '',20,13),(71,_binary '',20,14),(72,_binary '',20,15),(73,_binary '',20,16),(74,_binary '',20,17),(75,_binary '',20,18),(76,_binary '',20,19),(77,_binary '',25,1),(78,_binary '',25,2),(79,_binary '',25,3),(80,_binary '',25,4),(81,_binary '',25,5),(82,_binary '',25,6),(83,_binary '',25,7),(84,_binary '',25,8),(85,_binary '',25,9),(86,_binary '',25,10),(87,_binary '',25,11),(88,_binary '',25,12),(89,_binary '',25,13),(90,_binary '',25,14),(91,_binary '',25,15),(92,_binary '',25,16),(93,_binary '',25,17),(94,_binary '',25,18),(95,_binary '',25,19),(96,_binary '',30,1),(97,_binary '',30,2),(98,_binary '',30,3),(99,_binary '',30,4),(100,_binary '',30,5),(101,_binary '',30,6),(102,_binary '',30,7),(103,_binary '',30,8),(104,_binary '',30,9),(105,_binary '',30,10),(106,_binary '',30,11),(107,_binary '',30,12),(108,_binary '',30,13),(109,_binary '',30,14),(110,_binary '',30,15),(111,_binary '',30,16),(112,_binary '',30,17),(113,_binary '',30,18),(114,_binary '',30,19);
/*!40000 ALTER TABLE `t09_attendance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-22 22:24:38
