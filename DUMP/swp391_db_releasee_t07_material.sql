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
-- Table structure for table `t07_material`
--

DROP TABLE IF EXISTS `t07_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t07_material` (
  `C07_MATERIALS_ID` int NOT NULL AUTO_INCREMENT,
  `C07_MATERIALS_NAME` varchar(100) NOT NULL,
  `C07_TEACHER_ID` int NOT NULL,
  `C07_SUBJECT_ID` int NOT NULL,
  PRIMARY KEY (`C07_MATERIALS_ID`),
  KEY `FK_MATERIAL_TEACHER` (`C07_TEACHER_ID`),
  KEY `FK_MATERIAL_SUBJECT` (`C07_SUBJECT_ID`),
  CONSTRAINT `FK_MATERIAL_SUBJECT` FOREIGN KEY (`C07_SUBJECT_ID`) REFERENCES `t13_subject` (`C13_SUBJECT_ID`),
  CONSTRAINT `FK_MATERIAL_TEACHER` FOREIGN KEY (`C07_TEACHER_ID`) REFERENCES `t14_user` (`C14_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t07_material`
--

LOCK TABLES `t07_material` WRITE;
/*!40000 ALTER TABLE `t07_material` DISABLE KEYS */;
INSERT INTO `t07_material` VALUES (1,'Materials 1',3,1),(2,'Materials 2',8,2),(3,'Materials 3',13,3),(4,'Materials 4',18,4),(5,'Materials 5',3,5),(6,'Materials 6',8,6),(7,'Materials 7',13,7),(8,'Materials 8',18,8),(9,'Materials 9',3,9),(10,'Materials 10',8,10),(11,'Materials 11',13,11),(12,'Materials 12',18,12),(13,'Materials 13',3,13),(14,'Materials 14',8,14),(15,'Materials 15',13,15),(16,'Materials 16',18,16),(17,'Materials 17',3,17),(18,'Materials 18',8,18),(19,'Materials 19',13,19),(20,'Materials 20',18,20);
/*!40000 ALTER TABLE `t07_material` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-12 19:35:53
