-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ebike
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `bike`
--

DROP TABLE IF EXISTS `bike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bike` (
  `ID_Bike` int NOT NULL AUTO_INCREMENT,
  `ID_Station` int NOT NULL,
  `BikeSerialNumber` char(50) NOT NULL,
  `Deposit` double NOT NULL,
  `BikeType` char(50) NOT NULL,
  `isLocked` tinyint(1) NOT NULL,
  `linkImage` char(200) NOT NULL,
  `Pin` time DEFAULT NULL,
  PRIMARY KEY (`ID_Bike`),
  KEY `Bike_StationID_Station_StationID` (`ID_Station`),
  CONSTRAINT `Bike_StationID_Station_StationID` FOREIGN KEY (`ID_Station`) REFERENCES `station` (`ID_Station`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bike`
--

LOCK TABLES `bike` WRITE;
/*!40000 ALTER TABLE `bike` DISABLE KEYS */;
INSERT INTO `bike` VALUES (1,1,'WTU291820Z',400000,'xe đạp',2,'','00:00:00'),(2,3,'KTU2091F9',400000,'xe đạp',2,'','00:00:00'),(3,5,'eV08018V',700000,'xe điện',2,'','01:05:31'),(4,2,'181083z97V',550000,'xe đạp đôi',2,'','01:03:36'),(5,4,'32938VI23nU',700000,'xe điện',2,'','01:03:01'),(6,1,'181083z97A',550000,'xe đạp đôi',2,'','03:02:01'),(7,2,'181083z97XA',700000,'xe điện',2,'','04:02:01');
/*!40000 ALTER TABLE `bike` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-29 23:29:25
