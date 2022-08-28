/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : security

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 28/08/2022 16:47:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'Dumb', 'Dumb');
INSERT INTO `users` VALUES (2, 'Angelina', 'I-kill-you');
INSERT INTO `users` VALUES (3, 'Dummy', 'p@ssword');
INSERT INTO `users` VALUES (4, 'secure', 'crappy');
INSERT INTO `users` VALUES (5, 'stupid', 'stupidity');
INSERT INTO `users` VALUES (6, 'superman', 'genious');
INSERT INTO `users` VALUES (7, 'batman', 'mob!le');
INSERT INTO `users` VALUES (8, 'admin', 'admin');
INSERT INTO `users` VALUES (9, 'admin1', 'admin1');
INSERT INTO `users` VALUES (10, 'admin2', 'admin2');
INSERT INTO `users` VALUES (11, 'admin3', 'admin3');
INSERT INTO `users` VALUES (12, 'dhakkan', 'dumbo');
INSERT INTO `users` VALUES (14, 'admin4', 'admin4');

SET FOREIGN_KEY_CHECKS = 1;
