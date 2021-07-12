/*
 Navicat MySQL Data Transfer

 Source Server         : LocalDB_mysql
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : medical_resource

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 10/07/2021 23:11:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `account_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'account_id',
  `user_id` int(10) NOT NULL COMMENT 'user_id',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户address',
  `public_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户public_key',
  `private_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户private_key',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`account_id`) USING BTREE,
  UNIQUE INDEX `address`(`address`) USING BTREE COMMENT '用户地址',
  INDEX `user_id`(`user_id`) USING BTREE COMMENT '用户id'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 1, '0x62007579518b94f1d2648271546b9e4151b4cbad', '04c1870853a7ade036b97fecb6a6cb990ab3ec017a80b33aef1f308c808e668088b70879eab18c179fd8d93a1a6d89dd16ff6cc34bf0dcac9b0e736278e8374daf', '53783c2cf5cd758b896433d6d901b44b4e51d3af0984de46164758dcbf553789', '2021-07-07 09:03:00', '2021-07-07 09:03:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户角色',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户省份',
  `register_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `update_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `token_ver` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'token版本',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `mail`(`mail`) USING BTREE COMMENT '邮箱'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zr@zrcode.top', '$2a$10$PkSImKcNeEV0oOqz/7FDiembQpWc7VK0euIiNwk5Ip8bZMNt1jA0y', 'god', 'god', '广东', '2021-07-07 09:01:58', '2021-07-07 09:01:58', 0);

SET FOREIGN_KEY_CHECKS = 1;
