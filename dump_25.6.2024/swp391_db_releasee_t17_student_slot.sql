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
-- Table structure for table `t17_student_slot`
--

DROP TABLE IF EXISTS `t17_student_slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t17_student_slot` (
  `C17_STUDENT_SLOT_ID` int NOT NULL AUTO_INCREMENT,
  `C17_STUDENT_ID` int NOT NULL,
  `C17_SLOT_ID` int NOT NULL,
  `C17_ATTENDANCE_STATUS` bit(1) DEFAULT b'0',
  PRIMARY KEY (`C17_STUDENT_SLOT_ID`),
  UNIQUE KEY `CK_T17_UNQ` (`C17_STUDENT_ID`,`C17_SLOT_ID`),
  KEY `FK_T17_SLOT` (`C17_SLOT_ID`),
  CONSTRAINT `FK_T17_SLOT` FOREIGN KEY (`C17_SLOT_ID`) REFERENCES `t02_slot` (`C02_SLOT_ID`),
  CONSTRAINT `FK_T17_STUDENT` FOREIGN KEY (`C17_STUDENT_ID`) REFERENCES `t14_user` (`C14_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t17_student_slot`
--

LOCK TABLES `t17_student_slot` WRITE;
/*!40000 ALTER TABLE `t17_student_slot` DISABLE KEYS */;
INSERT INTO `t17_student_slot` VALUES (1,10,1,_binary '\0'),(2,10,2,_binary '\0'),(3,10,3,_binary '\0'),(4,10,4,_binary '\0'),(5,10,5,_binary '\0'),(6,10,6,_binary '\0'),(7,10,7,_binary '\0'),(8,10,8,_binary '\0'),(9,10,9,_binary '\0'),(10,10,10,_binary '\0'),(11,10,11,_binary '\0'),(12,10,12,_binary '\0'),(13,10,13,_binary '\0'),(14,10,14,_binary '\0'),(15,10,15,_binary '\0'),(16,10,16,_binary '\0'),(17,10,17,_binary '\0'),(18,10,18,_binary '\0'),(19,10,19,_binary '\0'),(20,10,20,_binary '\0'),(21,10,21,_binary '\0'),(22,10,22,_binary '\0'),(23,10,23,_binary '\0'),(24,10,24,_binary '\0'),(25,10,25,_binary '\0'),(26,10,26,_binary '\0'),(27,10,27,_binary '\0'),(28,10,28,_binary '\0'),(29,10,29,_binary '\0'),(30,10,30,_binary '\0'),(31,10,31,_binary '\0'),(32,10,32,_binary '\0'),(33,10,33,_binary '\0'),(34,10,34,_binary '\0'),(35,10,35,_binary '\0'),(36,10,36,_binary '\0'),(37,10,37,_binary '\0'),(38,10,38,_binary '\0'),(39,10,39,_binary '\0'),(40,10,40,_binary '\0'),(41,10,41,_binary '\0'),(42,10,42,_binary '\0'),(43,10,43,_binary '\0'),(44,10,44,_binary '\0'),(45,10,45,_binary '\0'),(46,10,46,_binary '\0'),(47,10,47,_binary '\0'),(48,10,48,_binary '\0'),(49,10,49,_binary '\0'),(50,10,50,_binary '\0'),(51,10,51,_binary '\0'),(52,10,52,_binary '\0'),(53,10,53,_binary '\0'),(54,10,54,_binary '\0'),(55,10,55,_binary '\0'),(56,10,56,_binary '\0'),(57,10,57,_binary '\0'),(58,10,58,_binary '\0'),(59,10,59,_binary '\0'),(60,10,60,_binary '\0'),(61,10,61,_binary '\0'),(62,10,62,_binary '\0'),(63,10,63,_binary '\0'),(64,10,64,_binary '\0'),(65,10,65,_binary '\0'),(66,10,66,_binary '\0'),(67,10,67,_binary '\0'),(68,10,68,_binary '\0'),(69,10,69,_binary '\0'),(70,10,70,_binary '\0'),(71,10,71,_binary '\0'),(72,10,72,_binary '\0'),(73,10,73,_binary '\0'),(74,10,74,_binary '\0'),(75,10,75,_binary '\0'),(76,10,76,_binary '\0'),(77,10,77,_binary '\0'),(78,10,78,_binary '\0'),(79,10,79,_binary '\0');
/*!40000 ALTER TABLE `t17_student_slot` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-25  2:18:51
