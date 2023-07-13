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
-- Table structure for table `ataque`
--

DROP TABLE IF EXISTS `ataque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ataque` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `efecto` bit(1) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `potencia` double DEFAULT NULL,
  `pp` double DEFAULT NULL,
  `precataque` double DEFAULT NULL,
  `tipo` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ataque`
--

LOCK TABLES `ataque` WRITE;
/*!40000 ALTER TABLE `ataque` DISABLE KEYS */;
INSERT INTO `ataque` VALUES (0,_binary '','Residuos',90,10,90,5),(1,_binary '','Rayo',90,25,90,4),(2,_binary '\0','Terremoto',100,10,90,2),(3,_binary '','Lanzallamas',90,10,90,0),(4,_binary '\0','Hoja aguda',70,20,95,3),(5,_binary '\0','Megapatada',100,5,70,6),(6,_binary '\0','Gigadrenado',90,5,90,7),(7,_binary '\0','Surf',90,10,90,1),(18,_binary '','Onda Voltio',70,20,100,4),(19,_binary '','Picotazo Venenoso',50,20,90,5),(20,_binary '','Ascuas',60,30,95,0),(21,_binary '\0','Hojas Navaja',75,20,90,3),(22,_binary '\0','Absorber',60,20,90,7),(23,_binary '','Punio Fuego',80,15,90,0),(24,_binary '','Patada Ignea',75,20,90,0),(25,_binary '\0','Hidropulso',60,20,90,1),(26,_binary '\0','Rayo Burbuja',50,25,90,1),(27,_binary '\0','Excavar',60,25,90,2),(28,_binary '\0','Tumba Rocas',70,25,90,2),(29,_binary '\0','Guillotina',5000,5,30,6),(30,_binary '','Gas Venenoso',40,30,90,5),(31,_binary '\0','Placaje',50,25,90,6),(32,_binary '\0','Rayo Aurora',80,20,80,8),(33,_binary '\0','Rayo Hielo',90,10,90,8),(34,_binary '\0','Garra Metal',50,20,90,9),(35,_binary '\0','Cola Ferrea',100,10,90,9),(36,_binary '\0','Garra Dragon',50,30,90,10),(37,_binary '\0','Cometa Draco',150,5,80,10),(38,_binary '\0','Confusion',40,25,90,11),(39,_binary '\0','Psiquico',90,10,90,11),(40,_binary '\0','Mordisco',50,20,80,12),(41,_binary '\0','Triturar',90,10,90,12),(42,_binary '\0','Golpe Karate',60,20,80,13),(43,_binary '\0','Demolicion ',100,10,90,13);
/*!40000 ALTER TABLE `ataque` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-12 23:33:59
