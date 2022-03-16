/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : graduation_project

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 16/03/2022 09:41:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for group_material
-- ----------------------------
DROP TABLE IF EXISTS `group_material`;
CREATE TABLE `group_material`  (
  `group_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作组ID',
  `material_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '素材ID',
  `group_material_type` int(1) NULL DEFAULT NULL COMMENT '1代表材料、2代表成品',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者ID',
  PRIMARY KEY (`group_id`, `material_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_material
-- ----------------------------
INSERT INTO `group_material` VALUES ('1642756420812', '1643007203666', 1, '2022-01-24 14:53:24', '1642489492053');
INSERT INTO `group_material` VALUES ('1642756420812', '1646398481851', 1, '2022-03-04 20:54:42', '1642489492053');
INSERT INTO `group_material` VALUES ('1642756420812', '1646398996212', 2, '2022-03-04 21:03:16', '1642489492053');
INSERT INTO `group_material` VALUES ('1643243923122', '1642863701602', 1, '2022-02-13 14:28:02', '1642489492053');
INSERT INTO `group_material` VALUES ('1643243923122', '1644224261292', 1, '2022-02-13 13:51:49', '1643009560056');
INSERT INTO `group_material` VALUES ('1643243923122', '1644241331165', 1, '2022-02-07 21:42:12', '1643009560056');
INSERT INTO `group_material` VALUES ('1643243923122', '1647221079833', 1, '2022-03-14 09:24:40', '1643009560056');
INSERT INTO `group_material` VALUES ('1643243923122', '1647221205207', 1, '2022-03-14 09:26:45', '1643009560056');
INSERT INTO `group_material` VALUES ('1643243923122', '1647221353058', 2, '2022-03-14 09:29:13', '1643009560056');
INSERT INTO `group_material` VALUES ('1643243923122', '1647353182439', 2, '2022-03-15 22:06:23', '1643009560056');

-- ----------------------------
-- Table structure for group_message
-- ----------------------------
DROP TABLE IF EXISTS `group_message`;
CREATE TABLE `group_message`  (
  `message_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息ID',
  `originator_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起方ID',
  `receiver_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收方ID',
  `group_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邀请加入工作组ID',
  `remarks` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邀请备注',
  `message_status` int(1) NULL DEFAULT NULL COMMENT '消息状态，1为待验证，2为同意，3为拒绝',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_message
-- ----------------------------
INSERT INTO `group_message` VALUES ('1644394355874', '1643009560056', '1642489492053', '1642756420812', '请求加入', 1);
INSERT INTO `group_message` VALUES ('1644483136815', '1643009560056', '1643163474195', '1643243923122', '邀请您加入', 1);
INSERT INTO `group_message` VALUES ('1644484051588', '1643009560056', '1643098646972', '1643243923122', '邀请您加入', 1);

-- ----------------------------
-- Table structure for group_user
-- ----------------------------
DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user`  (
  `group_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作组ID',
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`group_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_user
-- ----------------------------
INSERT INTO `group_user` VALUES ('1642756420812', '1642489492053', '2022-02-09 16:04:20');
INSERT INTO `group_user` VALUES ('1643243923122', '1642489492053', '2022-02-10 21:20:42');
INSERT INTO `group_user` VALUES ('1643243923122', '1643009560056', '2022-01-27 08:38:43');
INSERT INTO `group_user` VALUES ('1643250131568', '1643009560056', '2022-01-27 10:22:12');
INSERT INTO `group_user` VALUES ('1644398393798', '1643009560056', '2022-02-09 17:19:54');

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material`  (
  `material_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '素材ID',
  `material_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材名称',
  `material_urls` json NULL COMMENT '存放json,包含原图、mask、前景图',
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`material_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES ('1642499941387', '测试素材', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-18/3b7dc5d4-ce1f-44a1-9cd0-71c260481f93.jpg\"}', '1642489492053', '2022-01-18 17:59:01');
INSERT INTO `material` VALUES ('1642578070644', '背景虚化测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-19/9845e820-24df-449e-bbbc-32fa27904651.jpg\"}', '1642489492053', '2022-01-19 15:41:11');
INSERT INTO `material` VALUES ('1642863701602', '背景虚化测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-22/8614490e-daa2-4275-8dd2-0cc8892ba727.jpg\"}', '1642489492053', '2022-01-22 23:01:42');
INSERT INTO `material` VALUES ('1642863767265', '背景虚化测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-22/633ae4f1-f936-4f7f-94fb-eb82a3e0c023.jpg\"}', '1642489492053', '2022-01-22 23:02:47');
INSERT INTO `material` VALUES ('1643007203666', '测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-24/5d9c2741-256a-446c-b487-384b0173f7b5.jpg\"}', '1642489492053', '2022-01-24 14:53:24');
INSERT INTO `material` VALUES ('1644224261292', '测试素材', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-07/2e4ae254-e09e-41cd-9d30-04ba2ed744c5.jpg\"}', '1643009560056', '2022-02-07 16:57:41');
INSERT INTO `material` VALUES ('1644241331165', '前端测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-07/4dbb0b61-b764-4f7d-a956-92dc204ccf0a.jpg\"}', '1643009560056', '2022-02-07 21:42:12');
INSERT INTO `material` VALUES ('1644308525043', '素材测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-24/5d9c2741-256a-446c-b487-384b0173f7b5.jpg\"}', '1643009560056', '2022-02-08 16:22:05');
INSERT INTO `material` VALUES ('1644741571290', '我的上传', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-13/59ddda2e-0928-4005-b9a0-7fc9724d3253.jpg\"}', '1643009560056', '2022-02-13 16:39:31');
INSERT INTO `material` VALUES ('1644903594493', '公共素材测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-15/a91868c1-285e-4969-a59a-d49b31fdbd8b.jpg\"}', '1643009560056', '2022-02-16 22:03:49');
INSERT INTO `material` VALUES ('1646039180183', '人脸美颜测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/e7d2180a-ac43-4335-9b87-eeff90fa734b.jpg\"}', '1642489492053', '2022-02-28 17:06:20');
INSERT INTO `material` VALUES ('1646041339857', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/d8e707ee-8574-4689-a875-87a590099fbf.jpg\"}', '1642489492053', '2022-02-28 17:42:20');
INSERT INTO `material` VALUES ('1646041818476', '增强测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/54609ae8-28da-479f-9ad8-4723633dc7a3.jpg\"}', '1642489492053', '2022-02-28 17:50:18');
INSERT INTO `material` VALUES ('1646042317030', '背景虚化', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/2309ce5c-7bc6-4881-a184-afbd47ba2bac.jpg\"}', '1642489492053', '2022-02-28 17:58:37');
INSERT INTO `material` VALUES ('1646052871712', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/a77534e1-65d0-4f3c-9afb-1b2cccbc1bc7.jpg\"}', '1642489492053', '2022-02-28 20:54:32');
INSERT INTO `material` VALUES ('1646053331278', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/cbc1ea10-ea4f-4eab-8984-c22c2993cdc5.jpg\"}', '1642489492053', '2022-02-28 21:02:11');
INSERT INTO `material` VALUES ('1646053331387', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/c5aa252d-9084-4e82-8d7c-b7efc6482687.jpg\"}', '1642489492053', '2022-02-28 21:02:11');
INSERT INTO `material` VALUES ('1646053332088', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/d47cb006-af32-45a8-95da-2c43e187a23f.jpg\"}', '1642489492053', '2022-02-28 21:02:12');
INSERT INTO `material` VALUES ('1646053357577', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/9e78dc65-8298-44ab-8911-825e18705ef4.jpg\"}', '1642489492053', '2022-02-28 21:02:38');
INSERT INTO `material` VALUES ('1646053460395', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/c5e9e295-4e9d-4734-bebc-49ab409245ff.jpg\"}', '1642489492053', '2022-02-28 21:04:20');
INSERT INTO `material` VALUES ('1646053460476', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/772a0dd9-5e1b-4836-a4f6-347c8ac39441.jpg\"}', '1642489492053', '2022-02-28 21:04:20');
INSERT INTO `material` VALUES ('1646053754349', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/7cb5d79e-54e1-4edd-b519-4c023cb7c1e6.jpg\"}', '1642489492053', '2022-02-28 21:09:14');
INSERT INTO `material` VALUES ('1646053818131', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/6b761875-d506-47ed-ae2a-1d9178545570.jpg\"}', '1642489492053', '2022-02-28 21:10:18');
INSERT INTO `material` VALUES ('1646054040333', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/512d9bdf-e512-439f-af8f-ccbb44d00b0a.jpg\"}', '1642489492053', '2022-02-28 21:14:00');
INSERT INTO `material` VALUES ('1646054041372', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/2e95a428-c6c3-4c77-b178-0e1f4eeb7409.jpg\"}', '1642489492053', '2022-02-28 21:14:01');
INSERT INTO `material` VALUES ('1646054325803', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/f7ebf175-6fd2-4443-a653-d792056a956a.jpg\"}', '1642489492053', '2022-02-28 21:18:46');
INSERT INTO `material` VALUES ('1646055067381', '测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/3a1bbd73-4b17-4e19-83e3-b7c7aeb7471f.jpg\"}', '1642489492053', '2022-02-28 21:31:07');
INSERT INTO `material` VALUES ('1646055068063', '测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/d92fcf6c-0b11-4ef0-aa9a-cb14fff179f1.jpg\"}', '1642489492053', '2022-02-28 21:31:08');
INSERT INTO `material` VALUES ('1646055069298', '测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/9e368e07-e221-4c8e-ac74-9b59abf19ab3.jpg\"}', '1642489492053', '2022-02-28 21:31:09');
INSERT INTO `material` VALUES ('1646055090128', '浩哥', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/9f8661b2-ad72-4f35-882f-b98b0f241bfc.jpg\"}', '1642489492053', '2022-02-28 21:31:30');
INSERT INTO `material` VALUES ('1646055090700', '浩哥', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/fd36cf37-fd45-4f9a-aede-6386785d0074.jpg\"}', '1642489492053', '2022-02-28 21:31:31');
INSERT INTO `material` VALUES ('1646055121301', '123', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/915a5d76-0aef-4821-9e94-eb366d04805c.jpg\"}', '1642489492053', '2022-02-28 21:32:01');
INSERT INTO `material` VALUES ('1646055122308', '123', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/167d5fb9-bf59-4110-8680-9e21232c625b.jpg\"}', '1642489492053', '2022-02-28 21:32:02');
INSERT INTO `material` VALUES ('1646055122980', '123', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/2d13a57f-b64b-433a-99e5-c6965f90894f.jpg\"}', '1642489492053', '2022-02-28 21:32:03');
INSERT INTO `material` VALUES ('1646055123113', '123', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/f846985e-2f01-4320-96f7-398e1c3510d0.jpg\"}', '1642489492053', '2022-02-28 21:32:03');
INSERT INTO `material` VALUES ('1646055142500', '测试虚化', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/44a4e075-5053-468a-843a-87661b57869d.jpg\"}', '1642489492053', '2022-02-28 21:32:23');
INSERT INTO `material` VALUES ('1646055142701', '测试虚化', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/be883782-e35e-4f7c-85fb-3f9a1c61428a.jpg\"}', '1642489492053', '2022-02-28 21:32:23');
INSERT INTO `material` VALUES ('1646055142884', '测试虚化', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/af67cd25-46e2-421a-afd1-ef87805beb67.jpg\"}', '1642489492053', '2022-02-28 21:32:23');
INSERT INTO `material` VALUES ('1646055145949', '测试虚化', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-28/ec70821b-ebd3-4c1c-a29a-3db24dd1c1b5.jpg\"}', '1642489492053', '2022-02-28 21:32:26');
INSERT INTO `material` VALUES ('1646355241323', '背景图', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-04/6d0dfd3b-df6f-4d64-aa0c-19ce34a57394.jpg\"}', '1642489492053', '2022-03-04 08:54:01');
INSERT INTO `material` VALUES ('1646398481851', '合成素材', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-04/a71188dc-c6c6-44ee-9d85-b76f4e68e288.jpg\"}', '1642489492053', '2022-03-04 20:54:42');
INSERT INTO `material` VALUES ('1646398996212', '测试11', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-04/05315009-f545-46e9-b8d7-cb2f9b3af677.jpg\"}', '1642489492053', '2022-03-04 21:03:16');
INSERT INTO `material` VALUES ('1646629790789', '搜索测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/c6486b2b-983a-4641-b358-18fe1e01efb6.jpg\"}', '1642489492053', '2022-03-07 13:09:51');
INSERT INTO `material` VALUES ('1646629813755', '222', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/84b08617-69a0-44f1-8ddf-562f5a44b74a.jpg\"}', '1642489492053', '2022-03-07 13:10:14');
INSERT INTO `material` VALUES ('1646629813835', '222', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/cc9dfed0-acea-4cb2-b3ea-efca7e983606.jpg\"}', '1642489492053', '2022-03-07 13:10:14');
INSERT INTO `material` VALUES ('1646650209830', '鹅卵石', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/1e594035-7ab9-416c-8996-bdc4cfc99da3.jpg\"}', '1642489492053', '2022-03-07 18:50:10');
INSERT INTO `material` VALUES ('1646650209845', '鹅卵石', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/a9fcddb4-eebe-4f21-a104-bdba19291843.jpg\"}', '1642489492053', '2022-03-07 18:50:10');
INSERT INTO `material` VALUES ('1646650209890', '鹅卵石', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/268b46e2-6b3b-4a0e-8b70-9fca8dbc87e1.jpg\"}', '1642489492053', '2022-03-07 18:50:10');
INSERT INTO `material` VALUES ('1646650210037', '鹅卵石', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/05205ace-369e-4da8-b59a-9cf9e516c787.jpg\"}', '1642489492053', '2022-03-07 18:50:10');
INSERT INTO `material` VALUES ('1646650238637', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/c6820095-6d3c-4847-aac9-594599aff7c0.jpg\"}', '1642489492053', '2022-03-07 18:50:39');
INSERT INTO `material` VALUES ('1646650238671', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/4669ca98-0d17-49b6-b7cb-87cfb14cf9e0.jpg\"}', '1642489492053', '2022-03-07 18:50:39');
INSERT INTO `material` VALUES ('1646650238691', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/31008795-8bdb-462c-8920-a6b5de8067bc.jpg\"}', '1642489492053', '2022-03-07 18:50:39');
INSERT INTO `material` VALUES ('1646650238803', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/13e60998-0cd0-457c-80a1-0ef36df05641.jpg\"}', '1642489492053', '2022-03-07 18:50:39');
INSERT INTO `material` VALUES ('1646650242156', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/3f36376f-bd87-4ddc-ac8e-e792cd763969.jpg\"}', '1642489492053', '2022-03-07 18:50:42');
INSERT INTO `material` VALUES ('1646752848443', '未命名', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-08/47c4769e-e0c4-4060-aa78-5ce3a3db83e6.jpg\"}', '1643009560056', '2022-03-08 23:20:48');
INSERT INTO `material` VALUES ('1647071460646', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-12/9d847155-bb0b-4314-9202-a1b7e4668cc2.jpg\"}', '1642489492053', '2022-03-12 15:51:01');
INSERT INTO `material` VALUES ('1647071516863', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-12/79e2aa4c-8a96-4b53-b0a0-cca46c76a505.jpg\"}', '1642489492053', '2022-03-12 15:51:57');
INSERT INTO `material` VALUES ('1647071542471', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-12/fadc2928-eca1-4aec-94b0-1a6f1181f885.jpg\"}', '1642489492053', '2022-03-12 15:52:22');
INSERT INTO `material` VALUES ('1647172897100', '人脸美颜', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/fc1de09f-a03c-4c65-a218-0f3738554a1a.jpg\"}', '1643009560056', '2022-03-13 20:01:37');
INSERT INTO `material` VALUES ('1647173314215', '美颜', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/c453295e-1725-44f5-960f-320947b581f1.jpg\"}', '1643009560056', '2022-03-13 20:08:34');
INSERT INTO `material` VALUES ('1647173803303', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/6f98f4d9-1dec-44a6-816d-18291f4247a1.jpg\"}', '1643009560056', '2022-03-13 20:16:43');
INSERT INTO `material` VALUES ('1647173805998', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/76fa5ef8-9317-49fa-9532-e7c641e500cc.jpg\"}', '1643009560056', '2022-03-13 20:16:46');
INSERT INTO `material` VALUES ('1647173831852', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/1e4aedb0-5959-4ec6-830b-97829dbac53f.jpg\"}', '1643009560056', '2022-03-13 20:17:12');
INSERT INTO `material` VALUES ('1647173833595', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/51fd790a-b7f9-41de-a82d-356e14e463f1.jpg\"}', '1643009560056', '2022-03-13 20:17:14');
INSERT INTO `material` VALUES ('1647173835386', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/e3f39be9-0ff3-4e85-aa5e-e8ee16f7fd5c.jpg\"}', '1643009560056', '2022-03-13 20:17:15');
INSERT INTO `material` VALUES ('1647173847965', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/7d085a49-806d-4959-b74d-86d3b7f0cfa8.jpg\"}', '1643009560056', '2022-03-13 20:17:28');
INSERT INTO `material` VALUES ('1647173849690', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/eaab9dc6-ec5a-4631-8197-f834097f897e.jpg\"}', '1643009560056', '2022-03-13 20:17:30');
INSERT INTO `material` VALUES ('1647173849987', '增强', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/606cebf3-e8a3-4c16-a917-53f750ee5b57.jpg\"}', '1643009560056', '2022-03-13 20:17:30');
INSERT INTO `material` VALUES ('1647175520458', '测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/c71c7d94-6bfa-4a3e-a814-905ce66609d0.jpg\"}', '1643009560056', '2022-03-13 20:45:20');
INSERT INTO `material` VALUES ('1647175522171', '测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/9ad51391-2c35-43e2-aa31-4a9cdf7bfe4d.jpg\"}', '1643009560056', '2022-03-13 20:45:22');
INSERT INTO `material` VALUES ('1647175522991', '测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/8fc18a62-c2a7-4ece-a23f-cdba0fe980d1.jpg\"}', '1643009560056', '2022-03-13 20:45:23');
INSERT INTO `material` VALUES ('1647175526295', '测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/043b0990-9e2d-4fc0-aee2-fa2af52f9b6c.jpg\"}', '1643009560056', '2022-03-13 20:45:26');
INSERT INTO `material` VALUES ('1647184288228', '证件照', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/beb64717-8bc3-47ba-93a0-d71dea31db94.jpg\"}', '1643009560056', '2022-03-13 23:11:28');
INSERT INTO `material` VALUES ('1647184290680', '证件照', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/f0722920-3283-440c-9969-dcb62c0bc8f1.jpg\"}', '1643009560056', '2022-03-13 23:11:31');
INSERT INTO `material` VALUES ('1647184293412', '证件照', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/944dfa35-3657-4e29-a0fd-c956b89e1901.jpg\"}', '1643009560056', '2022-03-13 23:11:33');
INSERT INTO `material` VALUES ('1647184299822', '证件照', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/dc829a03-f909-4213-b83f-53e2ada4a40c.jpg\"}', '1643009560056', '2022-03-13 23:11:40');
INSERT INTO `material` VALUES ('1647184300040', '证件照', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/73a9b23f-891c-4e2e-bdb3-1c7c101df3f5.jpg\"}', '1643009560056', '2022-03-13 23:11:40');
INSERT INTO `material` VALUES ('1647185483345', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/e2513d25-9cd8-498b-9d8b-47bf909f899a.jpg\"}', '1643009560056', '2022-03-13 23:31:23');
INSERT INTO `material` VALUES ('1647185483527', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/dc024c60-07b8-478c-8a77-6ee7a75f2ce4.jpg\"}', '1643009560056', '2022-03-13 23:31:24');
INSERT INTO `material` VALUES ('1647185487116', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/1dc395fd-5f97-4957-a9ca-f1a1da6a45f1.jpg\"}', '1643009560056', '2022-03-13 23:31:27');
INSERT INTO `material` VALUES ('1647185487237', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/9168a717-508f-4b34-830e-25d1d488eef7.jpg\"}', '1643009560056', '2022-03-13 23:31:27');
INSERT INTO `material` VALUES ('1647185493256', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/03d7bd43-940d-47a4-8ee7-63ae8d98233a.jpg\"}', '1643009560056', '2022-03-13 23:31:33');
INSERT INTO `material` VALUES ('1647185493267', '1', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-13/92f770f9-086a-4ab5-960d-e7f7b429fb8c.jpg\"}', '1643009560056', '2022-03-13 23:31:33');
INSERT INTO `material` VALUES ('1647220877166', 'bg', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-14/08098ba6-46df-4fe8-8bef-b694e658fa61.jpg\"}', '1643009560056', '2022-03-14 09:21:17');
INSERT INTO `material` VALUES ('1647221079833', '前景', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-14/db2d8ec2-28fb-46ba-a3e7-541fd087b9ae.jpg\"}', '1643009560056', '2022-03-14 09:24:40');
INSERT INTO `material` VALUES ('1647221205207', '前景', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-14/84d66c76-0d4f-4d56-8196-b7e78800f39e.jpg\"}', '1643009560056', '2022-03-14 09:26:45');
INSERT INTO `material` VALUES ('1647221353058', '合成', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-14/f73ebe4c-1e6e-470b-ab49-6980bb62a4dd.jpg\"}', '1643009560056', '2022-03-14 09:29:13');
INSERT INTO `material` VALUES ('1647353182439', '搜索测试', '{\"originUrl\": \"https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-15/10f38079-1d15-4746-8393-f7e47b62489e.jpg\"}', '1643009560056', '2022-03-15 22:06:23');

-- ----------------------------
-- Table structure for material_user
-- ----------------------------
DROP TABLE IF EXISTS `material_user`;
CREATE TABLE `material_user`  (
  `material_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '素材ID',
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `material_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材名称',
  `remarks` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材备注',
  PRIMARY KEY (`material_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of material_user
-- ----------------------------
INSERT INTO `material_user` VALUES ('1642499941387', '1642489492053', '2022-01-18 17:59:01', '测试1', '修改测试备注132222');
INSERT INTO `material_user` VALUES ('1642578070644', '1642489492053', '2022-01-21 11:40:54', '测试1', '修改测试备注13');
INSERT INTO `material_user` VALUES ('1642863701602', '1642489492053', '2022-01-22 23:01:42', '测试11222222', '修改测试备注131');
INSERT INTO `material_user` VALUES ('1642863767265', '1642489492053', '2022-01-22 23:02:47', '测试11222222', '修改测试备注131');
INSERT INTO `material_user` VALUES ('1644224261292', '1643009560056', '2022-02-07 16:57:41', '测试素材', '未描述');
INSERT INTO `material_user` VALUES ('1644308525043', '1643009560056', '2022-02-08 16:22:05', '测试素材', '测试素材描述');
INSERT INTO `material_user` VALUES ('1644741571290', '1643009560056', '2022-02-13 16:39:31', '我的上传', '描述测试1');
INSERT INTO `material_user` VALUES ('1644903594493', '1643009560056', '2022-02-16 22:03:49', '转入测试', '转入测试描述');
INSERT INTO `material_user` VALUES ('1646041818476', '1642489492053', '2022-02-28 17:50:18', '增强测试', '121');
INSERT INTO `material_user` VALUES ('1646042317030', '1642489492053', '2022-02-28 17:58:37', '背景虚化', '背景虚化');
INSERT INTO `material_user` VALUES ('1646055142884', '1642489492053', '2022-02-28 21:32:23', '测试虚化', '1321');
INSERT INTO `material_user` VALUES ('1646055145949', '1642489492053', '2022-02-28 21:32:26', '测试虚化', '1321');
INSERT INTO `material_user` VALUES ('1646355241323', '1642489492053', '2022-03-04 08:54:01', '背景图', '背景图描述');
INSERT INTO `material_user` VALUES ('1646752848443', '1643009560056', '2022-03-08 23:20:48', '未命名', '未描述');
INSERT INTO `material_user` VALUES ('1647071460646', '1642489492053', '2022-03-12 15:51:01', '1', '1');
INSERT INTO `material_user` VALUES ('1647071516863', '1642489492053', '2022-03-12 15:51:57', '1', '1');
INSERT INTO `material_user` VALUES ('1647071542471', '1642489492053', '2022-03-12 15:52:22', '1', '1');
INSERT INTO `material_user` VALUES ('1647172897100', '1643009560056', '2022-03-13 20:01:37', '人脸美颜', '未描述');
INSERT INTO `material_user` VALUES ('1647173314215', '1643009560056', '2022-03-13 20:08:34', '美颜', '测试');
INSERT INTO `material_user` VALUES ('1647173803303', '1643009560056', '2022-03-13 20:16:43', '增强', '增强');
INSERT INTO `material_user` VALUES ('1647173805998', '1643009560056', '2022-03-13 20:16:46', '增强', '增强');
INSERT INTO `material_user` VALUES ('1647173831852', '1643009560056', '2022-03-13 20:17:12', '增强', '增强');
INSERT INTO `material_user` VALUES ('1647173833595', '1643009560056', '2022-03-13 20:17:14', '增强', '增强');
INSERT INTO `material_user` VALUES ('1647173835386', '1643009560056', '2022-03-13 20:17:15', '增强', '增强');
INSERT INTO `material_user` VALUES ('1647173847965', '1643009560056', '2022-03-13 20:17:28', '增强', '增强');
INSERT INTO `material_user` VALUES ('1647173849690', '1643009560056', '2022-03-13 20:17:30', '增强', '增强');
INSERT INTO `material_user` VALUES ('1647173849987', '1643009560056', '2022-03-13 20:17:30', '增强', '增强');
INSERT INTO `material_user` VALUES ('1647175520458', '1643009560056', '2022-03-13 20:45:20', '测试', '测试');
INSERT INTO `material_user` VALUES ('1647175522171', '1643009560056', '2022-03-13 20:45:22', '测试', '测试');
INSERT INTO `material_user` VALUES ('1647175522991', '1643009560056', '2022-03-13 20:45:23', '测试', '测试');
INSERT INTO `material_user` VALUES ('1647175526295', '1643009560056', '2022-03-13 20:45:26', '测试', '测试');
INSERT INTO `material_user` VALUES ('1647184288228', '1643009560056', '2022-03-13 23:11:28', '证件照', '证件照');
INSERT INTO `material_user` VALUES ('1647184290680', '1643009560056', '2022-03-13 23:11:31', '证件照', '证件照');
INSERT INTO `material_user` VALUES ('1647184293412', '1643009560056', '2022-03-13 23:11:33', '证件照', '证件照');
INSERT INTO `material_user` VALUES ('1647184299822', '1643009560056', '2022-03-13 23:11:40', '证件照', '证件照');
INSERT INTO `material_user` VALUES ('1647184300040', '1643009560056', '2022-03-13 23:11:40', '证件照', '证件照');
INSERT INTO `material_user` VALUES ('1647185483345', '1643009560056', '2022-03-13 23:31:23', '1', '1');
INSERT INTO `material_user` VALUES ('1647185483527', '1643009560056', '2022-03-13 23:31:24', '1', '1');
INSERT INTO `material_user` VALUES ('1647185487116', '1643009560056', '2022-03-13 23:31:27', '1', '1');
INSERT INTO `material_user` VALUES ('1647185487237', '1643009560056', '2022-03-13 23:31:27', '1', '1');
INSERT INTO `material_user` VALUES ('1647185493256', '1643009560056', '2022-03-13 23:31:33', '1', '1');
INSERT INTO `material_user` VALUES ('1647185493267', '1643009560056', '2022-03-13 23:31:33', '1', '1');
INSERT INTO `material_user` VALUES ('1647220877166', '1643009560056', '2022-03-14 09:21:17', 'bg', '未描述');

-- ----------------------------
-- Table structure for public_material
-- ----------------------------
DROP TABLE IF EXISTS `public_material`;
CREATE TABLE `public_material`  (
  `material_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '素材ID',
  `material_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材名称',
  `material_url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材URL',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材描述',
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `download_num` int(11) NULL DEFAULT NULL COMMENT '下载量',
  `transform_num` int(11) NULL DEFAULT NULL COMMENT '转入量',
  `search_num` int(11) NULL DEFAULT NULL COMMENT '搜索量',
  `thumbs_num` int(11) NULL DEFAULT NULL COMMENT '点赞量',
  PRIMARY KEY (`material_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of public_material
-- ----------------------------
INSERT INTO `public_material` VALUES ('1644903594493', '公共素材测试', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-15/a91868c1-285e-4969-a59a-d49b31fdbd8b.jpg', '公共素材测试描述', '1643009560056', '2022-02-15 13:39:54', 2, 1, 3, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `gender` int(1) NULL DEFAULT NULL COMMENT '1为男，2为女',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_type` int(1) NULL DEFAULT NULL COMMENT '1为普通用户、2为采集员、3为管理员',
  `user_status` int(1) NULL DEFAULT NULL COMMENT '1为可用、0为不可用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE COMMENT '手机号不可重复'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1642489492053', 'dakeji', '$2a$10$kdvRShkowwaJN8u6qCNoPun.9Mokazx2eC2dI6axRiv5I8lT7US/a', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-19/32ffff36-9ed1-48da-a72d-e164f9f134aa.jpg', 1, '15733230691', '857269545@qq.com', 1, 1, '2022-01-18 15:04:52', '2022-03-02 19:08:49');
INSERT INTO `user` VALUES ('1643009560056', 'laoli', '$2a$10$knIq3lFEATqINRxyKRyVGu/BGE6l4oUu8NGOCWo5J6IPk7EP1vl2O', 'https://images.nowcoder.com/images/20211115/633872610_1636955847464/FECD76F09C4EFFA7102ECDBC1795FB3B?x-oss-process=image/resize,m_mfit,h_100,w_100', 1, '15733230693', '857269545@qq.com', 2, 1, '2022-01-24 15:32:40', '2022-03-02 08:47:51');
INSERT INTO `user` VALUES ('1643098646972', 'admin', '', 'https://images.nowcoder.com/images/20211115/633872610_1636955847464/FECD76F09C4EFFA7102ECDBC1795FB3B?x-oss-process=image/resize,m_mfit,h_100,w_100', 2, '15733230692', '857269545@qq.com', 3, 1, '2022-01-25 16:17:27', '2022-03-03 09:58:02');
INSERT INTO `user` VALUES ('1643163474195', 'test1', '$2a$10$K9dz2HGoPkPGlw5hApc8B.Njlt8HPNetOM65MAddSG362sIngB3SS', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-26/17fc3084-9c69-4314-96a0-b5b9e5acc054.jpg', 1, '15733230697', '1@qq.com', 1, 0, '2022-01-26 10:17:54', '2022-03-02 19:08:43');
INSERT INTO `user` VALUES ('1646709990448', 'test', '$2a$10$dSVkd0X9WEG/TDvxdZunuu1DiCuj/1CFTq7tBVoCDrt7mFOOHYgG2', 'https://images.nowcoder.com/images/20211115/633872610_1636955847464/FECD76F09C4EFFA7102ECDBC1795FB3B?x-oss-process=image/resize,m_mfit,h_100,w_100', 1, '15733230699', '857269545@qq.com', 1, 1, '2022-03-08 11:26:30', NULL);

-- ----------------------------
-- Table structure for view_mark
-- ----------------------------
DROP TABLE IF EXISTS `view_mark`;
CREATE TABLE `view_mark`  (
  `view_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '打卡ID',
  `view_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打卡名称',
  `view_remarks` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打卡备注',
  `lat` decimal(20, 10) NULL DEFAULT NULL COMMENT '经度',
  `lng` decimal(20, 10) NULL DEFAULT NULL COMMENT '纬度',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市名称',
  `district` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区名称',
  `view_img_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打卡图片',
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `scan_num` int(11) NULL DEFAULT NULL COMMENT '浏览量',
  PRIMARY KEY (`view_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of view_mark
-- ----------------------------
INSERT INTO `view_mark` VALUES ('1646534180014', '测试', '测试', 38.0483119300, 114.5215319000, '河北省', '石家庄市', '长安区', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-06/71e5ad1a-5a41-4d9d-ad19-cb7085845f1d.jpg', '1642489492053', '2022-03-06 10:36:20', 0);
INSERT INTO `view_mark` VALUES ('1646650322522', '232', '1', 38.0483119300, 114.5215319000, '河北省', '石家庄市', '长安区', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-07/33343866-4a3e-4d1d-8584-451e86a298ad.jpg', '1642489492053', '2022-03-07 18:52:03', 0);
INSERT INTO `view_mark` VALUES ('1646829027612', '打卡', '打卡测试', 38.0833720000, 114.5117200000, '河北省', '石家庄市', '长安区', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-09/14e38b6c-77e0-40c9-a968-47f280cd2955.jpg', '1643009560056', '2022-03-09 20:30:28', 0);
INSERT INTO `view_mark` VALUES ('1646891799935', '位置', '哈哈哈哈哈哈哈哈哈哈哈哈好', 38.0833980000, 114.5122680000, '河北省', '石家庄市', '长安区', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-03-10/d6b30189-786b-4a3b-b6f0-32d5ddaa1de2.jpg', '1643009560056', '2022-03-10 13:56:40', 0);

-- ----------------------------
-- Table structure for work_group
-- ----------------------------
DROP TABLE IF EXISTS `work_group`;
CREATE TABLE `work_group`  (
  `group_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作组ID',
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作组名称',
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者id',
  `group_img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作组头像',
  `group_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作组描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of work_group
-- ----------------------------
INSERT INTO `work_group` VALUES ('1642756420812', '测试用户组1', '1642489492053', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-19/groupAvatar.jpg', '修改工作组测试描述', '2022-01-21 17:13:41');
INSERT INTO `work_group` VALUES ('1643243923122', '测试用户组1', '1643009560056', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-09/2f48d5da-3edd-431d-89c2-248d76ac85b5.jpg', '描述测试123', '2022-01-27 08:38:43');
INSERT INTO `work_group` VALUES ('1643250131568', '测试用户组2', '1643009560056', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-01-19/groupAvatar.jpg', '描述测试', '2022-01-27 10:22:12');
INSERT INTO `work_group` VALUES ('1644398393798', '创建工作组测试', '1643009560056', 'https://dakeji.oss-cn-beijing.aliyuncs.com/images/2022-02-09/6ceef3d9-a0d7-4323-833e-d4d00e89c468.jpg', '创建工作组描述测试', '2022-02-09 17:19:54');

SET FOREIGN_KEY_CHECKS = 1;
