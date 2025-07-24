-- MySQL dump 10.13  Distrib 9.3.0, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: booking_app
-- ------------------------------------------------------
-- Server version	11.8.2-MariaDB-ubu2404

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `end_date_time` datetime(6) DEFAULT NULL,
  `start_date_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `service_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb5g9io5h54iwl2inkno50ppln` (`user_id`),
  CONSTRAINT `FKb5g9io5h54iwl2inkno50ppln` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.','2025-08-16 12:25:00.000000','2025-07-26 12:25:00.000000','Priklad 1',1,NULL),(2,'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.','2025-08-09 12:25:00.000000','2025-07-24 12:25:00.000000','Priklad 2',2,NULL),(3,'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.','2025-08-10 12:24:00.000000','2025-07-25 12:24:00.000000','Priklad 3',1,NULL),(4,'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.','2025-08-08 12:49:00.000000','2025-07-31 12:49:00.000000','Priklad 4',1,NULL),(5,'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.','2025-08-10 12:51:00.000000','2025-07-30 12:51:00.000000','Priklad 5',1,NULL),(6,'Morbi in sem quis dui placerat ornare. Pellentesque odio nisi, euismod in, pharetra a, ultricies in, diam. Sed arcu. Cras consequat.','2025-08-10 13:03:00.000000','2025-07-26 13:03:00.000000','Priklad 6',1,NULL),(7,'Morbi in sem quis dui placerat ornare. Pellentesque odio nisi, euismod in, pharetra a, ultricies in, diam. Sed arcu. Cras consequat.','2025-08-16 16:07:00.000000','2025-07-27 13:04:00.000000','Priklad 7',1,NULL);
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@test.com','admin','admin','$2a$10$FrmVtTEfdkdhnpiCj1ZX4.NCn/DXHALPF2YBbdLPCa/dAo/4x7Xu2'),(2,'user@test.com','user','user','$2a$10$PXR6AUmhp8x0cZTs3Rgr7.4ORV61W18C/MwXpLNptgmQXyU7tU/Hm');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-24 19:35:25
