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
-- Table structure for table `t22_center_notification`
--

DROP TABLE IF EXISTS `t22_center_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t22_center_notification` (
  `C22_ID` int NOT NULL AUTO_INCREMENT,
  `C22_TITLE` varchar(255) NOT NULL,
  `C22_CONTENT` varchar(255) NOT NULL,
  `C22_CREATE_AT` datetime DEFAULT (now()),
  `C22_UPDATE_AT` datetime DEFAULT NULL,
  `C22_CENTER_ID` int NOT NULL,
  PRIMARY KEY (`C22_ID`),
  KEY `FK_T22_T03` (`C22_CENTER_ID`),
  CONSTRAINT `FK_T22_T03` FOREIGN KEY (`C22_CENTER_ID`) REFERENCES `t03_center` (`C03_CENTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t22_center_notification`
--

LOCK TABLES `t22_center_notification` WRITE;
/*!40000 ALTER TABLE `t22_center_notification` DISABLE KEYS */;
INSERT INTO `t22_center_notification` VALUES (1,'Thông báo lịch nghỉ lễ','Kính gửi quý thầy cô và các em học sinh, trung tâm thông báo lịch nghỉ lễ từ ngày 30/04 đến ngày 03/05/2024. Mong quý vị và các em chuẩn bị kế hoạch hợp lý để nghỉ ngơi và thư giãn.','2024-06-10 09:00:00',NULL,1),(2,'Thông báo nâng cấp phòng học','Trung tâm sẽ tiến hành nâng cấp phòng học vào ngày 10/06/2024. Mong quý thầy cô và các em học sinh thông cảm và hợp tác trong quá trình này.','2024-05-15 10:30:00',NULL,1),(3,'Thông báo quy định đi học đầy đủ','Để đảm bảo chất lượng giáo dục, trung tâm nhắc nhở các em học sinh về quy định đi học đầy đủ và đúng giờ. Mong quý phụ huynh và các em học sinh chú ý thực hiện.','2024-04-20 13:45:00',NULL,1),(4,'Thông báo về việc đóng học phí','Trung tâm nhắc nhở quý phụ huynh và các em học sinh về việc đóng học phí cho kỳ học mới. Mong quý vị và các em thực hiện đúng thời hạn để không gặp khó khăn trong quá trình học tập.','2024-01-25 15:00:00',NULL,1),(5,'Thông báo khóa học mới','Trung tâm sẽ khai giảng các khóa học mới vào tháng 7/2024. Quý thầy cô và các em học sinh vui lòng chú ý đăng ký và tham gia các khóa học phù hợp.','2024-02-20 08:30:00',NULL,1),(6,'Thông báo khóa học mới','Trung tâm sẽ khai giảng các khóa học mới vào tháng 7/2024. Quý thầy cô và các em học sinh vui lòng chú ý đăng ký và tham gia các khóa học phù hợp.','2024-04-30 08:30:00',NULL,2);
/*!40000 ALTER TABLE `t22_center_notification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-26 14:45:20
