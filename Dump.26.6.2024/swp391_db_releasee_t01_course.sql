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
-- Table structure for table `t01_course`
--

DROP TABLE IF EXISTS `t01_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t01_course` (
  `C01_COURSE_ID` int NOT NULL AUTO_INCREMENT,
  `c01_course_name` varchar(255) NOT NULL,
  `C01_COURSE_CODE` varchar(255) NOT NULL,
  `c01_course_desc` text NOT NULL,
  `c01_course_start_date` datetime(6) NOT NULL,
  `c01_course_end_date` datetime(6) NOT NULL,
  `C01_AMOUNT_OF_STUDENTS` int NOT NULL,
  `C01_COURSE_FEE` float NOT NULL,
  `C01_CENTER_ID` int NOT NULL,
  `C01_TEACHER_ID` int NOT NULL,
  `C01_SUBJECT_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`C01_COURSE_ID`),
  UNIQUE KEY `C01_COURSE_CODE` (`C01_COURSE_CODE`),
  KEY `FK_COURSE_CENTER` (`C01_CENTER_ID`),
  KEY `FK_COURSE_TEACHER` (`C01_TEACHER_ID`),
  CONSTRAINT `FK_COURSE_CENTER` FOREIGN KEY (`C01_CENTER_ID`) REFERENCES `t03_center` (`C03_CENTER_ID`),
  CONSTRAINT `FK_COURSE_TEACHER` FOREIGN KEY (`C01_TEACHER_ID`) REFERENCES `t14_user` (`C14_USER_ID`),
  CONSTRAINT `T01_CK_DATE` CHECK ((`c01_course_end_date` >= `c01_course_start_date`))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t01_course`
--

LOCK TABLES `t01_course` WRITE;
/*!40000 ALTER TABLE `t01_course` DISABLE KEYS */;
INSERT INTO `t01_course` VALUES (1,'Hình học lớp 11','Toan11','Khóa học Toán 11 là chương trình giáo dục chuyên sâu dành cho học sinh lớp 11, tập trung vào các nội dung chính của môn Toán gồm đại số, hình học và giải tích. Học sinh sẽ được tiếp cận với các khái niệm và phương pháp giải toán phức tạp, nâng cao kỹ năng phân tích và suy luận toán học. Khóa học giúp học sinh chuẩn bị tốt cho kỳ thi cuối kỳ và xây dựng nền tảng vững chắc cho học tập và nghiên cứu sau này trong lĩnh vực toán học.','2024-06-01 00:00:00.000000','2025-05-30 00:00:00.000000',20,250000,1,4,'Toán 11'),(2,'Ngữ văn lớp 11','Van11','Khóa học Ngữ văn lớp 11 tập trung vào việc khám phá và hiểu biết sâu rộng về văn học Việt Nam và thế giới. Học sinh sẽ được giới thiệu đến các tác phẩm văn học kinh điển, phê bình văn học và các thể loại văn học đa dạng. Khóa học cũng nhấn mạnh vào phát triển kỹ năng phân tích, diễn giải và sáng tạo văn học, giúp học sinh rèn luyện khả năng suy nghĩ logic và khả năng viết lách sâu sắc. Khóa học Ngữ văn lớp 11 là nền tảng quan trọng giúp học sinh phát triển cả về tri thức và kỹ năng sử dụng ngôn ngữ trong giao tiếp và học tập.','2024-06-01 00:00:00.000000','2025-05-30 00:00:00.000000',20,250000,1,5,'Văn 11');
/*!40000 ALTER TABLE `t01_course` ENABLE KEYS */;
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
