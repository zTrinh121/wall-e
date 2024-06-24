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
  `c03_center_name` varchar(255) NOT NULL,
  `c03_center_desc` varchar(255) DEFAULT NULL,
  `c03_center_address` varchar(255) NOT NULL,
  `c03_center_phone` varchar(255) NOT NULL,
  `c03_center_email` varchar(255) NOT NULL,
  `c03_center_regulations` text,
  `C03_CREATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `c03_center_status` tinyint NOT NULL,
  `c03_image_path` varchar(255) DEFAULT NULL,
  `C03_MANAGER_ID` int NOT NULL,
  `c03_cloudinary_image_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`C03_CENTER_ID`),
  UNIQUE KEY `C03_CENTER_CODE` (`C03_CENTER_CODE`),
  KEY `FK_CENTER_MANAGER` (`C03_MANAGER_ID`),
  CONSTRAINT `FK_CENTER_MANAGER` FOREIGN KEY (`C03_MANAGER_ID`) REFERENCES `t14_user` (`C14_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t03_center`
--

LOCK TABLES `t03_center` WRITE;
/*!40000 ALTER TABLE `t03_center` DISABLE KEYS */;
INSERT INTO `t03_center` VALUES (1,'NTT','Nguyễn Trường Tộ','Luyện thi trung học phổ thông quốc gia','Phường Mỹ An, Quận Ngũ Hành Sơn, TP. Đà Nẵng','0905732532','ntt@edu.vn','Các điều lệ của các trung tâm luyện thi bao gồm quy định về đăng ký và tham gia lớp học, quản lý hành vi của học viên, chế độ giảng dạy và tổ chức thi cử, đảm bảo an toàn và bảo mật thông tin cá nhân, cùng với các chính sách về thanh toán và hủy bỏ dịch vụ. Quy định này giúp đảm bảo sự trật tự, hiệu quả và công bằng trong hoạt động giáo dục của trung tâm.','2016-08-03 17:00:00',1,NULL,2,NULL),(2,'VUS','Trung tâm luyện thi VUS','Luyện thi phổ thông','Phường Hòa Cường Bắc, Quận Hải Châu, TP. Đà Nẵng','0905732532','vus@edu.vn','Các điều lệ của các trung tâm luyện thi bao gồm quy định về đăng ký và tham gia lớp học, quản lý hành vi của học viên, chế độ giảng dạy và tổ chức thi cử, đảm bảo an toàn và bảo mật thông tin cá nhân, cùng với các chính sách về thanh toán và hủy bỏ dịch vụ. Quy định này giúp đảm bảo sự trật tự, hiệu quả và công bằng trong hoạt động giáo dục của trung tâm.','2018-10-15 17:00:00',1,NULL,2,NULL),(3,'DHD','Đặng Hoàng Dư','Luyện thi trung học cơ sở','Phường Thanh Bình, Quận Hải Châu, TP. Đà Nẵng','0938653734','dhd@edu.vn','Các điều lệ của các trung tâm luyện thi bao gồm quy định về đăng ký và tham gia lớp học, quản lý hành vi của học viên, chế độ giảng dạy và tổ chức thi cử, đảm bảo an toàn và bảo mật thông tin cá nhân, cùng với các chính sách về thanh toán và hủy bỏ dịch vụ. Quy định này giúp đảm bảo sự trật tự, hiệu quả và công bằng trong hoạt động giáo dục của trung tâm.','2020-11-14 17:00:00',1,NULL,3,NULL);
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

-- Dump completed on 2024-06-25  2:18:50
