SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `project`
--

--
-- Table structure for table `basket_items`
--

CREATE TABLE `basket_items` (
  `customer_id` int(11) NOT NULL,
  `tree_id` int(11) NOT NULL,
  `rental_start_date` date NOT NULL,
  `rental_end_date` date NOT NULL,
  `delivery_out` enum('STANDARD_DELIVERY','COLLECT_FROM_WAREHOUSE','AM_DELIVERY','PM_DELIVERY') NOT NULL,
  `delivery_in` enum('STANDARD_COLLECTION','RETURN_TO_WAREHOUSE','AM_COLLECTION','PM_COLLECTION') NOT NULL,
  `quantity` int(11) NOT NULL,
  `date_added` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` int(11) NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `email_address` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone_number` varchar(15) NOT NULL DEFAULT '',
  `address_line_1` varchar(75) NOT NULL DEFAULT '',
  `address_line_2` varchar(75) NOT NULL DEFAULT '',
  `city` varchar(25) NOT NULL DEFAULT '',
  `post_code` varchar(10) NOT NULL DEFAULT '',
  `is_admin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `first_name`, `last_name`, `email_address`, `password`, `phone_number`, `address_line_1`, `address_line_2`, `city`, `post_code`, `is_admin`) VALUES
(1, 'John', 'Smith', 'johnsmith@gmail.com', '1234', '07123456789', '10 Sandy Lane', '', 'London', 'NW1 1AA', 1);

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `promotion_id` int(11) NOT NULL,
  `status` varchar(25) NOT NULL,
  `date_ordered` datetime NOT NULL DEFAULT current_timestamp(),
  `date_last_updated` datetime NOT NULL DEFAULT current_timestamp(),
  `total_price` double NOT NULL,
  `total_deposit` double NOT NULL,
  `total_delivery` double NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `order_id` int(11) NOT NULL,
  `tree_id` int(11) NOT NULL,
  `rental_start_date` date NOT NULL,
  `rental_end_date` date NOT NULL,
  `delivery_out` enum('STANDARD_DELIVERY','COLLECT_FROM_WAREHOUSE','AM_DELIVERY','PM_DELIVERY') NOT NULL,
  `delivery_in` enum('STANDARD_COLLECTION','RETURN_TO_WAREHOUSE','AM_COLLECTION','PM_COLLECTION') NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `promotions`
--

CREATE TABLE `promotions` (
  `id` int(11) NOT NULL,
  `description` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `promotions`
--

INSERT INTO `promotions` (`id`, `description`) VALUES
(1, 'Buy One Get One Half Price');

--
-- Table structure for table `trees`
--

CREATE TABLE `trees` (
  `id` int(11) NOT NULL,
  `type` varchar(25) NOT NULL,
  `material` varchar(25) NOT NULL,
  `height` int(11) NOT NULL,
  `description` varchar(250) NOT NULL,
  `supplier` varchar(25) NOT NULL,
  `price_per_day` double NOT NULL,
  `stock_level` int(11) NOT NULL,
  `deposit_percentage` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trees`
--

INSERT INTO `trees` (`id`, `type`, `material`, `height`, `description`, `supplier`, `price_per_day`, `stock_level`, `deposit_percentage`) VALUES
(1, 'Pine', 'PVC', 250, 'Pine trees are the most common coniferous trees in the world. Plastic made, perfect for hanging heavy decorations.', 'Big Trees Limited', 50, 5, 35),
(2, 'Pine', 'PVC', 300, 'Pine trees are the most common coniferous trees in the world. Plastic made, perfect for hanging heavy decorations.', 'Big Trees Limited', 65, 3, 35),
(3, 'Fir', 'Natural', 250, 'Fir trees are symmetrical, evergreen, coniferous trees that range in maturity. Aroma of juniper berries.', 'Fir Trees Ltd', 100, 3, 50),
(4, 'Fir', 'Natural', 200, 'Fir trees are symmetrical, evergreen, coniferous trees that range in maturity. Aroma of juniper berries.', 'Fir Trees Ltd', 75, 4, 50),
(5, 'Pine', 'PVC', 250, 'Pine trees are the most common coniferous trees in the world. Plastic made, perfect for hanging heavy decorations.', 'Big Trees Limited', 50, 5, 35);

--
-- Indexes for table `basket_items`
--
ALTER TABLE `basket_items`
  ADD PRIMARY KEY (`customer_id`,`tree_id`,`date_added`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`order_id`,`tree_id`);

--
-- Indexes for table `promotions`
--
ALTER TABLE `promotions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trees`
--
ALTER TABLE `trees`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `promotions`
--
ALTER TABLE `promotions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `trees`
--
ALTER TABLE `trees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
  
COMMIT;
