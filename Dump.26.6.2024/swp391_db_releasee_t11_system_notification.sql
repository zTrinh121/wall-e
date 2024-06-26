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
-- Table structure for table `t11_system_notification`
--

DROP TABLE IF EXISTS `t11_system_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t11_system_notification` (
  `C11_ID` int NOT NULL AUTO_INCREMENT,
  `C11_TITLE` varchar(255) NOT NULL,
  `c11_content` varchar(255) NOT NULL,
  `C11_CREATED_AT` datetime NOT NULL,
  `C11_UPDATED_AT` datetime DEFAULT NULL,
  PRIMARY KEY (`C11_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t11_system_notification`
--

LOCK TABLES `t11_system_notification` WRITE;
/*!40000 ALTER TABLE `t11_system_notification` DISABLE KEYS */;
INSERT INTO `t11_system_notification` VALUES (1,'Thông báo bảo trì hệ thống hàng tuần','Hệ thống sẽ được bảo trì hàng tuần vào các ngày thứ Năm từ 1 giờ sáng đến 5 giờ sáng.','2024-05-12 18:21:06',NULL),(2,'Thông báo bảo trì hệ thống tháng 7/2024','Hệ thống sẽ được bảo trì vào ngày 5/7/2024 từ 8 giờ sáng đến 12 giờ trưa.','2024-02-24 23:21:06',NULL),(3,'Thông báo bảo trì hệ thống tháng 8/2024','Hệ thống sẽ được bảo trì vào ngày 10/8/2024 từ 10 giờ sáng đến 2 giờ chiều.','2024-03-11 08:21:06',NULL),(4,'Thông báo bảo trì hệ thống tháng 9/2024','Hệ thống sẽ được bảo trì vào ngày 15/9/2024 từ 9 giờ sáng đến 1 giờ chiều.','2024-04-24 23:21:06',NULL),(5,'Thông báo khai giảng năm học mới','Khai giảng năm học mới sẽ diễn ra vào ngày 1/9/2024. Các trung tâm vui lòng chuẩn bị kế hoạch điều chỉnh lịch dạy thêm hợp lí cho học sinh và phụ huynh.','2024-05-27 16:21:06',NULL),(6,'Thông báo kết quả học kỳ','Kết quả học kỳ sẽ được công bố vào ngày 20/8/2024. Học sinh và phụ huynh có thể kiểm tra kết quả trên hệ thống.','2024-01-24 23:21:06',NULL),(7,'Thông báo lịch nghỉ Tết Nguyên Đán','Hệ thống sẽ nghỉ Tết Nguyên Đán từ ngày 30/1 đến ngày 5/2/2025. Các trung tâm vui lòng cập nhật lịch học cho học viên.','2024-02-16 09:21:06',NULL),(8,'Thông báo về cuộc thi Toán học quốc gia','Hệ thống sẽ tổ chức cuộc thi Toán học quốc gia vào ngày 10/8/2024. Các trung tâm có thể đăng ký tham gia để học sinh thử sức.','2024-03-24 23:21:06',NULL),(9,'Thông báo về buổi hội thảo chuyên đề về giáo dục','Sẽ có buổi hội thảo chuyên đề về giáo dục vào ngày 10/9/2024. Các giáo viên có thể đăng ký tham gia để nâng cao kiến thức.','2024-04-11 12:21:06',NULL),(10,'Thông báo về khóa học mới về Lập trình Python','Khóa học mới về Lập trình Python sẽ khai giảng vào ngày 1/8/2024. Đăng ký ngay để nhận được ưu đãi đặc biệt.','2024-04-09 15:21:06',NULL);
/*!40000 ALTER TABLE `t11_system_notification` ENABLE KEYS */;
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
