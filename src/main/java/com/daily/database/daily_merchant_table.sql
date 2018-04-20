/*
Navicat MySQL Data Transfer

Source Server         : db
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : daily

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2017-07-25 10:52:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for daily_merchant_table
-- ----------------------------
DROP TABLE IF EXISTS `daily_merchant_table`;
CREATE TABLE `daily_merchant_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_no` varchar(11) DEFAULT NULL,
  `merchant_id` int(11) DEFAULT NULL,
  `book_status` int(3) DEFAULT NULL,
  `sit_real` int(3) DEFAULT NULL,
  `capacity` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of daily_merchant_table
-- ----------------------------
INSERT INTO `daily_merchant_table` VALUES ('1', '一号桌', '1', '1', '5', '4');
INSERT INTO `daily_merchant_table` VALUES ('2', '二号桌', '1', '0', '3', '10');
INSERT INTO `daily_merchant_table` VALUES ('3', '三号桌', '1', '0', '9', '8');
INSERT INTO `daily_merchant_table` VALUES ('5', '四号桌', null, null, '0', '3');
