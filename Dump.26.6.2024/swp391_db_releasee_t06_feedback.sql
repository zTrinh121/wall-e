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
-- Table structure for table `t06_feedback`
--

DROP TABLE IF EXISTS `t06_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t06_feedback` (
  `C06_FEEDBACK_ID` int NOT NULL AUTO_INCREMENT,
  `c06_feedback_desc` text NOT NULL,
  `C06_CREATED_AT` datetime NOT NULL,
  `C06_UPDATED_AT` datetime DEFAULT NULL,
  `C06_ACTOR_ID` int NOT NULL,
  `C06_SEND_TO_USER` int NOT NULL,
  `C06_RATING` int NOT NULL,
  PRIMARY KEY (`C06_FEEDBACK_ID`),
  KEY `FK_T06_ACTOR` (`C06_ACTOR_ID`),
  KEY `FK_T06_COME` (`C06_SEND_TO_USER`),
  CONSTRAINT `FK_T06_ACTOR` FOREIGN KEY (`C06_ACTOR_ID`) REFERENCES `t14_user` (`C14_USER_ID`),
  CONSTRAINT `FK_T06_COME` FOREIGN KEY (`C06_SEND_TO_USER`) REFERENCES `t14_user` (`C14_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t06_feedback`
--

LOCK TABLES `t06_feedback` WRITE;
/*!40000 ALTER TABLE `t06_feedback` DISABLE KEYS */;
INSERT INTO `t06_feedback` VALUES (1,'Học sinh đã có thái độ học hành rất tích cực và chăm chỉ trong suốt tuần vừa qua.','2024-06-22 10:00:00',NULL,4,10,4),(2,'Học sinh đã đi học đúng giờ và không bỏ lỡ bất kỳ buổi nào trong tháng này.','2024-06-21 14:30:00',NULL,4,10,4),(3,'Học sinh đã đạt thành tích học tập xuất sắc và nổi bật trong kỳ kiểm tra vừa qua.','2024-06-20 09:15:00',NULL,4,10,5),(4,'Học sinh đã có nề nếp học tập rất tốt và luôn hoàn thành bài tập đầy đủ và đúng h.','2024-06-23 09:15:00',NULL,4,10,5),(5,'Thầy đã giảng dạy môn Toán rất chi tiết và dễ hiểu. Em rất hài lòng với sự giảng dạy của thầy.','2024-06-26 09:00:00',NULL,10,4,5),(6,'Cô đã giúp em tiến bộ rất nhiều trong việc viết văn và phát triển kỹ năng viết của em. Em cảm ơn cô rất nhiều!','2024-06-25 13:30:00',NULL,10,5,4),(7,'Thầy đã sử dụng nhiều ví dụ thực tế và bài toán phù hợp với khả năng của học sinh. Điều này giúp em hiểu bài rất nhanh.','2024-06-24 10:45:00',NULL,10,4,4),(8,'Cô đã hỗ trợ em rất nhiều trong việc phân tích và giải thích các tác phẩm văn học. Em rất cảm kích những giờ học với cô.','2024-06-23 11:15:00',NULL,10,5,5),(9,'Thầy đã tạo ra môi trường học tập thân thiện và khuyến khích các câu hỏi từ học sinh. Điều này giúp em tự tin hơn khi học môn Toán.','2024-06-22 14:00:00',NULL,10,4,4);
/*!40000 ALTER TABLE `t06_feedback` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-26 14:45:21
