/*
 Navicat Premium Data Transfer

 Source Server         : Local
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : airline_reservations

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 27/05/2025 17:49:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for airline
-- ----------------------------
DROP TABLE IF EXISTS `airline`;
CREATE TABLE `airline`  (
  `airline_id` int NOT NULL AUTO_INCREMENT,
  `airplane_id` int NULL DEFAULT NULL,
  `arrival_airport_id` int NULL DEFAULT NULL,
  `departure_airport_id` int NULL DEFAULT NULL,
  `boarding_gate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `departure_time` time NULL DEFAULT NULL,
  `arrival_time` time NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `first_seat` int NULL DEFAULT NULL,
  `second_seat` int NULL DEFAULT NULL,
  `third_seat` int NULL DEFAULT NULL,
  `duration` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `departure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `arrival` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`airline_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of airline
-- ----------------------------
INSERT INTO `airline` VALUES (1, 1, 1, 3, 'D12', 1599.00, '20:00:00', '23:30:00', '2025-12-17', 25, 160, 220, '3h30m', '广州', '北京');
INSERT INTO `airline` VALUES (2, 2, 3, 1, 'G05', 800.00, '10:15:00', '12:45:00', '2025-06-01', 15, 40, 65, '2h30m', '北京', '广州');
INSERT INTO `airline` VALUES (3, 3, 2, 3, 'G12', 1500.00, '14:30:00', '17:45:00', '2025-06-02', 25, 60, 120, '3h15m', '广州', '上海');
INSERT INTO `airline` VALUES (4, 4, 4, 1, 'G03', 900.00, '09:00:00', '11:15:00', '2025-06-02', 18, 45, 90, '2h15m', '北京', '深圳');
INSERT INTO `airline` VALUES (5, 5, 1, 4, 'G08', 1100.00, '13:45:00', '16:30:00', '2025-06-03', 22, 55, 110, '2h45m', '深圳', '北京');
INSERT INTO `airline` VALUES (6, 1, 3, 2, 'G10', 1300.00, '16:00:00', '18:15:00', '2025-06-03', 20, 50, 100, '2h15m', '上海', '广州');
INSERT INTO `airline` VALUES (7, 2, 2, 4, 'G07', 950.00, '11:30:00', '14:00:00', '2025-06-04', 15, 40, 80, '2h30m', '深圳', '上海');
INSERT INTO `airline` VALUES (8, 3, 4, 3, 'G09', 850.00, '08:45:00', '11:00:00', '2025-06-04', 25, 60, 120, '2h15m', '广州', '深圳');
INSERT INTO `airline` VALUES (9, 4, 1, 3, 'G02', 1400.00, '15:15:00', '18:30:00', '2025-06-05', 18, 45, 90, '3h15m', '广州', '北京');
INSERT INTO `airline` VALUES (10, 10, 2, 1, 'a2', 750.00, '07:30:00', '09:45:00', '2025-06-15', 123, 55, 110, '2h15m', '北京', '上海');

-- ----------------------------
-- Table structure for airplane
-- ----------------------------
DROP TABLE IF EXISTS `airplane`;
CREATE TABLE `airplane`  (
  `airplane_id` int NOT NULL AUTO_INCREMENT,
  `airplane_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `first_seat` int NULL DEFAULT NULL,
  `second_seat` int NULL DEFAULT NULL,
  `third_seat` int NULL DEFAULT NULL,
  `belonged_airport_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`airplane_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of airplane
-- ----------------------------
INSERT INTO `airplane` VALUES (1, '波音737', '已着陆', '北京', 20, 50, 100, 1);
INSERT INTO `airplane` VALUES (2, '空客A320', '已着陆', '广州', 15, 40, 80, 2);
INSERT INTO `airplane` VALUES (3, '波音787', '维护中', '上海', 25, 60, 120, 5);
INSERT INTO `airplane` VALUES (4, '空客A330', '航行中', '深圳', 18, 45, 90, 4);
INSERT INTO `airplane` VALUES (5, '波音747', '航行中', '北京', 22, 55, 110, 5);
INSERT INTO `airplane` VALUES (6, '空客A380', '维护中', '广州', 30, 70, 150, 2);
INSERT INTO `airplane` VALUES (7, '波音777', '航行中', '上海', 20, 50, 100, 1);
INSERT INTO `airplane` VALUES (8, '空客A350', '航行中', '深圳', 25, 60, 120, 2);
INSERT INTO `airplane` VALUES (9, '波音737 MAX', '维护中', '北京', 15, 40, 80, 3);
INSERT INTO `airplane` VALUES (10, '空客A321', '维护中', '广州', 18, 45, 90, 1);

-- ----------------------------
-- Table structure for airport
-- ----------------------------
DROP TABLE IF EXISTS `airport`;
CREATE TABLE `airport`  (
  `airport_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `airport_id` int NOT NULL,
  PRIMARY KEY (`airport_id` DESC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of airport
-- ----------------------------
INSERT INTO `airport` VALUES ('成都双流国际机场', '028-85205555', '成都市双流区机场路', 5);
INSERT INTO `airport` VALUES ('深圳宝安国际机场', '0755-23456789', '深圳市宝安区机场一路', 4);
INSERT INTO `airport` VALUES ('广州白云国际机场', '020-36066999', '广州市花都区机场路', 3);
INSERT INTO `airport` VALUES ('上海浦东国际机场', '021-68341000', '上海市浦东新区机场大道', 2);
INSERT INTO `airport` VALUES ('北京首都国际机场', '010-64541100', '北京市朝阳区机场西路', 1);

-- ----------------------------
-- Table structure for airport_user
-- ----------------------------
DROP TABLE IF EXISTS `airport_user`;
CREATE TABLE `airport_user`  (
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `airport_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`user_id` DESC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of airport_user
-- ----------------------------
INSERT INTO `airport_user` VALUES ('13800138004', '81dc9bdb52d04dc20036dbd8313ed055', 1, '1234', 1);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `customer_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `mils_points` int NULL DEFAULT NULL,
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (2, '13800138002', '王芳', '81dc9bdb52d04dc20036dbd8313ed055', 'wangfang@email.com', 14800);
INSERT INTO `customer` VALUES (3, '13800138003', '李强', '81dc9bdb52d04dc20036dbd8313ed055', 'liqiang@email.com', 2500);
INSERT INTO `customer` VALUES (4, '13800138004', '刘洋', '81dc9bdb52d04dc20036dbd8313ed055', 'liuyang@email.com', 11400);
INSERT INTO `customer` VALUES (5, '13800138005', '陈明', '81dc9bdb52d04dc20036dbd8313ed055', 'chenming@email.com', 3000);
INSERT INTO `customer` VALUES (6, '13800138006', '杨丽', '81dc9bdb52d04dc20036dbd8313ed055', 'yangli@email.com', 500);
INSERT INTO `customer` VALUES (7, '13800138007', '赵刚', '81dc9bdb52d04dc20036dbd8313ed055', 'zhaogang@email.com', 1800);
INSERT INTO `customer` VALUES (8, '13800138008', '周雪', '81dc9bdb52d04dc20036dbd8313ed055', 'zhouxue@email.com', 2200);
INSERT INTO `customer` VALUES (9, '13800138009', '吴勇', '81dc9bdb52d04dc20036dbd8313ed055', 'wuyong@email.com', 950);
INSERT INTO `customer` VALUES (10, '13800138010', '郑华', '81dc9bdb52d04dc20036dbd8313ed055', 'zhenghua@email.com', 3500);
INSERT INTO `customer` VALUES (11, '1234', '1234', '81dc9bdb52d04dc20036dbd8313ed055', '123', 2376000);
INSERT INTO `customer` VALUES (13, '12345', '1234', '81dc9bdb52d04dc20036dbd8313ed055', NULL, -1);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_stat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `spending` decimal(65, 2) NULL DEFAULT NULL,
  `booking_time` datetime NULL DEFAULT NULL,
  `airline_id` int NULL DEFAULT NULL,
  `seat_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `seat_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `customer_id` int NULL DEFAULT NULL,
  `citizen_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `citizen_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, '已退票', 1200.00, '2025-05-01 10:15:23', 1, 'S001', '头等舱', 1, '110101199001011234', '张伟');
INSERT INTO `order` VALUES (2, '已完成', 800.00, '2025-05-02 14:30:45', 2, 'S101', '商务舱', 2, '110101199002022345', '王芳');
INSERT INTO `order` VALUES (3, '已完成', 1500.00, '2025-05-03 09:20:12', 3, 'S201', '经济舱', 3, '110101199003033456', '李强');
INSERT INTO `order` VALUES (4, '已完成', 900.00, '2025-05-04 16:45:33', 4, 'S002', '头等舱', 4, '110101199004044567', '刘洋');
INSERT INTO `order` VALUES (5, '已完成', 1100.00, '2025-05-05 11:10:55', 5, 'S102', '商务舱', 5, '110101199005055678', '陈明');
INSERT INTO `order` VALUES (6, '已完成', 1300.00, '2025-05-06 13:25:18', 6, 'S202', '经济舱', 6, '110101199006066789', '杨丽');
INSERT INTO `order` VALUES (7, '已完成', 950.00, '2025-05-07 08:40:29', 7, 'S003', '头等舱', 7, '110101199007077890', '赵刚');
INSERT INTO `order` VALUES (8, '已完成', 850.00, '2025-05-08 15:55:47', 8, 'S103', '商务舱', 8, '110101199008088901', '周雪');
INSERT INTO `order` VALUES (9, '已完成', 1400.00, '2025-05-09 12:30:11', 9, 'S203', '经济舱', 9, '110101199009099012', '吴勇');
INSERT INTO `order` VALUES (10, '已完成', 750.00, '2025-05-10 17:20:39', 10, 'S004', '头等舱', 10, '110101199010100123', '郑华');
INSERT INTO `order` VALUES (11, '已预订', 1200.00, '2025-05-15 10:15:23', 1, 'S005', '头等舱', 1, '110101199001011234', '张伟');
INSERT INTO `order` VALUES (12, '已预订', 800.00, '2025-05-16 14:30:45', 2, 'S104', '商务舱', 2, '110101199002022345', '王芳');
INSERT INTO `order` VALUES (13, '已预订', 1500.00, '2025-05-17 09:20:12', 3, 'S204', '经济舱', 3, '110101199003033456', '李强');
INSERT INTO `order` VALUES (14, '已预订', 900.00, '2025-05-18 16:45:33', 4, 'S006', '头等舱', 4, '110101199004044567', '刘洋');
INSERT INTO `order` VALUES (15, '已预订', 1100.00, '2025-05-19 11:10:55', 5, 'S105', '商务舱', 5, '110101199005055678', '陈明');
INSERT INTO `order` VALUES (16, '已取消', 1300.00, '2025-05-20 13:25:18', 6, 'S205', '经济舱', 6, '110101199006066789', '杨丽');
INSERT INTO `order` VALUES (17, '已取消', 950.00, '2025-05-21 08:40:29', 7, 'S007', '头等舱', 7, '110101199007077890', '赵刚');
INSERT INTO `order` VALUES (18, '已取消', 850.00, '2025-05-22 15:55:47', 8, 'S106', '商务舱', 8, '110101199008088901', '周雪');
INSERT INTO `order` VALUES (19, '已预订', 1400.00, '2025-05-23 12:30:11', 9, 'S206', '经济舱', 9, '110101199009099012', '吴勇');
INSERT INTO `order` VALUES (20, '已预订', 750.00, '2025-05-24 17:20:39', 10, 'S008', '头等舱', 10, '110101199010100123', '郑华');
INSERT INTO `order` VALUES (21, '已退票', 750.00, '2025-05-24 17:20:39', 10, 'S008', '头等舱', 11, '110101199010100123', '郑华');
INSERT INTO `order` VALUES (26, '已退票', 1200.00, '2025-05-14 02:36:07', 1, 'CB1', '经济舱', 11, '1433223', '张三');
INSERT INTO `order` VALUES (40, '已支付', 700.00, '2025-05-24 22:39:30', 2, 'CE1', '经济舱', 2, '1433223', '李四');
INSERT INTO `order` VALUES (41, '已支付', 700.00, '2025-05-24 22:39:31', 2, 'CF1', '经济舱', 2, '1433223', '李四');
INSERT INTO `order` VALUES (42, '已支付', 700.00, '2025-05-24 22:39:34', 2, 'CG1', '经济舱', 2, '1433223', '李四');
INSERT INTO `order` VALUES (43, '已支付', 700.00, '2025-05-24 22:39:39', 2, 'CH1', '经济舱', 2, '1433223', '李四');
INSERT INTO `order` VALUES (44, '已支付', 700.00, '2025-05-24 22:39:39', 2, 'CI1', '经济舱', 2, '1433223', '李四');
INSERT INTO `order` VALUES (45, '已支付', 700.00, '2025-05-24 22:39:43', 2, 'CJ1', '经济舱', 2, '1433223', '李四');
INSERT INTO `order` VALUES (50, '已退票', 700.00, '2025-05-24 23:21:15', 1, 'C8-10', '经济舱', 4, '143332231', 'test');
INSERT INTO `order` VALUES (51, '已支付', 700.00, '2025-05-24 23:32:28', 2, 'CE2', '经济舱', 2, '1433223', '李四');
INSERT INTO `order` VALUES (52, '已支付', 679.00, '2025-05-25 01:40:55', 2, 'CF2', '经济舱', 4, '440181200406215119', '赵四');

SET FOREIGN_KEY_CHECKS = 1;
