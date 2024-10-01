CREATE DATABASE baza3;
USE baza3;

#paketi,pretplate,gledanja,ocene,korisnici,video snimci

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

DROP TABLE IF EXISTS `videosnimak`;
CREATE TABLE `videosnimak` (
  `idvid` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `trajanje` int NOT NULL,
  `vlasnik` int NOT NULL,
  `datum_i_vreme` datetime NOT NULL,
  PRIMARY KEY (`idvid`),
  KEY `vlasnik_idx` (`vlasnik`),
  CONSTRAINT `vlasnik` FOREIGN KEY (`vlasnik`) REFERENCES `korisnik` (`idkor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `kategorija`;
CREATE TABLE `kategorija` (
  `idkat` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  PRIMARY KEY (`idkat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `videosnimak_kategorija`;
CREATE TABLE `videosnimak_kategorija` (
  `video` int NOT NULL,
  `kategorija` int NOT NULL,
  PRIMARY KEY (`video`,`kategorija`),
  KEY `kategorija_idx` (`kategorija`),
  CONSTRAINT `kategorija` FOREIGN KEY (`kategorija`) REFERENCES `kategorija` (`idkat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `video` FOREIGN KEY (`video`) REFERENCES `videosnimak` (`idvid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `kategorija` (`naziv`) VALUES
('Nauka'),
('Umetnost'),
('Sport'),
('Muzika'),
('Putovanja');

INSERT INTO `videosnimak` (`naziv`, `trajanje`, `vlasnik`, `datum_i_vreme`) VALUES
('Naucni eksperiment', 180, 1, '2024-01-15 12:00:00'),
('Umetnicko delo', 240, 2, '2024-02-01 15:30:00'),
('Sportski dogadjaj', 360, 4, '2024-02-05 18:45:00'),
('Koncert', 540, 5, '2024-01-20 20:00:00'),
('Putopis', 270, 3, '2024-02-10 10:15:00');
INSERT INTO `videosnimak_kategorija` (`video`, `kategorija`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

CREATE TABLE `paket` (
  `idpak` int NOT NULL AUTO_INCREMENT,
  `cena` int NOT NULL,
  PRIMARY KEY (`idpak`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pretplata` (
  `idpret` int NOT NULL AUTO_INCREMENT,
  `korisnik` int NOT NULL,
  `paket` int NOT NULL,
  `datum_i_vreme` datetime NOT NULL,
  `cena` int NOT NULL,
  PRIMARY KEY (`idpret`),
  KEY `korisnik_idx` (`korisnik`),
  KEY `paket_idx` (`paket`),
  CONSTRAINT `korisnik` FOREIGN KEY (`korisnik`) REFERENCES `korisnik` (`idkor`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `paket` FOREIGN KEY (`paket`) REFERENCES `paket` (`idpak`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `gledanje` (
  `idgled` int NOT NULL AUTO_INCREMENT,
  `kor` int NOT NULL,
  `vid` int NOT NULL,
  `datum_i_vreme` datetime NOT NULL,
  `sekund_poc` int NOT NULL,
  `sekund_stigao` int NOT NULL,
  PRIMARY KEY (`idgled`),
  KEY `korisnik_idx` (`kor`),
  KEY `video_idx` (`vid`),
  CONSTRAINT `kor` FOREIGN KEY (`kor`) REFERENCES `korisnik` (`idkor`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `vid` FOREIGN KEY (`vid`) REFERENCES `videosnimak` (`idvid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ocena` (
  `idocena` int NOT NULL AUTO_INCREMENT,
  `idkor` int NOT NULL,
  `idvid` int NOT NULL,
  `datum_i_vreme` datetime NOT NULL,
  `ocena` int NOT NULL,
  PRIMARY KEY (`idocena`),
  KEY `kor_idx` (`idkor`),
  KEY `vid_idx` (`idvid`),
  CONSTRAINT `idkor` FOREIGN KEY (`idkor`) REFERENCES `korisnik` (`idkor`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idvid` FOREIGN KEY (`idvid`) REFERENCES `videosnimak` (`idvid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `paket` (`cena`) VALUES
(50),
(100),
(150);

INSERT INTO `pretplata` (`korisnik`, `paket`, `datum_i_vreme`, `cena`) VALUES
(1, 1, '2024-01-15 12:00:00', 50),
(2, 2, '2024-02-01 15:30:00', 100),
(3, 1, '2024-02-05 18:45:00', 50),
(4, 3, '2024-01-20 20:00:00', 150),
(5, 1, '2024-02-10 10:15:00', 50);


INSERT INTO `gledanje` (`kor`, `vid`, `datum_i_vreme`, `sekund_poc`, `sekund_stigao`) VALUES
(1, 1, '2024-01-15 12:30:00', 0, 180),
(2, 2, '2024-02-01 15:45:00', 0, 240),
(3, 3, '2024-02-05 19:00:00', 0, 360),
(4, 4, '2024-01-20 20:30:00', 0, 540),
(5, 5, '2024-02-10 10:30:00', 0, 270);

INSERT INTO `ocena` (`idkor`, `idvid`, `datum_i_vreme`, `ocena`) VALUES
(1, 1, '2024-01-15 13:00:00', 5),
(2, 2, '2024-02-01 16:00:00', 4),
(3, 3, '2024-02-05 19:30:00', 3),
(4, 4, '2024-01-20 21:00:00', 5),
(5, 5, '2024-02-10 11:00:00', 4);



