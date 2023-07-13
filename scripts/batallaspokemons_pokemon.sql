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
-- Table structure for table `pokemon`
--

DROP TABLE IF EXISTS `pokemon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pokemon` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `tipo` int DEFAULT NULL,
  `vida` double DEFAULT NULL,
  `imagenDorso` varchar(255) DEFAULT NULL,
  `imagenFrente` varchar(255) DEFAULT NULL,
  `velocidad` double DEFAULT NULL,
  `rareza` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokemon`
--

LOCK TABLES `pokemon` WRITE;
/*!40000 ALTER TABLE `pokemon` DISABLE KEYS */;
INSERT INTO `pokemon` VALUES (1,'Ivysaur',5,495,'ivysaur (1).gif','ivysaur.gif',70,0),(2,'Venusaur',5,600,'venusaur-f (1).gif','venusaur-f.gif',80,1),(3,'Charmeleon',0,395,'charmeleon (1).gif','charmeleon.gif',90,0),(4,'Charizard',0,600,'charizard (1).gif','charizard.gif',90,1),(5,'Wartortle',1,570,'wartortle (1).gif','wartortle.gif',120,0),(6,'Blastoise',1,700,'blastoise (1).gif','blastoise.gif',80,1),(7,'Raticate',6,590,'raticate-f (1).gif','raticate-f.gif',100,0),(8,'Pikachu',4,550,'pikachu-f (1).gif','pikachu-f.gif',105,0),(9,'Gyarados',1,800,'gyarados-f (1).gif','gyarados-f.gif',130,1),(10,'Eevee',6,500,'eevee (1).gif','eevee.gif',80,0),(11,'Vaporeon',1,630,'vaporeon (1).gif','vaporeon.gif',98,1),(12,'Jolteon',4,590,'jolteon (1).gif','jolteon.gif',115,1),(13,'Flareon',0,610,'flareon (1).gif','flareon.gif',95,1),(14,'Leafeon',3,610,'leafeon (1).gif','leafeon.gif',85,1),(15,'Bayleef',3,475,'bayleef (1).gif','bayleef.gif',80,0),(16,'Meganium',3,700,'meganium-f (1).gif','meganium-f.gif',85,1),(17,'Quilava',0,430,'quilava (1).gif','quilava.gif',83,0),(18,'Typhlosion',0,560,'typhlosion (1).gif','typhlosion.gif',115,1),(19,'Croconaw',1,460,'croconaw (1).gif','croconaw.gif',79,0),(20,'Feraligatr',1,620,'feraligatr (1).gif','feraligatr.gif',95,1),(21,'Grovyle',3,430,'grovyle (1).gif','grovyle.gif',87,0),(22,'Sceptile',3,570,'sceptile (1).gif','sceptile.gif',115,1),(23,'Combusken',13,400,'combusken-f (1).gif','combusken-f.gif',90,0),(24,'Blaziken',13,500,'blaziken-f (1).gif','blaziken-f.gif',120,1),(25,'Marshtomp',1,490,'marshtomp (1).gif','marshtomp.gif',88,0),(26,'Swampert',1,650,'swampert (1).gif','swampert.gif',100,1),(27,'Gardevoir',4,500,'gardevoir (1).gif','gardevoir.gif',100,1),(28,'Grotle',2,500,'grotle (1).gif','grotle.gif',75,0),(29,'Torterra',2,800,'torterra (1).gif','torterra.gif',75,1),(30,'Monferno',0,390,'monferno (1).gif','monferno.gif',95,0),(31,'Infernape',0,550,'infernape (1).gif','infernape.gif',110,1),(32,'Prinplup',1,470,'prinplup (1).gif','prinplup.gif',85,0),(33,'Empoleon',1,650,'empoleon (1).gif','empoleon.gif',85,1),(34,'Servine',3,460,'servine (1).gif','servine.gif',85,0),(35,'Serperior',3,580,'serperior (1).gif','serperior.gif',105,1),(36,'Pignite',0,450,'pignite (1).gif','pignite.gif',90,0),(37,'Emboar',0,650,'emboar (1).gif','emboar.gif',90,1),(38,'Dewott',1,430,'dewott (1).gif','dewott.gif',80,0),(39,'Samurott',1,630,'samurott (1).gif','samurott.gif',95,1),(40,'Moltres',0,800,'moltres (1).gif','moltres.gif',100,2),(41,'Zapdos',4,800,'zapdos (1).gif','zapdos.gif',150,2),(42,'Articuno',8,800,'articuno (1).gif','articuno.gif',110,2),(43,'Entei',0,800,'entei (1).gif','entei.gif',100,2),(44,'Raikou',4,800,'raikou (1).gif','raikou.gif',125,2),(45,'Suicune',1,800,'suicune (1).gif','suicune.gif',105,2),(46,'Regice',8,800,'regice (1).gif','regice.gif',90,2),(47,'Registeel',9,800,'registeel (1).gif','registeel.gif',120,2),(48,'Regirock',2,800,'regirock (1).gif','regirock.gif',85,2),(49,'Regigigas',6,800,'regigigas (1).gif','regigigas.gif',1,2),(50,'Groudon',2,800,'groudon (1).gif','groudon.gif',90,2),(51,'Kyogre',1,800,'kyogre (1).gif','kyogre.gif',100,2),(52,'Rayquaza',10,800,'rayquaza (1).gif','rayquaza.gif',130,2),(53,'Latios',10,800,'latios (1).gif','latios.gif',150,2),(54,'Latias',10,800,'latias (1).gif','latias.gif',150,2),(55,'Lugia',1,800,'lugia (1).gif','lugia.gif',100,2),(56,'Ho-oh',0,800,'ho-oh (1).gif','ho-oh.gif',105,2),(57,'Darkrai',12,800,'darkrai (1).gif','darkrai.gif',100,2),(58,'Giratina',12,800,'giratina (1).gif','giratina.gif',110,2),(59,'Dialga',9,800,'dialga (1).gif','dialga.gif',107,2),(60,'Palkia',1,800,'palkia (1).gif','palkia.gif',100,2),(61,'Zekrom',4,800,'zekrom (1).gif','zekrom.gif',110,2),(62,'Reshiram',0,800,'reshiram (1).gif','reshiram.gif',120,2),(63,'Kyurem',8,800,'kyurem (1).gif','kyurem.gif',125,2);
/*!40000 ALTER TABLE `pokemon` ENABLE KEYS */;
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
