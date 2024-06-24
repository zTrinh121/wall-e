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
-- Table structure for table `t12_apply_center`
--

DROP TABLE IF EXISTS `t12_apply_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t12_apply_center` (
  `C12_APPLY_ID` int NOT NULL AUTO_INCREMENT,
  `C12_TEACHER_ID` int NOT NULL,
  `C12_CENTER_ID` int NOT NULL,
  `C12_TITLE` varchar(255) NOT NULL,
  `c12_content` text,
  `C12_APPLY_STATUS` enum('Approved','Rejected','Wait_to_process') NOT NULL DEFAULT 'Wait_to_process',
  PRIMARY KEY (`C12_APPLY_ID`),
  UNIQUE KEY `CK_C12_UNQ` (`C12_TEACHER_ID`,`C12_CENTER_ID`),
  UNIQUE KEY `CK_T12_UNQ` (`C12_TEACHER_ID`,`C12_CENTER_ID`),
  KEY `FK_T12_CENTER` (`C12_CENTER_ID`),
  CONSTRAINT `FK_T12_CENTER` FOREIGN KEY (`C12_CENTER_ID`) REFERENCES `t03_center` (`C03_CENTER_ID`),
  CONSTRAINT `FK_T12_TEACHER` FOREIGN KEY (`C12_TEACHER_ID`) REFERENCES `t14_user` (`C14_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t12_apply_center`
--

LOCK TABLES `t12_apply_center` WRITE;
/*!40000 ALTER TABLE `t12_apply_center` DISABLE KEYS */;
INSERT INTO `t12_apply_center` VALUES (1,4,1,'\"Xin tham gia dạy môn Toán tại trung tâm.\"','\"Kính gửi Ban quản lý trung tâm, tôi là giáo viên có kinh nghiệm trong việc giảng dạy môn Toán. Tôi rất mong muốn có cơ hội được gắn bó và đóng góp vào sự phát triển chung của trung tâm. Xin cảm ơn sự xem xét của Ban.\"','Approved'),(2,5,1,'\"Xin tham gia dạy môn Văn tại trung tâm.\"','\"Kính gửi Ban quản lý trung tâm, tôi là giáo viên có tâm huyết với môn Văn và có kinh nghiệm giảng dạy lâu năm. Tôi mong muốn được góp phần vào việc phát triển học sinh trong lĩnh vực Văn học. Xin trân trọng đề nghị và mong nhận được sự phản hồi từ Ban.\"','Approved'),(3,6,1,'\"Xin tham gia dạy môn Lịch sử tại trung tâm.\"','\"Kính gửi Ban quản lý trung tâm, tôi là giáo viên chuyên dạy môn Lịch sử với sự đam mê và hiểu biết sâu rộng về lịch sử địa phương và quốc gia. Tôi mong muốn có cơ hội được chia sẻ kiến thức và kinh nghiệm với các em học sinh tại trung tâm. Xin chân thành cảm ơn.\"','Wait_to_process'),(4,7,1,'\"Xin tham gia dạy môn Hóa học tại trung tâm.\"','\"Kính gửi Ban quản lý trung tâm, tôi là giáo viên chuyên dạy môn Hóa học với mong muốn mang đến cho học sinh sự hiểu biết rõ ràng và ứng dụng thực tiễn của môn học. Tôi rất mong muốn được tham gia vào đội ngũ giảng dạy của trung tâm. Xin trân trọng đề nghị và mong nhận được sự hồi âm từ Ban.\"','Wait_to_process');
/*!40000 ALTER TABLE `t12_apply_center` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-25  2:18:50
