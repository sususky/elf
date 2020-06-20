-- 权限功能表
BEGIN;
INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (1, 0, '系统管理', 1, '', 1 ,now());
INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (2, 1, '权限管理', 2, '/privilege', 1, now());
INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (3, 1, '角色管理', 2, '/role', 2, now());
INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (4, 1, '用户管理', 2, '/user', 3, now());
INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (5, 1, '系统日志', 2, '/log', 4, now());
INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (6, 1, '系统监控', 2, '/monitor', 5, now());

INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (10, 0, '信息管理', 1, '', 2, now());
INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (11, 10, '博客管理', 2, '/blog', 1, now());
INSERT INTO `sys_privilege`(id, parent_id, `name`, category, path, seq, create_time) VALUES (12, 10, '评论管理', 2, '/comment', 2, now());

COMMIT;

-- 角色表
BEGIN;
INSERT INTO `sys_role`(id, `name`, `level`, create_time) VALUES (1, '一级管理员', 1, now());
INSERT INTO `sys_role`(id, `name`, `level`, create_time) VALUES (2, '二级管理员', 2, now());
INSERT INTO `sys_role`(id, `name`, `level`, create_time) VALUES (3, '普通用户', 3, now());
COMMIT;

-- 角色权限关联表
BEGIN;
INSERT INTO sys_role_privilege_rel VALUES (1, 1, 2);
INSERT INTO sys_role_privilege_rel VALUES (1, 2, 2);
INSERT INTO sys_role_privilege_rel VALUES (1, 3, 2);
INSERT INTO sys_role_privilege_rel VALUES (1, 4, 2);
INSERT INTO sys_role_privilege_rel VALUES (1, 5, 2);
INSERT INTO sys_role_privilege_rel VALUES (1, 6, 2);
INSERT INTO sys_role_privilege_rel (role_id, privilege_id) VALUES (2, 5);
INSERT INTO sys_role_privilege_rel (role_id, privilege_id) VALUES (2, 6);
INSERT INTO sys_role_privilege_rel VALUES (2, 10, 1);
INSERT INTO sys_role_privilege_rel VALUES (2, 11, 1);
COMMIT;

-- 用户表
BEGIN;
INSERT INTO `sys_user`(id, username, nickname, password, is_super create_time) VALUES (1, 'super', '超管', '$2a$10$uzhkKtmXJijCTebjFQW.MOa2m/Zl1/7KqiiRGJTKKDiCCkdbKBTwa', 1, now());
INSERT INTO `sys_user`(id, username, nickname, password, create_time) VALUES (2, 'admin', '一级管理员', '$2a$10$awElGCSlUNd8pAdfxPjlvuNmzlPdlh1hy95cOEhjZm3S5uvZLgK3m', now());

COMMIT;


-- 用户角色关联表
BEGIN;
INSERT INTO sys_user_role_rel (user_id, role_id) VALUES (2, 1);
INSERT INTO sys_user_role_rel (user_id, role_id) VALUES (2, 2);

COMMIT;
