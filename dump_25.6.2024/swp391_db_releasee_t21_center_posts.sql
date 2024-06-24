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
-- Table structure for table `t21_center_posts`
--

DROP TABLE IF EXISTS `t21_center_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t21_center_posts` (
  `C21_ID` int NOT NULL AUTO_INCREMENT,
  `C21_TITLE` varchar(255) NOT NULL,
  `c21_content` varchar(255) NOT NULL,
  `c21_post_status` tinyint NOT NULL,
  `C21_FILE_URL` varchar(255) DEFAULT NULL,
  `C21_CREATED_AT` datetime NOT NULL,
  `C21_UPDATED_AT` datetime DEFAULT NULL,
  `C21_SEND_TO_CENTER` varchar(255) NOT NULL,
  PRIMARY KEY (`C21_ID`),
  KEY `FK_T21_CENTER` (`C21_SEND_TO_CENTER`),
  CONSTRAINT `FK_T21_CENTER` FOREIGN KEY (`C21_SEND_TO_CENTER`) REFERENCES `t03_center` (`C03_CENTER_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t21_center_posts`
--

LOCK TABLES `t21_center_posts` WRITE;
/*!40000 ALTER TABLE `t21_center_posts` DISABLE KEYS */;
INSERT INTO `t21_center_posts` VALUES (1,'Lớp học Toán cấp 3','Khóa học Toán cấp 3 với giáo viên có kinh nghiệm giảng dạy nhiệt tình. Đăng ký ngay để nâng cao kiến thức và chuẩn bị cho kì thi quan trọng!',1,NULL,'2024-06-25 00:28:08',NULL,'NTT'),(2,'Khóa ôn thi Lý cấp 3','Khóa ôn thi Lý cấp 3 với phương pháp dạy hiệu quả, đảm bảo cung cấp kiến thức toàn diện. Đăng ký ngay để chuẩn bị cho kì thi tốt nghiệp!',1,NULL,'2024-06-25 00:28:08',NULL,'NTT'),(3,'Lớp học Hóa học cấp 3','Khóa học Hóa học cấp 3 giúp học sinh hiểu sâu về các khái niệm và phản ứng hóa học. Đăng ký ngay để nâng cao điểm số và đạt thành tích cao!',1,NULL,'2024-06-25 00:28:08',NULL,'NTT'),(4,'Khóa ôn thi Địa lý cấp 3','Khóa ôn thi Địa lý cấp 3 với các bài giảng chi tiết và đầy đủ, giúp học sinh tự tin với kì thi sắp tới. Đăng ký ngay để có một kì thi thành công!',0,NULL,'2024-06-25 00:28:08',NULL,'NTT'),(5,'Lớp học Văn học cấp 3','Khóa học Văn học cấp 3 giúp học sinh hiểu rõ về các tác phẩm và phương pháp phân tích văn học. Đăng ký ngay để phát triển kỹ năng viết và làm bài thi tốt hơn!',0,NULL,'2024-06-25 00:28:08',NULL,'NTT');
/*!40000 ALTER TABLE `t21_center_posts` ENABLE KEYS */;
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
