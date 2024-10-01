CREATE DATABASE baza1;
USE baza1;

DROP TABLE IF EXISTS `mesto`;
CREATE TABLE `mesto` (
    `idmes` INT AUTO_INCREMENT NOT NULL,
    `naziv` VARCHAR(255),
    PRIMARY KEY (`idmes`)
);

DROP TABLE IF EXISTS `korisnik`;
CREATE TABLE `korisnik` (
  `idkor` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL UNIQUE,
  `godiste` int NOT NULL,
  `pol` enum('muski','zenski') NOT NULL,
  `idmes` int DEFAULT NULL,
  PRIMARY KEY (`idkor`),
  KEY `idmes_idx` (`idmes`),
  CONSTRAINT `idmes` FOREIGN KEY (`idmes`) REFERENCES `mesto` (`idmes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `mesto` VALUES (1,'Beograd'),(2,'Tokyo'),(3,'NYC'),(4,'Amsterdam'),(5,'Moscow');

INSERT INTO `korisnik` VALUES (1, 'Nikola', 'nikola@gmail.com', 2002, 'muski', 2);
INSERT INTO `korisnik` VALUES (2, 'Natalija', 'natalija@gmail.com', 2002, 'zenski', 2);
INSERT INTO `korisnik` VALUES (3, 'Maksim', 'maxim@gmail.com', 2002, 'muski', 1);
INSERT INTO `korisnik` VALUES (4, 'Luka', 'joca@gmail.com', 2002, 'muski', 4);
INSERT INTO `korisnik` VALUES (5, 'Ivona', 'ivona@gmail.com', 2006, 'zenski', 5);