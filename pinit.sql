-- MySQL dump 10.13  Distrib 5.7.19, for Linux (x86_64)
--
-- Host: localhost    Database: pinitBDD
-- ------------------------------------------------------
-- Server version	5.7.19-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_activity` varchar(255) DEFAULT NULL,
  `rain_max` double NOT NULL,
  `rain_min` double NOT NULL,
  `snow_max` double NOT NULL,
  `snow_min` double NOT NULL,
  `temp_max` double NOT NULL,
  `temp_min` double NOT NULL,
  `wind_speed_max` double NOT NULL,
  `wind_speed_min` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,'Randonnée',5,0,50,0,30,10,40,0),(2,'Vélo',5,0,5,0,30,10,40,0);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES ('ROLE_ADMIN'),('ROLE_ANONYMOUS'),('ROLE_USER');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inscription_activity`
--

DROP TABLE IF EXISTS `inscription_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inscription_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `activity_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgvahh74b7h7v5o6b47yb80p04` (`activity_id`),
  KEY `FKknpj9329anccmdk75hglunvs4` (`user_id`),
  CONSTRAINT `FKgvahh74b7h7v5o6b47yb80p04` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `FKknpj9329anccmdk75hglunvs4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscription_activity`
--

LOCK TABLES `inscription_activity` WRITE;
/*!40000 ALTER TABLE `inscription_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `inscription_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(255) NOT NULL,
  KEY `FK6ktglpl5mjosa283rvken2py5` (`authority_name`),
  KEY `FKpqlsjpkybgos9w2svcri7j8xy` (`user_id`),
  CONSTRAINT `FK6ktglpl5mjosa283rvken2py5` FOREIGN KEY (`authority_name`) REFERENCES `authority` (`authority`),
  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-13 19:41:18


#Data fixtures to develop in ease

-- Authority
INSERT INTO authority values('ROLE_ADMIN');
INSERT INTO authority values('ROLE_USER');
INSERT INTO authority values('ROLE_ANONYMOUS');

-- Activity
-- rain max, rain min, snow, temp, wind
INSERT INTO activity VALUES (1,'Randonnée',5,0,50,0,30,10,40,0);
INSERT INTO activity VALUES (2,'Vélo',5,0,5,0,30,10,40,0);