-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 07, 2023 at 10:53 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

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
  `ID_Bike` int(11) NOT NULL,
  `ID_Station` int(11) NOT NULL,
  `BikeSerialNumber` char(50) NOT NULL,
  `Deposit` double NOT NULL,
  `BikeType` char(50) NOT NULL,
  `isLocked` tinyint(1) NOT NULL,
  `linkImage` char(200) NOT NULL,
  `Pin` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bike`
--

INSERT INTO `bike` (`ID_Bike`, `ID_Station`, `BikeSerialNumber`, `Deposit`, `BikeType`, `isLocked`, `linkImage`, `Pin`) VALUES
(1, 1, 'WTU291820Z', 3, 'VSence Bike', 0, '', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE `card` (
  `CreditCardNumber` char(50) NOT NULL,
  `BankName` char(50) NOT NULL,
  `FullName` char(50) NOT NULL,
  `ExpireDate` date NOT NULL,
  `SecurityCode` char(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `card`
--

INSERT INTO `card` (`CreditCardNumber`, `BankName`, `FullName`, `ExpireDate`, `SecurityCode`) VALUES
('03230840248207', 'Techcom Bank', 'NGUYEN THU TRANG', '0000-00-00', '21090992'),
('09358083403', 'MB Bank', 'NGUYEN THANH TU', '0000-00-00', '17032008');

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `ID_Station` int(11) NOT NULL,
  `StationName` char(200) NOT NULL,
  `LocationName` char(200) NOT NULL,
  `StationArea` char(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`ID_Station`, `StationName`, `LocationName`, `StationArea`) VALUES
(1, 'SVD Bach Khoa', '197 Minh Khai, Hai Ba Trung', '42'),
(2, 'Khu ki tuc NEU', '7 Dong Tam, Hai Ba Trung', '20');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `ID_Transaction` int(11) NOT NULL,
  `CreditCardNumber` char(50) NOT NULL,
  `ID_Bike` int(11) NOT NULL,
  `Date_Transaction` date NOT NULL,
  `TimeStart` time NOT NULL,
  `LatestUnlockTime` time NOT NULL,
  `ContentTransaction` char(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bike`
--
ALTER TABLE `bike`
  ADD PRIMARY KEY (`ID_Bike`);

--
-- Indexes for table `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`CreditCardNumber`);

--
-- Indexes for table `station`
--
ALTER TABLE `station`
  ADD PRIMARY KEY (`ID_Station`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`ID_Transaction`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
