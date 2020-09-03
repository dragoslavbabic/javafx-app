-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 03, 2020 at 01:25 PM
-- Server version: 5.7.31-0ubuntu0.18.04.1
-- PHP Version: 7.2.24-0ubuntu0.18.04.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `evidencijavoznje`
--

-- --------------------------------------------------------

--
-- Table structure for table `destinacija`
--

CREATE TABLE `destinacija` (
  `id` int(11) NOT NULL,
  `grad` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `destinacija`
--

INSERT INTO `destinacija` (`id`, `grad`) VALUES
(1, 'Novi Sad'),
(2, 'Zrenjanin'),
(3, 'Sombor'),
(4, 'Kikinda'),
(5, 'Subotica');

-- --------------------------------------------------------

--
-- Table structure for table `gorivo`
--

CREATE TABLE `gorivo` (
  `id` int(20) NOT NULL,
  `iznos` int(11) DEFAULT NULL,
  `litre` int(11) NOT NULL,
  `voznja_id` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `id` int(20) NOT NULL,
  `admin` bit(1) DEFAULT NULL,
  `ime` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `korisnicko_ime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lozinka` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prezime` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`id`, `admin`, `ime`, `korisnicko_ime`, `lozinka`, `prezime`) VALUES
(9, NULL, 'Timotije', 'tim', '12', 'Timotić'),
(10, NULL, 'Jovan', 'joca', '111', 'Jovanović'),
(11, NULL, 'Goran', 'goran', '111', 'Goranović');

-- --------------------------------------------------------

--
-- Table structure for table `servis`
--

CREATE TABLE `servis` (
  `id` int(20) NOT NULL,
  `iznos` int(11) DEFAULT NULL,
  `opis` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vrsta` int(11) NOT NULL,
  `voznja_id` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vozilo`
--

CREATE TABLE `vozilo` (
  `id` int(20) NOT NULL,
  `datum_registracije` datetime DEFAULT NULL,
  `predjeno_km` int(11) DEFAULT NULL,
  `registarski_broj` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rok_zapp` datetime DEFAULT NULL,
  `rok_za_prvu_pomoc` datetime DEFAULT NULL,
  `servis_km` int(11) DEFAULT NULL,
  `servisni_interval` int(11) DEFAULT NULL,
  `naziv` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `vozilo`
--

INSERT INTO `vozilo` (`id`, `datum_registracije`, `predjeno_km`, `registarski_broj`, `rok_zapp`, `rok_za_prvu_pomoc`, `servis_km`, `servisni_interval`, `naziv`) VALUES
(11, NULL, 12000, 'NS 111 AA', NULL, NULL, NULL, NULL, 'Vozilo1'),
(12, NULL, 13000, 'NS 222 BB', NULL, NULL, NULL, NULL, 'Vozilo2'),
(13, NULL, 14000, 'NS 333 CC', NULL, NULL, NULL, NULL, 'Vozilo3');

-- --------------------------------------------------------

--
-- Table structure for table `voznja`
--

CREATE TABLE `voznja` (
  `id` int(20) NOT NULL,
  `pocetak_voznje` timestamp NULL DEFAULT NULL,
  `kraj_voznje` timestamp NULL DEFAULT NULL,
  `pocetna_km` int(11) DEFAULT NULL,
  `zavrsna_km` int(11) DEFAULT NULL,
  `pranje` bit(1) DEFAULT NULL,
  `predjeno_km` int(11) DEFAULT NULL,
  `stanje_vozila` int(11) DEFAULT NULL,
  `svrha` int(11) DEFAULT NULL,
  `napomena` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `destinacija_id` int(11) DEFAULT NULL,
  `korisnik_id` int(20) DEFAULT NULL,
  `vozilo_id` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `voznja`
--

INSERT INTO `voznja` (`id`, `pocetak_voznje`, `kraj_voznje`, `pocetna_km`, `zavrsna_km`, `pranje`, `predjeno_km`, `stanje_vozila`, `svrha`, `napomena`, `destinacija_id`, `korisnik_id`, `vozilo_id`) VALUES
(23, '2020-09-03 10:01:00', '2020-09-03 10:01:00', 12000, 12000, NULL, NULL, NULL, NULL, 'Novo vozilo', NULL, NULL, 11),
(24, '2020-09-03 10:01:23', '2020-09-03 10:01:23', 13000, 13000, NULL, NULL, NULL, NULL, 'Novo vozilo', NULL, NULL, 12),
(25, '2020-09-03 10:01:38', '2020-09-03 10:01:38', 14000, 14000, NULL, NULL, NULL, NULL, 'Novo vozilo', NULL, NULL, 13),
(26, '2020-09-03 10:16:42', NULL, 12000, NULL, NULL, NULL, 0, 0, '', 1, 10, 11),
(27, '2020-09-03 10:22:05', NULL, 13000, NULL, NULL, NULL, 0, 0, '', 1, 9, 12),
(28, '2020-09-03 10:26:25', '2020-09-03 10:27:20', 14000, 14200, NULL, 200, 0, 1, '', 4, 11, 13);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `destinacija`
--
ALTER TABLE `destinacija`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `gorivo`
--
ALTER TABLE `gorivo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `voznja_to_gorivo` (`voznja_id`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `servis`
--
ALTER TABLE `servis`
  ADD PRIMARY KEY (`id`),
  ADD KEY `voznja_to_servis` (`voznja_id`);

--
-- Indexes for table `vozilo`
--
ALTER TABLE `vozilo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `voznja`
--
ALTER TABLE `voznja`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vozilo_to_voznja` (`vozilo_id`),
  ADD KEY `korisnik_to_voznja` (`korisnik_id`),
  ADD KEY `destinacija_to_voznja` (`destinacija_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `destinacija`
--
ALTER TABLE `destinacija`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `gorivo`
--
ALTER TABLE `gorivo`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `servis`
--
ALTER TABLE `servis`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `vozilo`
--
ALTER TABLE `vozilo`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `voznja`
--
ALTER TABLE `voznja`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `gorivo`
--
ALTER TABLE `gorivo`
  ADD CONSTRAINT `voznja_to_gorivo` FOREIGN KEY (`voznja_id`) REFERENCES `voznja` (`id`);

--
-- Constraints for table `servis`
--
ALTER TABLE `servis`
  ADD CONSTRAINT `voznja_to_servis` FOREIGN KEY (`voznja_id`) REFERENCES `voznja` (`id`);

--
-- Constraints for table `voznja`
--
ALTER TABLE `voznja`
  ADD CONSTRAINT `destinacija_to_voznja` FOREIGN KEY (`destinacija_id`) REFERENCES `destinacija` (`id`),
  ADD CONSTRAINT `korisnik_to_voznja` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`id`),
  ADD CONSTRAINT `vozilo_to_voznja` FOREIGN KEY (`vozilo_id`) REFERENCES `vozilo` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
