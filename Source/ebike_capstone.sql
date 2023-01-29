-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2023 at 03:09 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11
CREATE DATABASE ebike_capstone;

USE ebike_capstone;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ebike`
--

-- --------------------------------------------------------

--
-- Table structure for table `bike`
--

CREATE TABLE `bike` (
  `ID_Bike` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Station` int(11) NOT NULL,
  `BikeSerialNumber` char(50) NOT NULL,
  `Deposit` double NOT NULL,
  `BikeType` char(50) NOT NULL,
  `isLocked` tinyint(1) NOT NULL,
  `linkImage` char(200) NOT NULL,
  `Pin` time DEFAULT NULL,
    PRIMARY KEY ( `ID_Bike` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bike`
--

INSERT INTO `bike` (`ID_Bike`, `ID_Station`, `BikeSerialNumber`, `Deposit`, `BikeType`, `isLocked`, `linkImage`, `Pin`) VALUES
(1, 1, 'WTU291820Z', 3, 'xe điện', 2, '', '01:01:57'),
(2, 3, 'KTU2091F9', 20, 'xe đạp', 2, '', '00:00:00'),
(3, 5, 'eV08018V', 21, 'xe điện', 2, '', '01:05:31'),
(4, 2, '181083z97V', 32, 'xe điện', 2, '', '01:03:36'),
(5, 4, '32938VI23nU', 19, 'xe điện', 2, '', '01:03:01');

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE `card` (
  `CreditCardNumber` char(50) NOT NULL,
  `BankName` char(50) NOT NULL,
  `FullName` char(50) NOT NULL,
  `ExpireDate` date NOT NULL,
  `SecurityCode` char(50) NOT NULL,
  `money` int(11) NOT NULL,
    PRIMARY KEY ( `CreditCardNumber` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `card`
--

INSERT INTO `card` (`CreditCardNumber`, `BankName`, `FullName`, `ExpireDate`, `SecurityCode`,`money`) VALUES
('013909888012', 'Techcom Bank', 'NGUYEN THI CHI', '2023-01-01', '1090801801','1000000');

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `ID_Station` int(11) NOT NULL AUTO_INCREMENT,
  `StationName` char(200) NOT NULL,
  `LocationName` char(200) NOT NULL,
  `StationArea` char(200) NOT NULL,
    PRIMARY KEY ( `ID_Station` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`ID_Station`, `StationName`, `LocationName`, `StationArea`) VALUES
(1, 'SVD Bach Khoa', '197 Minh Khai, Hai Ba Trung', '42'),
(2, 'Khu ki tuc NEU', '7 Dong Tam, Hai Ba Trung', '20'),
(3, 'Đại học bách khoa', 'số 1, Đại Cồ Việt', '2010'),
(4, 'Khu do thi Ecopark', 'So 20, Nguyen Nhu Xuan', '200'),
(5, 'Cong vien Yen So', 'so 201 Cau Giay', '3000');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `ID_Transaction` int(11) NOT NULL AUTO_INCREMENT,
  `CreditCardNumber` char(50) NOT NULL,
  `ID_Bike` int(11) NOT NULL,
  `Date_Transaction` date NOT NULL,
  `TimeStart` time NOT NULL,
  `LatestUnlockTime` time NOT NULL,
  `ContentTransaction` char(100) NOT NULL,
  `TimeRentingBike` time NOT NULL,
    PRIMARY KEY ( `ID_Transaction` )

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bike`
--
ALTER TABLE `bike`
  
  ADD KEY `Bike_StationID_Station_StationID` (`ID_Station`);

--


--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`

  ADD KEY `Transaction_BikeID_Bike_BikeID` (`ID_Bike`),
  ADD KEY `Card_CreditNumber_Transaction_CreditNumber` (`CreditCardNumber`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bike`
--
ALTER TABLE `bike`
  ADD CONSTRAINT `Bike_StationID_Station_StationID` FOREIGN KEY (`ID_Station`) REFERENCES `station` (`ID_Station`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `Card_CreditNumber_Transaction_CreditNumber` FOREIGN KEY (`CreditCardNumber`) REFERENCES `card` (`CreditCardNumber`),
  ADD CONSTRAINT `Transaction_BikeID_Bike_BikeID` FOREIGN KEY (`ID_Bike`) REFERENCES `bike` (`ID_Bike`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
