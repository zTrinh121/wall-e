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
-- Table structure for table `t20_individual_notification`
--

DROP TABLE IF EXISTS `t20_individual_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t20_individual_notification` (
  `C20_ID` int NOT NULL AUTO_INCREMENT,
  `C20_TITLE` varchar(255) NOT NULL,
  `c20_content` text NOT NULL,
  `C20_ACTOR_ID` int NOT NULL,
  `C20_SEND_TO_USER` varchar(255) NOT NULL,
  `C20_HAS_SEEN` bit(1) DEFAULT b'0',
  `C20_SEEN_TIME` datetime DEFAULT NULL,
  `c20_create_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `c20_update_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`C20_ID`),
  UNIQUE KEY `UNIQUE_ACTOR_SENDTO_CREATEDATE` (`C20_ACTOR_ID`,`C20_SEND_TO_USER`,`c20_create_at`),
  KEY `FK_T20_T14` (`C20_SEND_TO_USER`),
  CONSTRAINT `FK_T20_T14` FOREIGN KEY (`C20_SEND_TO_USER`) REFERENCES `t14_user` (`C14_USERNAME`),
  CONSTRAINT `FK_T20_T14_ID` FOREIGN KEY (`C20_ACTOR_ID`) REFERENCES `t14_user` (`C14_USER_ID`),
  CONSTRAINT `CHECK_UPDATE_AFTER_CREATE` CHECK ((`c20_update_at` > `c20_create_at`))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t20_individual_notification`
--

LOCK TABLES `t20_individual_notification` WRITE;
/*!40000 ALTER TABLE `t20_individual_notification` DISABLE KEYS */;
INSERT INTO `t20_individual_notification` VALUES (1,'Thông báo cập nhật mật khẩu bảo mật','Xin chào DungTL,\n\nChúng tôi đã nhận thấy một hoạt động đáng ngờ trên tài khoản của bạn. Để đảm bảo an toàn, chúng tôi đã thay đổi mật khẩu cho bạn. Bạn có thể sử dụng mật khẩu mới sau khi đăng nhập lại vào hệ thống.\n\nNếu bạn không thực hiện thay đổi này, vui lòng liên hệ với bộ phận hỗ trợ ngay lập tức.\n\nTrân trọng,\nHệ thống',1,'DungTL',_binary '\0',NULL,'2024-07-08 10:30:00',NULL),(2,'Thông báo cập nhật mật khẩu bảo mật','Xin chào AnDL,\n\nChúng tôi đã nhận thấy một hoạt động đáng ngờ trên tài khoản của bạn. Để đảm bảo an toàn, chúng tôi đã thay đổi mật khẩu cho bạn. Bạn có thể sử dụng mật khẩu mới sau khi đăng nhập lại vào hệ thống.\n\nNếu bạn không thực hiện thay đổi này, vui lòng liên hệ với bộ phận hỗ trợ ngay lập tức.\n\nTrân trọng,\nHệ thống',1,'AnDL',_binary '','2024-07-23 07:30:12','2024-07-08 10:30:00',NULL),(3,'Thông báo cập nhật mật khẩu bảo mật','Kính gửi DungTL,\n\nChúng tôi xin thông báo rằng chính sách bảo mật của chúng tôi đã được cập nhật. Những thay đổi này bao gồm các điều khoản mới về bảo vệ dữ liệu và quyền riêng tư của người dùng. Vui lòng xem chi tiết trong trang cập nhật chính sách trên trang web của chúng tôi.\n\nChân thành cảm ơn sự hiểu biết và hợp tác của bạn.\n\nTrân trọng,\nHệ thống',1,'DungTL',_binary '','2024-07-23 22:45:12','2024-07-12 11:30:00',NULL),(4,'Thông báo cập nhật mật khẩu bảo mật','Kính gửi AnDL,\n\nChúng tôi xin thông báo rằng chính sách bảo mật của chúng tôi đã được cập nhật. Những thay đổi này bao gồm các điều khoản mới về bảo vệ dữ liệu và quyền riêng tư của người dùng. Vui lòng xem chi tiết trong trang cập nhật chính sách trên trang web của chúng tôi.\n\nChân thành cảm ơn sự hiểu biết và hợp tác của bạn.\n\nTrân trọng,\nHệ thống',1,'AnDL',_binary '','2024-07-23 22:45:12','2024-07-12 11:30:00',NULL),(5,'Thông báo về việc phát hiện hoạt động đáng ngờ trên tài khoản','Xin chào DungTL,\n\nHệ thống đã phát hiện một hoạt động không thường xuyên trên tài khoản của bạn vào lúc [thời gian]. Để đảm bảo an toàn, chúng tôi đã tạm khóa tài khoản của bạn để điều tra thêm về sự cố này.\n\nVui lòng liên hệ với bộ phận hỗ trợ của chúng tôi để được hỗ trợ thêm và mở khóa tài khoản.\n\nChân thành,\n[Tên tổ chức]',1,'DungTL',_binary '','2024-09-11 11:23:12','2024-08-12 12:45:20',NULL),(6,'Thông báo về việc phát hiện hoạt động đáng ngờ trên tài khoản','Xin chào AnDL,\n\nHệ thống đã phát hiện một hoạt động không thường xuyên trên tài khoản của bạn vào lúc [thời gian]. Để đảm bảo an toàn, chúng tôi đã tạm khóa tài khoản của bạn để điều tra thêm về sự cố này.\n\nVui lòng liên hệ với bộ phận hỗ trợ của chúng tôi để được hỗ trợ thêm và mở khóa tài khoản.\n\nChân thành,\n[Tên tổ chức]',1,'AnDL',_binary '','2024-09-11 11:23:12','2024-08-12 12:45:20',NULL),(8,'Thông báo Lịch nghỉ lễ','Kính gửi các Thầy/Cô,\n\nTrung tâm xin thông báo về lịch nghỉ lễ nhân dịp Lễ 30/4 - 1/5, thời gian nghỉ sẽ từ ngày 30/4 đến ngày 3/5. Trong thời gian này, trung tâm sẽ không hoạt động.\n\nMong các Thầy/Cô thông báo đến học viên và sắp xếp công việc cá nhân phù hợp. Xin cảm ơn và chúc mọi người có một kỳ nghỉ vui vẻ!\n\nTrân trọng,\nJenny Tú Anh\nQuản lí trung tâm\n28/4/2024\n',2,'DungTL',_binary '','2024-09-11 11:23:12','2024-08-12 12:45:20',NULL),(9,'Thông báo Nâng cấp cơ sở vật chất','Kính gửi các Thầy/Cô,\n\nTrung tâm xin trân trọng thông báo về việc nâng cấp cơ sở vật chất để cải thiện chất lượng dịch vụ. Cụ thể, chúng tôi sẽ tiến hành nâng cấp [mô tả công trình] trong thời gian từ ngày [ngày bắt đầu] đến ngày [ngày hoàn thành].\n\nChúng tôi đã sắp xếp công việc dạy học và hoạt động của các lớp học trong thời gian này sao cho không gây ảnh hưởng lớn đến quá trình giảng dạy. Mong các Thầy/Cô thông báo đến học viên và hỗ trợ chúng tôi trong quá trình nâng cấp này.\n\nXin chân thành cảm ơn sự hợp tác của các Thầy/Cô.\n\nTrân trọng,\nJenny Tú Anh\nQuản lí trung tâm\n22/6/2024\n',2,'DungTL',_binary '','2024-06-26 11:23:12','2024-06-22 12:45:20',NULL),(10,'Thông báo Thay đổi lịch giảng dạy','Kính gửi các Thầy/Cô,\n\nTrung tâm xin thông báo về việc điều chỉnh lịch giảng dạy do công tác nâng cấp cơ sở vật chất. Cụ thể, các lớp học sẽ có sự điều chỉnh về thời gian và địa điểm như sau:\n\n- [Mô tả sự thay đổi cho từng lớp]\n\nXin các Thầy/Cô thông báo đến học viên và sắp xếp công việc giảng dạy phù hợp. Mong nhận được sự hỗ trợ và chia sẻ từ các Thầy/Cô.\n\nTrân trọng,\nJenny Tú Anh\nQuản lí trung tâm\n10/8/2024\n',2,'DungTL',_binary '','2024-08-12 11:23:12','2024-08-10 12:45:20',NULL),(11,'Thông báo Nộp học phí','Kính gửi các học sinh,\n\nTrung tâm nhắc nhở các bạn về việc nộp học phí cho kỳ học học kì hè. Hạn chót nộp học phí được xác định là ngày 30/8/2024. Xin các bạn chú ý hoàn thành việc nộp học phí đúng thời hạn để không bị gián đoạn trong quá trình học tập.\n\nCác bạn có thể nộp học phí tại [địa điểm nộp], hoặc liên hệ với nhân viên văn phòng để biết thêm thông tin chi tiết.\n\nTrân trọng,\n\nJenny Tú Anh\nQuản lí trung tâm\n25/8/2024\n\n',2,'AnDL',_binary '\0',NULL,'2024-08-25 07:45:20',NULL),(12,'Thông báo Đóng tiền bảo hiểm','Kính gửi các học sinh,\n\nTrung tâm nhắc nhở các bạn về việc đóng tiền bảo hiểm cho kỳ học học kì hè. Hạn chót đóng tiền bảo hiểm là ngày [ngày hết hạn]. Đây là một yêu cầu quan trọng để bảo đảm quyền lợi bảo hiểm của các bạn trong quá trình học tập tại trung tâm.\n\nCác bạn vui lòng thực hiện việc đóng tiền bảo hiểm tại trung tâm y tế quận Hải Châu, hoặc liên hệ với nhân viên văn phòng để được hỗ trợ thêm thông tin.\n\nTrân trọng,\n\nJenny Tú Anh\nQuản lí trung tấm\n10/6/2024\n\n\n',2,'AnDL',_binary '','2024-06-11 06:30:20','2024-06-10 11:23:20',NULL);
/*!40000 ALTER TABLE `t20_individual_notification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-25  2:18:49
