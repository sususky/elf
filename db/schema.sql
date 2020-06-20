-- ----------------------------
-- Table structure for sys_privilege 系统权限表
-- ----------------------------
DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(11)  NOT NULL DEFAULT 0 COMMENT '上级菜单ID',
  `name` varchar(64) NOT NULL COMMENT '权限名称',
  `category` tinyint  NOT NULL DEFAULT 2 COMMENT '权限类型 1目录 2菜单 3按钮',
  `path` varchar(128) DEFAULT NULL COMMENT '地址',
  `enabled` tinyint  NOT NULL DEFAULT 1 COMMENT '状态：1启用、2禁用',
  `seq` tinyint unsigned  NOT NULL DEFAULT 0 COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限表 菜单';


-- ----------------------------
-- Table structure for sys_role 系统角色表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `level` tinyint  NOT NULL DEFAULT 1 COMMENT '角色级别 1超管 2管理员 3普通用户',
  `enabled` tinyint  NOT NULL DEFAULT 1 COMMENT '状态：1启用、2禁用',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';


-- ----------------------------
-- Table structure for sys_role_privilege_ref 系统角色权限关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_privilege_rel`;
CREATE TABLE `sys_role_privilege_rel` (
  `role_id` int(11) NOT NULL DEFAULT 0,
  `privilege_id` int(11) NOT NULL DEFAULT 0,
  `readonly` tinyint NOT NULL DEFAULT 1 COMMENT '1只读，2读写'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for sys_user 系统用户表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `gender` tinyint NOT NULL DEFAULT 1 COMMENT '1男, 2女',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `avatar_url` varchar(128) DEFAULT NULL COMMENT '头像地址',
  `avatar_path` varchar(255) DEFAULT NULL COMMENT '头像真实路径',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `is_super` tinyint NOT NULL DEFAULT 2 COMMENT '是否为admin账号 1是 2否',
  `is_readonly` tinyint NOT NULL DEFAULT 2 COMMENT '是否为只读账号 1是 2否',
  `enabled` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1启用、2禁用',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新着',
  `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';


-- ----------------------------
-- Table structure for sys_user_role_ref 系统用户角色关联表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_rel`;
CREATE TABLE `sys_user_role_rel` (
  `user_id` int(11) NOT NULL DEFAULT 0,
  `role_id` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for sys_log 系统日志表
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `log_type` enum('debug', 'info', 'warn', 'error') DEFAULT 'info' COMMENT '日志等级',
  `username` varchar(64) DEFAULT NULL  COMMENT '操作人',
  `method` varchar(128) DEFAULT NULL COMMENT '方法',
  `client_ip` varchar(16) DEFAULT NULL COMMENT '客户端IP',
  `browser` varchar(32) DEFAULT NULL COMMENT '浏览器',
  `os` varchar(16) DEFAULT '' COMMENT '操作系统',
  `opt` varchar(16) DEFAULT NULL COMMENT '操作',
  `time` int NOT NULL DEFAULT 0 COMMENT '执行时间 毫秒',
  `request_params` varchar(255) DEFAULT NULL COMMENT '请求参数',
  `exception_detail` text,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `log_create_time_index` (`create_time`),
  KEY `inx_log_type` (`log_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';


-- 区域表
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `level` tinyint NOT NULL DEFAULT 0 COMMENT '1国家 2省份 3市 4区县 5其他',
  `code` int unsigned NOT NULL DEFAULT 0,
  `parent_code` int unsigned NOT NULL DEFAULT 0,
  `name` varchar(128) NOT NULL DEFAULT '',
  `longitude` double NOT NULL DEFAULT 0,
  `latitude` double NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `region_code_uk` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
