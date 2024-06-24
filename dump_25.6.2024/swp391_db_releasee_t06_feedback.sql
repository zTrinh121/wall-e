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
  `C06_SEND_TO_USER` varchar(255) NOT NULL,
  `C06_COURSE_ID` int NOT NULL,
  PRIMARY KEY (`C06_FEEDBACK_ID`),
  KEY `FK_T06_ACTOR` (`C06_ACTOR_ID`),
  KEY `FK_T06_COURSE` (`C06_COURSE_ID`),
  KEY `FK_T06_TO` (`C06_SEND_TO_USER`),
  CONSTRAINT `FK_T06_ACTOR` FOREIGN KEY (`C06_ACTOR_ID`) REFERENCES `t14_user` (`C14_USER_ID`),
  CONSTRAINT `FK_T06_COURSE` FOREIGN KEY (`C06_COURSE_ID`) REFERENCES `t01_course` (`C01_COURSE_ID`),
  CONSTRAINT `FK_T06_TO` FOREIGN KEY (`C06_SEND_TO_USER`) REFERENCES `t14_user` (`C14_USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t06_feedback`
--

LOCK TABLES `t06_feedback` WRITE;
/*!40000 ALTER TABLE `t06_feedback` DISABLE KEYS */;
INSERT INTO `t06_feedback` VALUES (1,'\"Thưa thầy/cô, con tôi đã rất hứng thú và tiến bộ trong môn Toán từ khi bắt đầu khóa học. Con rất cảm ơn thầy/cô vì sự cố gắng và cách dạy dựa trên từng nhu cầu của từng học sinh. Chúng tôi hy vọng con sẽ tiếp tục được học cùng thầy/cô trong những năm tiếp theo.\"','2024-12-26 00:00:00',NULL,8,'DungTL',1),(2,'\"Xin chào thầy/cô, con tôi đã rất thích môn học Toán lớp 11 dưới sự hướng dẫn của thầy/cô. Thầy/cô đã giúp con xây dựng nền tảng vững chắc và tự tin hơn trong việc giải các bài toán phức tạp. Chúng tôi cảm thấy may mắn khi con có một người thầy/cô như thầy/cô.\"','2025-01-10 00:00:00',NULL,8,'DungTL',1),(3,'\"Thưa thầy/cô, em rất cảm ơn thầy/cô vì đã giảng dạy môn Toán một cách rõ ràng và chi tiết. Nhờ vào những giải thích của thầy/cô, em đã hiểu được nhiều khái niệm khó và có thể áp dụng vào giải các bài toán phức tạp. Em cảm thấy tự tin hơn và mong muốn tiếp tục học tập dưới sự hướng dẫn của thầy/cô.\"','2024-09-17 00:00:00',NULL,10,'DungTL',1),(4,'\"Xin chào thầy/cô, em thực sự thích môn Toán lớp 11 dưới sự dạy dỗ của thầy/cô. Thầy/cô đã tạo ra môi trường học tập thân thiện và động viên em luôn cố gắng hết mình. Nhờ vào những bài giảng sinh động và phương pháp giảng dạy hấp dẫn của thầy/cô, em đã có những tiến bộ đáng kể. Em rất biết ơn và mong muốn học tốt hơn nữa.\"','2024-12-28 00:00:00',NULL,10,'DungTL',1),(5,'\"Kính gửi thầy/cô, chúng tôi muốn bày tỏ lòng biết ơn đến thầy/cô vì sự nhiệt tình và tâm huyết trong việc giảng dạy môn Toán lớp 11. Con tôi đã có sự tiến bộ đáng kể từ khi tham gia khóa học và đã bắt đầu có những thành tích khả quan. Chúng tôi mong muốn sự hỗ trợ và sự dẫn dắt tiếp theo từ thầy/cô.\"','2025-02-14 00:00:00',NULL,8,'DungTL',1),(6,'\"Chào các em, tôi rất vui mừng thấy sự tiến bộ của các em trong môn Toán gần đây. Các em đã làm rất tốt trong việc nắm bắt các khái niệm và áp dụng chúng vào giải quyết các bài toán. Tôi hy vọng các em sẽ tiếp tục cố gắng và không ngừng học hỏi để phát triển thêm nữa.\"','2024-12-30 00:00:00',NULL,4,'AnDL',1),(7,'\"Xin chào các em, tôi muốn gửi lời khen đến các em về sự nỗ lực và sự quan tâm mà các em đã dành cho môn học Toán. Tôi thấy các em đã có những tiến bộ đáng kể và đây là điều rất đáng mừng. Hãy tiếp tục giữ vững phong độ và luôn học hỏi để phát triển bản thân nhé.\"','2025-05-27 00:00:00',NULL,4,'AnDL',1);
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

-- Dump completed on 2024-06-25  2:18:50
