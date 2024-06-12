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
-- Table structure for table `t03_center`
--

DROP TABLE IF EXISTS `t03_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t03_center` (
  `C03_CENTER_ID` int NOT NULL AUTO_INCREMENT,
  `C03_CENTER_CODE` varchar(255) NOT NULL,
  `c03_center_name` varchar(255) DEFAULT NULL,
  `c03_center_desc` varchar(255) DEFAULT NULL,
  `c03_center_address` varchar(255) DEFAULT NULL,
  `c03_center_phone` varchar(255) DEFAULT NULL,
  `c03_center_email` varchar(255) DEFAULT NULL,
  `c03_center_regulations` varchar(255) DEFAULT NULL,
  `C03_CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `c03_center_status` tinyint DEFAULT NULL,
  `c03_image_path` varchar(255) DEFAULT NULL,
  `C03_MANAGER_ID` int NOT NULL,
  PRIMARY KEY (`C03_CENTER_ID`),
  UNIQUE KEY `C03_CENTER_CODE` (`C03_CENTER_CODE`),
  KEY `FK_CENTER_MANAGER` (`C03_MANAGER_ID`),
  CONSTRAINT `FK_CENTER_MANAGER` FOREIGN KEY (`C03_MANAGER_ID`) REFERENCES `t14_user` (`C14_USER_ID`),
  CONSTRAINT `CHK_CENTER_CODE` CHECK ((`C03_CENTER_CODE` like _utf8mb4'CENTER%'))
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t03_center`
--

LOCK TABLES `t03_center` WRITE;
/*!40000 ALTER TABLE `t03_center` DISABLE KEYS */;
INSERT INTO `t03_center` VALUES (1,'CENTER1001','Center 1','Description of Center 1','123 Street, City','123456789','center1@example.com','Regulations of Center 1','2024-06-12 11:20:03',3,'path_to_image1',2),(2,'CENTER1002','Center 2','Description of Center 2','456 Street, City','987654321','center2@example.com','Regulations of Center 2','2024-06-12 11:20:03',3,'path_to_image2',2),(3,'CENTER1003','Center 3','Description of Center 3','789 Street, City','111223344','center3@example.com','Regulations of Center 3','2024-06-12 11:20:03',3,'path_to_image3',2),(4,'CENTER1004','Center 4','Description of Center 4','1011 Street, City','998877665','center4@example.com','Regulations of Center 4','2024-06-12 11:20:03',3,'path_to_image4',2),(5,'CENTER1005','Center 5','Description of Center 5','1213 Street, City','554433221','center5@example.com','Regulations of Center 5','2024-06-12 11:20:03',3,'path_to_image5',2),(6,'CENTER1006','Center 6','Description of Center 6','1415 Street, City','667788990','center6@example.com','Regulations of Center 6','2024-06-12 11:20:03',3,'path_to_image6',2),(7,'CENTER1007','Center 7','Description of Center 7','1617 Street, City','112233445','center7@example.com','Regulations of Center 7','2024-06-12 11:20:03',3,'path_to_image7',3),(8,'CENTER1008','Center 8','Description of Center 8','1819 Street, City','998877665','center8@example.com','Regulations of Center 8','2024-06-12 11:20:03',3,'path_to_image8',3),(9,'CENTER1009','Center 9','Description of Center 9','2021 Street, City','554433221','center9@example.com','Regulations of Center 9','2024-06-12 11:20:03',3,'path_to_image9',3),(10,'CENTER1010','Center 10','Description of Center 10','2223 Street, City','667788990','center10@example.com','Regulations of Center 10','2024-06-12 11:20:03',3,'path_to_image10',3),(11,'CENTER1011','Center 11','Description of Center 11','2425 Street, City','112233445','center11@example.com','Regulations of Center 11','2024-06-12 11:20:03',3,'path_to_image11',3),(12,'CENTER1012','Center 12','Description of Center 12','2627 Street, City','998877665','center12@example.com','Regulations of Center 12','2024-06-12 11:20:03',3,'path_to_image12',3),(13,'CENTER1013','Center 13','Description of Center 13','2829 Street, City','554433221','center13@example.com','Regulations of Center 13','2024-06-12 11:20:03',3,'path_to_image13',2),(14,'CENTER1014','Center 14','Description of Center 14','3031 Street, City','667788990','center14@example.com','Regulations of Center 14','2024-06-12 11:20:03',3,'path_to_image14',2),(15,'CENTER1015','Center 15','Description of Center 15','3233 Street, City','112233445','center15@example.com','Regulations of Center 15','2024-06-12 11:20:03',3,'path_to_image15',2),(16,'CENTER1016','Center 16','Description of Center 16','3435 Street, City','998877665','center16@example.com','Regulations of Center 16','2024-06-12 11:20:03',3,'path_to_image16',4),(17,'CENTER1017','Center 17','Description of Center 17','3637 Street, City','554433221','center17@example.com','Regulations of Center 17','2024-06-12 11:20:03',3,'path_to_image17',4),(18,'CENTER1018','Center 18','Description of Center 18','3839 Street, City','667788990','center18@example.com','Regulations of Center 18','2024-06-12 11:20:03',3,'path_to_image18',3),(19,'CENTER1019','Center 19','Description of Center 19','4041 Street, City','112233445','center19@example.com','Regulations of Center 19','2024-06-12 11:20:03',3,'path_to_image19',3),(20,'CENTER1020','Center 20','Description of Center 20','4243 Street, City','998877665','center20@example.com','Regulations of Center 20','2024-06-12 11:20:03',3,'path_to_image20',3);
/*!40000 ALTER TABLE `t03_center` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-12 19:35:51
