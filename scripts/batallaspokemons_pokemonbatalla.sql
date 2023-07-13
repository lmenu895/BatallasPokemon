CREATE DATABASE  IF NOT EXISTS `batallaspokemons` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `batallaspokemons`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: batallaspokemons
-- ------------------------------------------------------
-- Server version	8.0.24

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
-- Table structure for table `pokemonbatalla`
--

DROP TABLE IF EXISTS `pokemonbatalla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pokemonbatalla` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `debilitado` bit(1) DEFAULT NULL,
  `entrenador` varchar(255) DEFAULT NULL,
  `batalla_id` bigint DEFAULT NULL,
  `pokemon_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdcjaj031116uv3n7oce0ute18` (`pokemon_id`),
  KEY `FKdy5r1fycfkyikj9yoyewfaf6u` (`batalla_id`),
  CONSTRAINT `FKdcjaj031116uv3n7oce0ute18` FOREIGN KEY (`pokemon_id`) REFERENCES `pokemon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKdy5r1fycfkyikj9yoyewfaf6u` FOREIGN KEY (`batalla_id`) REFERENCES `batalla` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokemonbatalla`
--

LOCK TABLES `pokemonbatalla` WRITE;
/*!40000 ALTER TABLE `pokemonbatalla` DISABLE KEYS */;
INSERT INTO `pokemonbatalla` VALUES (73,_binary '\0','usuario',13,1),(74,_binary '\0','usuario',13,1),(75,_binary '\0','usuario',13,1),(76,_binary '','cpu',13,1),(77,_binary '','cpu',13,1),(78,_binary '','cpu',13,1),(79,_binary '\0','usuario',14,1),(80,_binary '\0','usuario',14,1),(81,_binary '\0','usuario',14,1),(82,_binary '','cpu',14,1),(83,_binary '','cpu',14,1),(84,_binary '','cpu',14,1),(85,_binary '\0','usuario',15,1),(86,_binary '\0','usuario',15,1),(87,_binary '\0','usuario',15,1),(88,_binary '','cpu',15,1),(89,_binary '','cpu',15,1),(90,_binary '','cpu',15,1),(91,_binary '\0','usuario',16,10),(92,_binary '\0','usuario',16,27),(93,_binary '\0','usuario',16,3),(94,_binary '','cpu',16,62),(95,_binary '','cpu',16,42),(96,_binary '','cpu',16,49),(97,_binary '\0','usuario',17,10),(98,_binary '\0','usuario',17,27),(99,_binary '\0','usuario',17,3),(100,_binary '','cpu',17,56),(101,_binary '','cpu',17,29),(102,_binary '','cpu',17,48),(103,_binary '\0','usuario',18,10),(104,_binary '\0','usuario',18,27),(105,_binary '\0','usuario',18,3),(106,_binary '','cpu',18,44),(107,_binary '','cpu',18,24),(108,_binary '','cpu',18,52),(109,_binary '\0','usuario',19,10),(110,_binary '\0','usuario',19,27),(111,_binary '\0','usuario',19,3),(112,_binary '','cpu',19,48),(113,_binary '','cpu',19,23),(114,_binary '','cpu',19,6),(115,_binary '','usuario',20,2),(116,_binary '\0','usuario',20,3),(117,_binary '','usuario',20,5),(118,_binary '','cpu',20,48),(119,_binary '','cpu',20,47),(120,_binary '','cpu',20,16);
/*!40000 ALTER TABLE `pokemonbatalla` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-12 23:33:58
