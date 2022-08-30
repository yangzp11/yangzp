/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : yzp

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 30/08/2022 14:12:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_clothes
-- ----------------------------
DROP TABLE IF EXISTS `order_clothes`;
CREATE TABLE `order_clothes`  (
  `clothes_id` int NOT NULL COMMENT '衣物ID',
  `order_clothes_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `clothes_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '衣物号',
  `clothes_status` int NOT NULL COMMENT '衣物状态',
  `clothes_name` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '衣物名称',
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单主键',
  PRIMARY KEY (`order_clothes_id`) USING BTREE,
  INDEX `idx_clothes_num`(`clothes_num`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单衣物表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_clothes_operate
-- ----------------------------
DROP TABLE IF EXISTS `order_clothes_operate`;
CREATE TABLE `order_clothes_operate`  (
  `operate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `clothes_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `operate_status` int NOT NULL,
  `operate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`operate_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单衣物操作记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_clothes_rewash
-- ----------------------------
DROP TABLE IF EXISTS `order_clothes_rewash`;
CREATE TABLE `order_clothes_rewash`  (
  `rewash_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `clothes_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rewash_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`rewash_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单衣物反洗表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_amount` decimal(8, 2) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
