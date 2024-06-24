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
-- Table structure for table `t14_user`
--

DROP TABLE IF EXISTS `t14_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t14_user` (
  `C14_USER_ID` int NOT NULL AUTO_INCREMENT,
  `C14_USERNAME` varchar(255) NOT NULL,
  `C14_USER_PASSWORD` varchar(255) NOT NULL,
  `c14_acc_status` int NOT NULL,
  `C14_USER_CODE` varchar(255) NOT NULL,
  `C14_NAME` varchar(255) DEFAULT NULL,
  `c14_user_phone` varchar(255) NOT NULL,
  `c14_user_address` varchar(255) DEFAULT NULL,
  `c14_user_dob` datetime(6) DEFAULT NULL,
  `c14_user_gender` int DEFAULT NULL,
  `c14_user_email` varchar(255) NOT NULL,
  `C14_VERIFICATION_CODE` varchar(255) DEFAULT (NULL),
  `C14_PROFILE_IMAGE` varchar(255) DEFAULT (_utf8mb4'file path'),
  `C14_ROLE_ID` int NOT NULL,
  `C14_PARENT_ID` int DEFAULT NULL,
  `c14_cloudinary_image_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`C14_USER_ID`),
  UNIQUE KEY `C14_USER_USERNAME` (`C14_USERNAME`),
  UNIQUE KEY `C14_USER_CODE` (`C14_USER_CODE`),
  UNIQUE KEY `C14_USER_EMAIL` (`c14_user_email`),
  KEY `FK_USER_ROLE` (`C14_ROLE_ID`),
  KEY `FK_USER_PARENT` (`C14_PARENT_ID`),
  CONSTRAINT `FK_USER_PARENT` FOREIGN KEY (`C14_PARENT_ID`) REFERENCES `t14_user` (`C14_USER_ID`),
  CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`C14_ROLE_ID`) REFERENCES `t05_role` (`C05_ROLE_ID`),
  CONSTRAINT `CHK_USER_CODE` CHECK ((`C14_USER_CODE` like _utf8mb4'USER%'))
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t14_user`
--

LOCK TABLES `t14_user` WRITE;
/*!40000 ALTER TABLE `t14_user` DISABLE KEYS */;
INSERT INTO `t14_user` VALUES (1,'admin','$2a$10$w5staAEPxII9XjauzO4lYegDvD.Z.OF0tOBCLZTCmIwDojMKWFMMq',1,'USER05d883fe',NULL,'0905986456',NULL,NULL,1,'mmd83380@vogco.com',NULL,NULL,5,NULL,NULL),(2,'Jenny','$2a$10$QzSLDZK33mRh/jYCxz9amukSVD7nZT3M42LAL9ThXGluXRtS1BfDG',1,'USER4f7902b3','Jenny Tú Anh','0905732532','Phường An Hải Đông, Quận Sơn Trà, TP.Đà Nẵng','1972-12-15 00:00:00.000000',0,'pwg96136@ilebi.com',NULL,NULL,4,NULL,NULL),(3,'ChienND','$2a$10$kAoG8o0GmIxP4ofbWOHje.TcynjSLLtTqExPWNGvTvuHgn5zUwRRu',1,'USER980730de','Nguyễn Danh Chiến','0938653734','Phường Thanh Bình, Quận Hải Châu, TP.Đà Nẵng','1970-06-05 00:00:00.000000',1,'amd18158@doolk.com',NULL,NULL,4,NULL,NULL),(4,'DungTL','$2a$10$Oou/vB90dXrWsvjjMxsG/u6DwpVFC6QGt8VwCGLvSp3li3wthkpNe',1,'USERc5f7a13f','Trần Lê Dung','0905873847','Phường Hòa Hải, Quận Ngũ Hành Sơn, TP. Đà Nẵng','1982-07-08 00:00:00.000000',0,'olx48034@ilebi.com',NULL,NULL,3,NULL,NULL),(5,'DuyenNH','$2a$10$zcmh16tHjWKicIcay63Qa.a.sqR5TBV/GtupW8UNHMk7WIrS3sfYy',1,'USER6aca5da9','Nguyễn Hồng Duyên','0938875039','Phường Thọ Quang, Quận Sơn Trà, TP. Đà Nẵng','1985-05-02 00:00:00.000000',0,'rtl12222@doolk.com',NULL,NULL,3,NULL,NULL),(6,'HaDN','$2a$10$.Ygu4452h79sQQ.JW512fuYP1t.IyY20naawdtmtJRV5G0JKFJxoq',1,'USERab3b5b9a','Đỗ Ngọc Hà','0935754082','Phường Thọ Quang, Quận Hoàng Mai, TP. Hà Nội','1970-10-11 00:00:00.000000',1,'pjz40717@ilebi.com',NULL,NULL,3,NULL,NULL),(7,'HieuPT','$2a$10$j/zhxXPU/OvmQuo0.oVTU.FjpW0GkqNPGsT8PT6DpHk4lSdwsgSo.',1,'USER4c5a16af','Phạm Trọng Hiếu','0874238091','Xã Triệu Ái, Huyện Triệu Phong, Tỉnh Quảng Trị','1975-04-30 00:00:00.000000',1,'mxo46921@ilebi.com',NULL,NULL,3,NULL,NULL),(8,'HungDV','$2a$10$t8/csN1RZ7.ohhs0DVtBmOgqkhCu5DPZxXz1QRIY8DLwbW/FhGeW.',1,'USERd506b7b2','Đặng Việt Hùng','0824057345','Phường Hòa Hải, Quận Ngũ Hành Sơn, TP. Đà Nẵng','1970-08-17 00:00:00.000000',1,'itf97500@ilebi.com',NULL,NULL,2,NULL,NULL),(9,'HuongLTT','$2a$10$iLj7KwWcq2XSko9vluk00udD3fQmvM07kWeiRbeyi1PX0hQDqMvJi',1,'USERf77963b7','Lê Thị Thu Hương','0826234038','Phường Đa Kao, Quận 1, TP. Đà Nẵng','1976-04-05 00:00:00.000000',0,'hcy65748@doolk.com',NULL,NULL,2,NULL,NULL),(10,'AnDL','$2a$10$XZw4pfQ8PJLKBiC7luqJceav1en92mnqXLiK7KxuKFheOX/Zzq7mm',1,'USER4a321cff','Đặng Lâm An','0768953972','Phường Hòa Hải, Quận Ngũ Hành Sơn, TP. Đà Nẵng','2008-09-07 00:00:00.000000',0,'psi18719@doolk.com',NULL,NULL,1,8,NULL),(11,'KhiemLD','$2a$10$NpY6RlFaXBFQhrsv5YctR.lwLYSDqc7YdxBFg84Q.rUjPvmOQklm2',1,'USER1b5c9868','Lê Duy Khiêm','0963671281','Phường Đa Kao, Quận 1, TP. Đà Nẵng','2008-12-17 00:00:00.000000',1,'hob45499@ilebi.com',NULL,NULL,1,9,NULL),(12,'AnhNNT','$2a$10$Sv5UQHhxAF9F3tmyR9kASu8iQ08b1pmDQyFeLyvYzCfpHFnshBC.O',1,'USER903997e1','Nguyễn Ngọc Trâm Anh','0926864963','Phường An Khê, Quận Thanh Khê, TP. Đà Nẵng','2008-11-15 00:00:00.000000',0,'hum24544@doolk.com',NULL,NULL,1,NULL,NULL),(13,'ChiNPL','$2a$10$ETU2UngK8gVrdw3GPZ5b4.bPxDtTyW.MRBRe1Yyw8aSFDzTsprJ/e',1,'USER7bc51764','Nguyễn Phùng Linh Chi','0793722374','Phường Hòa Xuân, Quận Cẩm Lệ, TP. Đà Nẵng','2008-09-05 00:00:00.000000',0,'skn66295@ilebi.com',NULL,NULL,1,NULL,NULL),(14,'HoangNH','$2a$10$zEGEvCvbrgXB/6v/9NXC1Oax3uCmqiHXB4toOIDlMz5BYvEiofE7.',1,'USER425b2c97','Nguyễn Huy Hoàng','0787323974','Phường An Khê, Quận Thanh Khê, TP. Đà Nẵng','2008-08-18 00:00:00.000000',1,'ffb46087@vogco.com',NULL,NULL,1,NULL,NULL),(15,'HuyVD','$2a$10$vpJY6VQxUWVS7cz/bh4eb.l96wKRuksPgPqyyYpc/SCMoHtnh4P92',1,'USER89019f64','Vũ Đức Huy','0789583296','Phường An Hải Bắc, Quận Sơn Trà, TP. Đà Nẵng','2008-12-14 00:00:00.000000',1,'gwm84648@ilebi.com',NULL,NULL,1,NULL,NULL),(16,'LyHH','$2a$10$0TQI5db0zBPPAfSbv0LUa.3tRRyUy3kqXOgVrQy.uPy..AbW.v6D6',1,'USER7276a78b','Hoàng Hương Ly','0862863861','Phường Thanh Khê Đông, Quận Thanh Khê, TP. Đà Nẵng','2008-07-18 00:00:00.000000',0,'yaz31302@vogco.com',NULL,NULL,1,NULL,NULL),(17,'KhueNM','$2a$10$AHNqm45nxki4P.aNvLqyz.uW4MWAf2jeFCeuvBOL9UeAqpCdFLEqi',1,'USER26f0e121','Nguyễn Minh Khuê','0864973732','Phường Hòa Xuân, Quận Cẩm Lệ, TP. Đà Nẵng','2008-09-14 00:00:00.000000',0,'eqz52643@doolk.com',NULL,NULL,1,NULL,NULL),(18,'NamNVT','$2a$10$L/Gebv6TDHCoXi/QGi8v/eAIydoNirraTDpc9OFVrJpmRMKplo9Uq',1,'USERd585d278','Nguyễn Vũ Tiến Nam','0897725842','Phường An Hải Bắc, Quận Sơn Trà, TP. Đà Nẵng','2008-10-15 00:00:00.000000',1,'niw78748@vogco.com',NULL,NULL,1,NULL,NULL),(19,'TuanLM','$2a$10$kqG11e17KaUovPzhm5Iy4uEzZRi1Yq9Lj0W1Pei66b.ApoE4dBMKy',1,'USER9f808268','Lê Minh Tuấn','0834983836','Phường Hòa Khánh Bắc, Quận Liên Chiểu, TP. Đà Nẵng','2008-11-25 00:00:00.000000',1,'smp22874@vogco.com',NULL,NULL,1,NULL,NULL),(20,'NgaPTH','$2a$10$W7zVWUKlU/qC0l9zLAj9hO1iYw.WpggXr2.yzAj84/R/8p7Xa5ItW',1,'USERb8444277','Phạm Thị Hằng Nga','0797375994','Phường Thạch Thang, Quận Hải Châu, TP. Đà Nẵng','2008-12-28 00:00:00.000000',0,'aab82257@vogco.com',NULL,NULL,1,NULL,NULL);
/*!40000 ALTER TABLE `t14_user` ENABLE KEYS */;
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
