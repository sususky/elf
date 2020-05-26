
-- 公众号表
INSERT INTO `user`(account, password, nickname, is_super, create_time) VALUES ('admin', '202cb962ac59075b964b07152d234b70', '管理员', 1, now());


-- 权限功能表
INSERT INTO `privilege`(id, privilege_name, parent_id, link, seq, category, create_time, update_time) VALUES (1, '系统管理', 0, '', 1, 1 ,now(), now());
INSERT INTO `privilege` VALUES (2, '权限管理', 1, 'privilege', 1, 2, now(), now());
INSERT INTO `privilege` VALUES (3, '角色管理', 1, 'role', 1, 2, now(), now());
INSERT INTO `privilege` VALUES (4, '用户管理', 1, 'user', 1, 2, now(), now());
INSERT INTO `privilege` VALUES (5, '系统日志', 1, 'log', 1, 2, now(), now());


INSERT INTO `privilege` VALUES (6, '公众号管理', 1, 'mp', 1, 2, now(), now());

INSERT INTO `privilege`(id, privilege_name, level, parent_id, link, create_time, update_time) VALUES (20, '公众号', 1, 0, '' , now(), now());
INSERT INTO `privilege` VALUES (21, '素材管理', 2, 20, 'matter', now(), now());
INSERT INTO `privilege` VALUES (4230,'关键字管理',2,4228,'wai/keyman','','2016-12-21 10:40:53',now());
INSERT INTO `privilege` VALUES (4231,'菜单管理',2,4228,'wai/automenu','','2016-12-21 10:40:58',now());
INSERT INTO `privilege` VALUES (4232,'关注回复',2,4228,'wai/attentionAnswer','新增功能','2016-11-14 23:06:54',now());
INSERT INTO `privilege` VALUES (4233,'默认回复',2,4228,'wai/defaultAnswer','新增功能','2016-11-14 23:07:24',now());
INSERT INTO `privilege` VALUES (4234,'粉丝管理',2,4228,'wai/fansManage','新增功能','2016-11-14 23:07:47',now());
INSERT INTO `privilege` VALUES (4235,'分组管理',2,4228,'wai/groupManage','新增功能','2016-11-14 23:08:11',now());
INSERT INTO `privilege` VALUES (4236,'推送管理',2,4228,'wai/pushManage','新增功能','2016-11-14 23:08:30',now());
INSERT INTO `privilege` VALUES (4237,'数据统计',2,4228,'wai/statistics','新增功能','2016-11-14 23:09:07',now());
INSERT INTO `privilege` VALUES (4238,'统一推送',2,4228,'wai/pushAll','新增功能','2016-11-17 18:12:46',now());
INSERT INTO `privilege` VALUES (4239,'模板消息管理',2,4228,'wai/customizedMessages','新增功能','2016-11-22 17:33:28',now());

INSERT INTO `privilege` VALUES (4240,'金币管理',1,NULL,'#',NULL,'2016-11-21 23:47:14',now());
INSERT INTO `privilege` VALUES (4241,'金币管理',2,4240,'coin/coinManage','金币管理','2016-11-21 23:48:03',now());

INSERT INTO `privilege` VALUES (4244,'个人中心',1,NULL,'#',NULL,'2016-12-10 00:56:43',now());
INSERT INTO `privilege` VALUES (4245,'客户建议',2,4244,'suggestionList/suggestionList','客户建议','2016-12-10 01:11:46',now());
INSERT INTO `privilege` VALUES (4258,'广告设置',2,4244,'usercenter/banner','广告设置','2016-12-27 03:16:13',now());
INSERT INTO `privilege` VALUES (4246,'订购管理',2,4244,'wai/searchOrder','新增功能','2016-11-18 01:24:42',now());

INSERT INTO `privilege` VALUES (4259,'活动管理',1,NULL,'#',NULL,'2017-01-04 07:57:06',now());
INSERT INTO `privilege` VALUES (4260,'新活动',2,4259,'activity/create','新活动','2017-01-04 07:57:44',now());
INSERT INTO `privilege` VALUES (4261,'活动管理',2,4259,'activity/manage','活动管理','2017-01-04 07:58:00',now());
INSERT INTO `privilege` VALUES (4262,'奖品管理',2,4259,'activity/award','新问卷','2017-01-04 07:58:21',now());
INSERT INTO `privilege` VALUES (4263,'问题管理',2,4259,'activity/questionManage','问卷管理','2017-01-04 07:58:41',now());

INSERT INTO `privilege` VALUES (4276,'个人设置',1,NULL,'#',NULL,'2017-07-12 09:29:56',now());
INSERT INTO `privilege` VALUES (4277,'双因子认证',2,4276,'usercenter/towFactor','','2017-07-12 09:32:55',now());
INSERT INTO `privilege` VALUES (4278,'修改密码',2,4276,'usercenter/user','新增功能','2017-07-12 09:34:17',now());


-- 角色表
INSERT INTO role (role_id, role_name, `describe`, role_super) VALUES (1, '管理员', '管理员角色', 1);

-- 角色权限关联表
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 3100);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 3201);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 3202);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 3203);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 3204);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4228);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4229);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4230);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4231);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4232);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4233);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4234);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4235);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4236);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4237);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4238);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4239);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4240);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4241);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4244);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4245);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4246);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4258);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4259);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4260);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4261);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4262);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4263);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4276);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4277);
INSERT INTO role_privilege_ref (role_id, privilege_id) VALUES (1, 4278);


-- 用户-公众号-角色关联表
INSERT INTO user_account_role_ref (user_id, account_id, role_id) VALUES (1, 1001, 1);
INSERT INTO user_account_role_ref (user_id, account_id, role_id) VALUES (1, 1003, 1);
INSERT INTO user_account_role_ref (user_id, account_id, role_id) VALUES (1, 1009, 1);
