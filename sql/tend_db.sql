/*
 Navicat Premium Data Transfer

 Source Server         : 本地docker容器
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 192.168.120.130:3306
 Source Schema         : tend_db

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 16/03/2025 20:09:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for database_tend
-- ----------------------------
DROP TABLE IF EXISTS `database_tend`;
CREATE TABLE `database_tend`  (
  `database_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`database_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of database_tend
-- ----------------------------
INSERT INTO `database_tend` VALUES ('app1', 'jdbc:mysql://192.168.120.130:3306/tend_db_1', 'root', '123456');
INSERT INTO `database_tend` VALUES ('app2', 'jdbc:mysql://192.168.120.130:3306/tend_db_2', 'root', '123456');

SET FOREIGN_KEY_CHECKS = 1;
