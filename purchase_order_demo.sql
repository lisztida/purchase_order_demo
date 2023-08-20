/*
 Navicat MySQL Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : purchase_order_demo

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 20/08/2023 23:15:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for demo_b_purchaseorder
-- ----------------------------
DROP TABLE IF EXISTS `demo_b_purchaseorder`;
CREATE TABLE `demo_b_purchaseorder`  (
  `PURCHASE_CODE` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `CREATE_DATE` date NULL DEFAULT NULL,
  `CREATE_BY` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `TOTAL_PRICE` decimal(10, 2) NOT NULL,
  `REMARK` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`PURCHASE_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of demo_b_purchaseorder
-- ----------------------------
INSERT INTO `demo_b_purchaseorder` VALUES ('CG_JQ2023081712345', '2023-08-17', 'admin', 800.00, NULL);
INSERT INTO `demo_b_purchaseorder` VALUES ('CG_JQ2023081812345', '2023-08-17', 'admin', 960.00, NULL);

-- ----------------------------
-- Table structure for demo_b_purchaseorderitem
-- ----------------------------
DROP TABLE IF EXISTS `demo_b_purchaseorderitem`;
CREATE TABLE `demo_b_purchaseorderitem`  (
  `PURCHASE_ORDER_ID` bigint NOT NULL AUTO_INCREMENT,
  `PURCHASE_CODE` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `SUPPLIER_CODE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PRODUCT_CODE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `NUMS` int NOT NULL,
  `TOTAL_PRICE` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`PURCHASE_ORDER_ID`) USING BTREE,
  INDEX `PURCHASE_CODE`(`PURCHASE_CODE`) USING BTREE,
  INDEX `SUPPLIER_CODE`(`SUPPLIER_CODE`) USING BTREE,
  INDEX `PRODUCT_CODE`(`PRODUCT_CODE`) USING BTREE,
  CONSTRAINT `demo_b_purchaseorderitem_ibfk_1` FOREIGN KEY (`PURCHASE_CODE`) REFERENCES `demo_b_purchaseorder` (`PURCHASE_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `demo_b_purchaseorderitem_ibfk_2` FOREIGN KEY (`SUPPLIER_CODE`) REFERENCES `demo_c_supplier` (`SUPPLIER_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `demo_b_purchaseorderitem_ibfk_3` FOREIGN KEY (`PRODUCT_CODE`) REFERENCES `demo_c_product` (`PRODUCT_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of demo_b_purchaseorderitem
-- ----------------------------
INSERT INTO `demo_b_purchaseorderitem` VALUES (1, 'CG_JQ2023081712345', 'SP202105', 'SKU202105', 8, 400.00);
INSERT INTO `demo_b_purchaseorderitem` VALUES (2, 'CG_JQ2023081712345', 'SP202106', 'SKU202107', 8, 400.00);
INSERT INTO `demo_b_purchaseorderitem` VALUES (10, 'CG_JQ2023081812345', 'SP202105', 'SKU202106', 8, 480.00);
INSERT INTO `demo_b_purchaseorderitem` VALUES (11, 'CG_JQ2023081812345', 'SP202107', 'SKU202108', 6, 480.00);

-- ----------------------------
-- Table structure for demo_c_product
-- ----------------------------
DROP TABLE IF EXISTS `demo_c_product`;
CREATE TABLE `demo_c_product`  (
  `PRODUCT_CODE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PRODUCT_NAME` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `SUPPLIER_CODE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PRODUCT_CATEGORY` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `SPEC_TYPE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `UNIT` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PRICE` decimal(5, 2) NOT NULL,
  PRIMARY KEY (`PRODUCT_CODE`) USING BTREE,
  INDEX `SUPPLIER_CODE`(`SUPPLIER_CODE`) USING BTREE,
  CONSTRAINT `demo_c_product_ibfk_1` FOREIGN KEY (`SUPPLIER_CODE`) REFERENCES `demo_c_supplier` (`SUPPLIER_CODE`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of demo_c_product
-- ----------------------------
INSERT INTO `demo_c_product` VALUES ('SKU202105', '花牛', 'SP202105', '生鲜', 'DEL-1023', '箱', 50.00);
INSERT INTO `demo_c_product` VALUES ('SKU202106', '国光', 'SP202105', '生鲜', 'DEL-1024', '箱', 60.00);
INSERT INTO `demo_c_product` VALUES ('SKU202107', '富士', 'SP202106', '生鲜', 'DEL-1025', '箱', 50.00);
INSERT INTO `demo_c_product` VALUES ('SKU202108', '佳士', 'SP202107', '生鲜', 'DEL-1025', '箱', 80.00);

-- ----------------------------
-- Table structure for demo_c_supplier
-- ----------------------------
DROP TABLE IF EXISTS `demo_c_supplier`;
CREATE TABLE `demo_c_supplier`  (
  `SUPPLIER_CODE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `SUPPLIER_NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ADDRESS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `CONACTOR` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PHONE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`SUPPLIER_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of demo_c_supplier
-- ----------------------------
INSERT INTO `demo_c_supplier` VALUES ('SP202105', '九七果蔬农贸有限公司1', '马驹桥3号院111', '光滑2', '03163134225');
INSERT INTO `demo_c_supplier` VALUES ('SP202106', '九七果蔬农贸有限公司2', '马驹桥3号院112', '光滑4', '03163134226');
INSERT INTO `demo_c_supplier` VALUES ('SP202107', '九七果蔬农贸有限公司3', '马驹桥3号院113', '光滑5', '03163134227');

SET FOREIGN_KEY_CHECKS = 1;
