/*
 Navicat Premium Data Transfer

 Source Server         : 本地docker容器
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 192.168.120.130:3306
 Source Schema         : tend_db_1

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 16/03/2025 20:09:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user_grade
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_grade`;
CREATE TABLE `sys_user_grade`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `sex` tinyint(4) NULL DEFAULT NULL,
  `grade` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_grade
-- ----------------------------
INSERT INTO `sys_user_grade` VALUES (1, '小红', 1, 1, 2323.00);

SET FOREIGN_KEY_CHECKS = 1;
