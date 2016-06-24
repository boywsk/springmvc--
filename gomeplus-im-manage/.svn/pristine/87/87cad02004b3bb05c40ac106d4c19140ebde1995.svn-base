/*
Navicat MySQL Data Transfer

Source Server         : 10.125.31.220
Source Server Version : 50622
Source Host           : 10.125.31.220:3306
Source Database       : gomeplus_im_manage

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2016-06-03 15:45:29
*/

CREATE DATABASE IF NOT EXISTS gomeplus_im_manage DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE gomeplus_im_manage;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_menu
-- ----------------------------
DROP TABLE IF EXISTS `tbl_menu`;
CREATE TABLE `tbl_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(128) DEFAULT '',
  `url` varchar(256) DEFAULT '',
  `orderBy` bigint(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_menu
-- ----------------------------
INSERT INTO `tbl_menu` VALUES ('1', '0', '文件管理', '', '2');
INSERT INTO `tbl_menu` VALUES ('2', '1', '文件上传', '../file/upload.do', '1');
INSERT INTO `tbl_menu` VALUES ('3', '1', '文件列表', '../file/listUserFiles.do', '2');
INSERT INTO `tbl_menu` VALUES ('4', '1', '文件统计', '../file/preStatistics.do', '3');
INSERT INTO `tbl_menu` VALUES ('5', '1', '头像上传', '../upload_avatar.jsp', '4');
INSERT INTO `tbl_menu` VALUES ('6', '1', '头像列表', '../avatar/listAvatar.do', '5');
INSERT INTO `tbl_menu` VALUES ('7', '1', '头像统计', '../avatar/preStatistics.do', '6');
INSERT INTO `tbl_menu` VALUES ('8', '1', '旧头像列表', '../avatar/listAvatarOld.do', '7');
INSERT INTO `tbl_menu` VALUES ('9', '1', '旧头像统计', '../avatar/preStatisticsOld.do', '8');
INSERT INTO `tbl_menu` VALUES ('10', '1', '娱乐文件列表', '../forum/listForum.do', '9');
INSERT INTO `tbl_menu` VALUES ('20', '0', '离线文件管理', '', '1');
INSERT INTO `tbl_menu` VALUES ('21', '20', '文件管理', 'http://117.79.150.122:8083/file/listFile.do', '1');
INSERT INTO `tbl_menu` VALUES ('200', '0', '系统管理', '', '10');
INSERT INTO `tbl_menu` VALUES ('201', '200', '用户管理', '../user/listUser.do', '2');
INSERT INTO `tbl_menu` VALUES ('202', '200', '菜单管理', '../menu/listMenu.do', '3');
INSERT INTO `tbl_menu` VALUES ('203', '200', '修改密码', '../user/preEditPassword.do', '1');
INSERT INTO `tbl_menu` VALUES ('215', '0', '离线消息信息', '', '5');
INSERT INTO `tbl_menu` VALUES ('217', '215', '消息列表', '../message/groupMsgList.do', '1');
INSERT INTO `tbl_menu` VALUES ('218', '1', '文件上传_thumb', '../upload_1.jsp', '10');
INSERT INTO `tbl_menu` VALUES ('221', '215', '消息统计', '../message/groupMsgStatistics.do', '3');
INSERT INTO `tbl_menu` VALUES ('222', '1', '礼品上传', '../giftUpload.jsp', '11');
INSERT INTO `tbl_menu` VALUES ('223', '1', '礼品文件列表', '../gift/listGift.do', '12');
INSERT INTO `tbl_menu` VALUES ('225', '0', '群组信息', '', '3');
INSERT INTO `tbl_menu` VALUES ('227', '225', '群组信息', '../group/group.do', '1');
INSERT INTO `tbl_menu` VALUES ('229', '0', 'API-调试', '', '7');
INSERT INTO `tbl_menu` VALUES ('231', '229', 'api调试工具', '../api/test.do', '1');
INSERT INTO `tbl_menu` VALUES ('237', '0', '用户信息', '', '6');
INSERT INTO `tbl_menu` VALUES ('239', '237', '用户列表', '../online/online.do', '1');
INSERT INTO `tbl_menu` VALUES ('241', '229', 'BS-IM-AP调试工具', '../api/bsApi.do', '2');
INSERT INTO `tbl_menu` VALUES ('243', '0', '服务管理', '', '8');
INSERT INTO `tbl_menu` VALUES ('245', '243', '服务列表', '../server/logicServer.do', '1');
INSERT INTO `tbl_menu` VALUES ('247', '229', 'AppServer-Api测试', '../api/appApi.do', '3');
INSERT INTO `tbl_menu` VALUES ('249', '0', 'APP信息', '', '9');
INSERT INTO `tbl_menu` VALUES ('255', '249', 'APP信息列表', '../app/allAppInfoList.do', '3');
INSERT INTO `tbl_menu` VALUES ('261', '249', 'APP系统帐号列表', '../app/appSysAccountList.do', '6');
INSERT INTO `tbl_menu` VALUES ('263', '0', 'liuzhenhuantest', '', '11');
INSERT INTO `tbl_menu` VALUES ('265', '263', 'liuzhenhuanzi1', '123.com', '1');

-- ----------------------------
-- Table structure for tbl_test
-- ----------------------------
DROP TABLE IF EXISTS `tbl_test`;
CREATE TABLE `tbl_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_test
-- ----------------------------
INSERT INTO `tbl_test` VALUES ('1');

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(64) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `type` varchar(6) DEFAULT '2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('1', 'admin', 'admin', 'admin', '1403531965189', '1');

-- ----------------------------
-- Table structure for tbl_user_permit
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_permit`;
CREATE TABLE `tbl_user_permit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `menuId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2202 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user_permit
-- ----------------------------
INSERT INTO `tbl_user_permit` VALUES ('691', '11', '229');
INSERT INTO `tbl_user_permit` VALUES ('693', '11', '241');
INSERT INTO `tbl_user_permit` VALUES ('879', '19', '247');
INSERT INTO `tbl_user_permit` VALUES ('881', '19', '229');
INSERT INTO `tbl_user_permit` VALUES ('883', '19', '231');
INSERT INTO `tbl_user_permit` VALUES ('885', '19', '241');
INSERT INTO `tbl_user_permit` VALUES ('887', '21', '247');
INSERT INTO `tbl_user_permit` VALUES ('889', '21', '229');
INSERT INTO `tbl_user_permit` VALUES ('1159', '23', '229');
INSERT INTO `tbl_user_permit` VALUES ('1161', '23', '247');
INSERT INTO `tbl_user_permit` VALUES ('1163', '23', '249');
INSERT INTO `tbl_user_permit` VALUES ('1165', '23', '251');
INSERT INTO `tbl_user_permit` VALUES ('1167', '23', '253');
INSERT INTO `tbl_user_permit` VALUES ('1169', '15', '229');
INSERT INTO `tbl_user_permit` VALUES ('1171', '15', '247');
INSERT INTO `tbl_user_permit` VALUES ('1173', '15', '249');
INSERT INTO `tbl_user_permit` VALUES ('1175', '15', '251');
INSERT INTO `tbl_user_permit` VALUES ('1177', '15', '253');
INSERT INTO `tbl_user_permit` VALUES ('1485', '25', '247');
INSERT INTO `tbl_user_permit` VALUES ('1487', '25', '229');
INSERT INTO `tbl_user_permit` VALUES ('1535', '27', '249');
INSERT INTO `tbl_user_permit` VALUES ('1537', '27', '255');
INSERT INTO `tbl_user_permit` VALUES ('1539', '27', '257');
INSERT INTO `tbl_user_permit` VALUES ('1541', '27', '261');
INSERT INTO `tbl_user_permit` VALUES ('1907', '17', '1');
INSERT INTO `tbl_user_permit` VALUES ('1909', '17', '2');
INSERT INTO `tbl_user_permit` VALUES ('1911', '17', '3');
INSERT INTO `tbl_user_permit` VALUES ('1913', '17', '4');
INSERT INTO `tbl_user_permit` VALUES ('1915', '17', '5');
INSERT INTO `tbl_user_permit` VALUES ('1917', '17', '6');
INSERT INTO `tbl_user_permit` VALUES ('1919', '17', '7');
INSERT INTO `tbl_user_permit` VALUES ('1921', '17', '8');
INSERT INTO `tbl_user_permit` VALUES ('1923', '17', '9');
INSERT INTO `tbl_user_permit` VALUES ('1925', '17', '10');
INSERT INTO `tbl_user_permit` VALUES ('1927', '17', '218');
INSERT INTO `tbl_user_permit` VALUES ('1929', '17', '222');
INSERT INTO `tbl_user_permit` VALUES ('1931', '17', '223');
INSERT INTO `tbl_user_permit` VALUES ('1973', '6', '1');
INSERT INTO `tbl_user_permit` VALUES ('1975', '6', '2');
INSERT INTO `tbl_user_permit` VALUES ('1977', '6', '3');
INSERT INTO `tbl_user_permit` VALUES ('1979', '6', '225');
INSERT INTO `tbl_user_permit` VALUES ('1981', '6', '227');
INSERT INTO `tbl_user_permit` VALUES ('1983', '6', '215');
INSERT INTO `tbl_user_permit` VALUES ('1985', '6', '217');
INSERT INTO `tbl_user_permit` VALUES ('1987', '6', '221');
INSERT INTO `tbl_user_permit` VALUES ('1989', '6', '237');
INSERT INTO `tbl_user_permit` VALUES ('1991', '6', '239');
INSERT INTO `tbl_user_permit` VALUES ('1993', '6', '229');
INSERT INTO `tbl_user_permit` VALUES ('1995', '6', '231');
INSERT INTO `tbl_user_permit` VALUES ('1997', '6', '241');
INSERT INTO `tbl_user_permit` VALUES ('1999', '6', '249');
INSERT INTO `tbl_user_permit` VALUES ('2001', '6', '255');
INSERT INTO `tbl_user_permit` VALUES ('2003', '6', '261');
INSERT INTO `tbl_user_permit` VALUES ('2005', '6', '200');
INSERT INTO `tbl_user_permit` VALUES ('2007', '6', '203');
INSERT INTO `tbl_user_permit` VALUES ('2009', '6', '201');
INSERT INTO `tbl_user_permit` VALUES ('2011', '6', '202');
INSERT INTO `tbl_user_permit` VALUES ('2013', '6', '4');
INSERT INTO `tbl_user_permit` VALUES ('2015', '29', '229');
INSERT INTO `tbl_user_permit` VALUES ('2017', '29', '241');
INSERT INTO `tbl_user_permit` VALUES ('2019', '9', '225');
INSERT INTO `tbl_user_permit` VALUES ('2021', '9', '227');
INSERT INTO `tbl_user_permit` VALUES ('2023', '9', '215');
INSERT INTO `tbl_user_permit` VALUES ('2025', '9', '217');
INSERT INTO `tbl_user_permit` VALUES ('2027', '9', '221');
INSERT INTO `tbl_user_permit` VALUES ('2029', '9', '237');
INSERT INTO `tbl_user_permit` VALUES ('2031', '9', '239');
INSERT INTO `tbl_user_permit` VALUES ('2033', '9', '229');
INSERT INTO `tbl_user_permit` VALUES ('2035', '9', '231');
INSERT INTO `tbl_user_permit` VALUES ('2037', '9', '241');
INSERT INTO `tbl_user_permit` VALUES ('2039', '9', '247');
INSERT INTO `tbl_user_permit` VALUES ('2041', '9', '243');
INSERT INTO `tbl_user_permit` VALUES ('2043', '9', '245');
INSERT INTO `tbl_user_permit` VALUES ('2045', '9', '249');
INSERT INTO `tbl_user_permit` VALUES ('2047', '9', '255');
INSERT INTO `tbl_user_permit` VALUES ('2049', '9', '261');
INSERT INTO `tbl_user_permit` VALUES ('2051', '9', '200');
INSERT INTO `tbl_user_permit` VALUES ('2053', '9', '203');
INSERT INTO `tbl_user_permit` VALUES ('2055', '9', '201');
INSERT INTO `tbl_user_permit` VALUES ('2057', '9', '202');
INSERT INTO `tbl_user_permit` VALUES ('2059', '9', '20');
INSERT INTO `tbl_user_permit` VALUES ('2061', '9', '21');
INSERT INTO `tbl_user_permit` VALUES ('2063', '31', '241');
INSERT INTO `tbl_user_permit` VALUES ('2065', '31', '229');
INSERT INTO `tbl_user_permit` VALUES ('2067', '33', '241');
INSERT INTO `tbl_user_permit` VALUES ('2069', '33', '229');
INSERT INTO `tbl_user_permit` VALUES ('2071', '1', '225');
INSERT INTO `tbl_user_permit` VALUES ('2073', '1', '227');
INSERT INTO `tbl_user_permit` VALUES ('2075', '1', '215');
INSERT INTO `tbl_user_permit` VALUES ('2077', '1', '217');
INSERT INTO `tbl_user_permit` VALUES ('2079', '1', '221');
INSERT INTO `tbl_user_permit` VALUES ('2081', '1', '237');
INSERT INTO `tbl_user_permit` VALUES ('2083', '1', '239');
INSERT INTO `tbl_user_permit` VALUES ('2085', '1', '229');
INSERT INTO `tbl_user_permit` VALUES ('2087', '1', '231');
INSERT INTO `tbl_user_permit` VALUES ('2089', '1', '241');
INSERT INTO `tbl_user_permit` VALUES ('2091', '1', '247');
INSERT INTO `tbl_user_permit` VALUES ('2093', '1', '243');
INSERT INTO `tbl_user_permit` VALUES ('2095', '1', '245');
INSERT INTO `tbl_user_permit` VALUES ('2097', '1', '249');
INSERT INTO `tbl_user_permit` VALUES ('2099', '1', '255');
INSERT INTO `tbl_user_permit` VALUES ('2101', '1', '261');
INSERT INTO `tbl_user_permit` VALUES ('2103', '1', '200');
INSERT INTO `tbl_user_permit` VALUES ('2105', '1', '203');
INSERT INTO `tbl_user_permit` VALUES ('2107', '1', '201');
INSERT INTO `tbl_user_permit` VALUES ('2109', '1', '202');
INSERT INTO `tbl_user_permit` VALUES ('2111', '1', '2');
INSERT INTO `tbl_user_permit` VALUES ('2113', '1', '1');
INSERT INTO `tbl_user_permit` VALUES ('2115', '1', '3');
INSERT INTO `tbl_user_permit` VALUES ('2117', '1', '4');
INSERT INTO `tbl_user_permit` VALUES ('2119', '35', '241');
INSERT INTO `tbl_user_permit` VALUES ('2121', '35', '229');
INSERT INTO `tbl_user_permit` VALUES ('2123', '13', '229');
INSERT INTO `tbl_user_permit` VALUES ('2125', '13', '241');
INSERT INTO `tbl_user_permit` VALUES ('2127', '13', '249');
INSERT INTO `tbl_user_permit` VALUES ('2129', '13', '255');
INSERT INTO `tbl_user_permit` VALUES ('2131', '13', '261');
INSERT INTO `tbl_user_permit` VALUES ('2133', '37', '1');
INSERT INTO `tbl_user_permit` VALUES ('2135', '37', '2');
INSERT INTO `tbl_user_permit` VALUES ('2137', '37', '3');
INSERT INTO `tbl_user_permit` VALUES ('2139', '37', '4');
INSERT INTO `tbl_user_permit` VALUES ('2141', '37', '5');
INSERT INTO `tbl_user_permit` VALUES ('2143', '37', '6');
INSERT INTO `tbl_user_permit` VALUES ('2145', '37', '7');
INSERT INTO `tbl_user_permit` VALUES ('2147', '37', '8');
INSERT INTO `tbl_user_permit` VALUES ('2149', '37', '9');
INSERT INTO `tbl_user_permit` VALUES ('2151', '37', '10');
INSERT INTO `tbl_user_permit` VALUES ('2153', '37', '218');
INSERT INTO `tbl_user_permit` VALUES ('2155', '37', '222');
INSERT INTO `tbl_user_permit` VALUES ('2157', '37', '223');
INSERT INTO `tbl_user_permit` VALUES ('2159', '37', '20');
INSERT INTO `tbl_user_permit` VALUES ('2161', '37', '21');
INSERT INTO `tbl_user_permit` VALUES ('2163', '37', '225');
INSERT INTO `tbl_user_permit` VALUES ('2165', '37', '227');
INSERT INTO `tbl_user_permit` VALUES ('2167', '37', '215');
INSERT INTO `tbl_user_permit` VALUES ('2169', '37', '217');
INSERT INTO `tbl_user_permit` VALUES ('2171', '37', '221');
INSERT INTO `tbl_user_permit` VALUES ('2173', '37', '237');
INSERT INTO `tbl_user_permit` VALUES ('2175', '37', '239');
INSERT INTO `tbl_user_permit` VALUES ('2177', '37', '229');
INSERT INTO `tbl_user_permit` VALUES ('2179', '37', '231');
INSERT INTO `tbl_user_permit` VALUES ('2181', '37', '241');
INSERT INTO `tbl_user_permit` VALUES ('2183', '37', '247');
INSERT INTO `tbl_user_permit` VALUES ('2185', '37', '243');
INSERT INTO `tbl_user_permit` VALUES ('2187', '37', '245');
INSERT INTO `tbl_user_permit` VALUES ('2189', '37', '249');
INSERT INTO `tbl_user_permit` VALUES ('2191', '37', '255');
INSERT INTO `tbl_user_permit` VALUES ('2193', '37', '261');
INSERT INTO `tbl_user_permit` VALUES ('2195', '37', '200');
INSERT INTO `tbl_user_permit` VALUES ('2197', '37', '203');
INSERT INTO `tbl_user_permit` VALUES ('2199', '37', '201');
INSERT INTO `tbl_user_permit` VALUES ('2201', '37', '202');
