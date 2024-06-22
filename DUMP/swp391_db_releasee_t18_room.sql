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
-- Table structure for table `t18_room`
--

DROP TABLE IF EXISTS `t18_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t18_room` (
  `c18_room_id` int NOT NULL AUTO_INCREMENT,
  `c18_room_name` varchar(255) NOT NULL,
  `c18_center_id` int DEFAULT NULL,
  PRIMARY KEY (`c18_room_id`),
  UNIQUE KEY `CK_T18_UNQ` (`c18_room_name`,`c18_center_id`),
  KEY `FKapmt9eah4aqm0jk5rapl8ttd0` (`c18_center_id`),
  CONSTRAINT `FKapmt9eah4aqm0jk5rapl8ttd0` FOREIGN KEY (`c18_center_id`) REFERENCES `t03_center` (`C03_CENTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t18_room`
--

LOCK TABLES `t18_room` WRITE;
/*!40000 ALTER TABLE `t18_room` DISABLE KEYS */;
INSERT INTO `t18_room` VALUES (1,'Room A',1),(6,'Room A',2),(11,'Room A',3),(16,'Room A',4),(21,'Room A',5),(26,'Room A',6),(31,'Room A',7),(36,'Room A',8),(41,'Room A',9),(46,'Room A',10),(51,'Room A',11),(56,'Room A',12),(61,'Room A',13),(66,'Room A',14),(71,'Room A',15),(76,'Room A',16),(81,'Room A',17),(86,'Room A',18),(91,'Room A',19),(96,'Room A',20),(2,'Room B',1),(7,'Room B',2),(12,'Room B',3),(17,'Room B',4),(22,'Room B',5),(27,'Room B',6),(32,'Room B',7),(37,'Room B',8),(42,'Room B',9),(47,'Room B',10),(52,'Room B',11),(57,'Room B',12),(62,'Room B',13),(67,'Room B',14),(72,'Room B',15),(77,'Room B',16),(82,'Room B',17),(87,'Room B',18),(92,'Room B',19),(97,'Room B',20),(3,'Room C',1),(8,'Room C',2),(13,'Room C',3),(18,'Room C',4),(23,'Room C',5),(33,'Room C',7),(38,'Room C',8),(43,'Room C',9),(48,'Room C',10),(53,'Room C',11),(58,'Room C',12),(63,'Room C',13),(68,'Room C',14),(73,'Room C',15),(78,'Room C',16),(83,'Room C',17),(88,'Room C',18),(98,'Room C',20),(4,'Room D',1),(9,'Room D',2),(14,'Room D',3),(19,'Room D',4),(24,'Room D',5),(29,'Room D',6),(34,'Room D',7),(39,'Room D',8),(44,'Room D',9),(49,'Room D',10),(54,'Room D',11),(59,'Room D',12),(64,'Room D',13),(69,'Room D',14),(74,'Room D',15),(79,'Room D',16),(84,'Room D',17),(89,'Room D',18),(94,'Room D',19),(99,'Room D',20),(5,'Room E',1),(10,'Room E',2),(15,'Room E',3),(20,'Room E',4),(25,'Room E',5),(30,'Room E',6),(35,'Room E',7),(40,'Room E',8),(45,'Room E',9),(50,'Room E',10),(55,'Room E',11),(60,'Room E',12),(65,'Room E',13),(70,'Room E',14),(75,'Room E',15),(80,'Room E',16),(85,'Room E',17),(90,'Room E',18),(95,'Room E',19),(100,'Room E',20),(93,'Roomt19_private_notifications C',19),(28,'Rt02_slotoom C',6);
/*!40000 ALTER TABLE `t18_room` ENABLE KEYS */;
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
