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
-- Table structure for table `t13_subject`
--

DROP TABLE IF EXISTS `t13_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t13_subject` (
  `C13_SUBJECT_ID` int NOT NULL AUTO_INCREMENT,
  `C13_SUBJECT_CODE` varchar(255) NOT NULL,
  `c13_subject_name` varchar(255) NOT NULL,
  `c13_subject_desc` text NOT NULL,
  PRIMARY KEY (`C13_SUBJECT_ID`),
  UNIQUE KEY `C13_SUBJECT_CODE` (`C13_SUBJECT_CODE`),
  UNIQUE KEY `C13_SUBJECT_NAME` (`c13_subject_name`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t13_subject`
--

LOCK TABLES `t13_subject` WRITE;
/*!40000 ALTER TABLE `t13_subject` DISABLE KEYS */;
INSERT INTO `t13_subject` VALUES (1,'MATH1','Toán 1','Description of Toán 1'),(2,'MATH2','Toán 2','Description of Toán 2'),(3,'MATH3','Toán 3','Description of Toán 3'),(4,'MATH4','Toán 4','Description of Toán 4'),(5,'MATH5','Toán 5','Description of Toán 5'),(6,'MATH6','Toán 6','Description of Toán 6'),(7,'MATH7','Toán 7','Description of Toán 7'),(8,'MATH8','Toán 8','Description of Toán 8'),(9,'MATH9','Toán 9','Description of Toán 9'),(10,'PHYS1','Lý 1','Description of Lý 1'),(11,'PHYS2','Lý 2','Description of Lý 2'),(12,'PHYS3','Lý 3','Description of Lý 3'),(13,'PHYS4','Lý 4','Description of Lý 4'),(14,'PHYS5','Lý 5','Description of Lý 5'),(15,'PHYS6','Lý 6','Description of Lý 6'),(16,'PHYS7','Lý 7','Description of Lý 7'),(17,'PHYS8','Lý 8','Description of Lý 8'),(18,'PHYS9','Lý 9','Description of Lý 9'),(19,'PHYS10','Lý 10','Description of Lý 10'),(20,'CHEM1','Hóa 1','Description of Hóa 1'),(21,'CHEM2','Hóa 2','Description of Hóa 2'),(22,'CHEM3','Hóa 3','Description of Hóa 3'),(23,'CHEM4','Hóa 4','Description of Hóa 4'),(24,'CHEM5','Hóa 5','Description of Hóa 5'),(25,'CHEM6','Hóa 6','Description of Hóa 6'),(26,'CHEM7','Hóa 7','Description of Hóa 7'),(27,'CHEM8','Hóa 8','Description of Hóa 8'),(28,'CHEM9','Hóa 9','Description of Hóa 9'),(29,'CHEM10','Hóa 10','Description of Hóa 10'),(30,'ENG1','Anh 1','Description of Anh 1'),(31,'ENG2','Anh 2','Description of Anh 2'),(32,'ENG3','Anh 3','Description of Anh 3'),(33,'ENG4','Anh 4','Description of Anh 4'),(34,'ENG5','Anh 5','Description of Anh 5'),(35,'ENG6','Anh 6','Description of Anh 6'),(36,'ENG7','Anh 7','Description of Anh 7'),(37,'ENG8','Anh 8','Description of Anh 8'),(38,'ENG9','Anh 9','Description of Anh 9'),(39,'ENG10','Anh 10','Description of Anh 10'),(40,'LIT1','Văn 1','Description of Văn 1'),(41,'LIT2','Văn 2','Description of Văn 2'),(42,'LIT3','Văn 3','Description of Văn 3'),(43,'LIT4','Văn 4','Description of Văn 4'),(44,'LIT5','Văn 5','Description of Văn 5'),(45,'LIT6','Văn 6','Description of Văn 6'),(46,'LIT7','Văn 7','Description of Văn 7'),(47,'LIT8','Văn 8','Description of Văn 8'),(48,'LIT9','Văn 9','Description of Văn 9'),(49,'LIT10','Văn 10','Description of Văn 10');
/*!40000 ALTER TABLE `t13_subject` ENABLE KEYS */;
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
