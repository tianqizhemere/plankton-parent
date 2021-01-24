-- 版本信息
insert into version_upgrade values(1, '2020-1-8 20:20:21', '2020-1-8 20:20:21', 'v1.0', '0', 'http://ip:8080/upload/file/aaaa.apk', '1.基于官方最新OneUI3.0\n2.更清晰、更简洁','G9880',1)

-- 初始化用户
insert into user values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 'superAdmin', 'rts123!@#', '1.0', 2, 'powerful', 1, 1);
insert into role values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '超级用户', '超级管理员', '1');
-- 系统权限
insert into auth values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理', 'system:version:index', '/system/version');
insert into auth values('2', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-用户管理', 'system:user:index', '/system/user');
insert into auth values('3', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-菜单管理', 'system:menu:index', '/system/menu');
insert into auth values('4', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-角色管理', 'system:role:index', '/system/role');
insert into auth values('5', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-用户管理-视图', 'system:user:view', '/system/user');
insert into auth values('6', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-用户管理-新增', 'system:user:save', '/system/user');
insert into auth values('7', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-用户管理-修改', 'system:user:update', '/system/user');
insert into auth values('8', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-用户管理-删除', 'system:user:delete', '/system/user');
insert into auth values('9', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-字典管理', 'system:dict:index', '/system/dict');
insert into auth values('10', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-字典管理-视图', 'system:dict:view', '/system/dict');
insert into auth values('11', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-字典管理-新增', 'system:dict:save', '/system/dict');
insert into auth values('12', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-字典管理-修改', 'system:dict:update', '/system/dict');
insert into auth values('13', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-字典管理-删除', 'system:dict:delete', '/system/dict');


-- 角色和菜单权限
insert into role_menu values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 1);
insert into role_menu values('2', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 2);
insert into role_menu values('3', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 3);
insert into role_menu values('4', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 4);
insert into role_menu values('5', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 5);
insert into role_menu values('6', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 6);
insert into role_menu values('7', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 7);
insert into role_menu values('8', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 8);
insert into role_menu values('9', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 9);
insert into role_menu values('10', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 10);

-- 角色权限
insert into role_auth values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 1);
insert into role_auth values('2', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 2);
insert into role_auth values('3', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 3);
insert into role_auth values('4', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 4);
insert into role_auth values('5', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 5);
insert into role_auth values('6', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 6);
insert into role_auth values('7', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 7);
insert into role_auth values('8', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 8);
insert into role_auth values('9', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 9);
insert into role_auth values('10', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 10);
insert into role_auth values('11', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 11);
insert into role_auth values('12', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 12);
insert into role_auth values('13', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 13);

insert into user_role values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 1);

-- 初始化菜单
INSERT INTO menu VALUES ('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:index','/index', '首页',0, 'el-icon-s-home', 'AdminIndex', '0');
INSERT INTO menu VALUES ('2', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:index','/home/intro','运行情况', 0, null, 'dashboard/admin/index', '1');
INSERT INTO menu VALUES ('3', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:index','/sys', '系统管理', 0,'el-icon-s-tools', 'AdminIndex', '0');
INSERT INTO menu VALUES ('4', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:user','/admin', '内容管理', 0, 'el-icon-tickets', 'AdminIndex', '0');
INSERT INTO menu VALUES ('5', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:user','/admin', '系统配置', 0, 'el-icon-s-tools', 'AdminIndex', '0');
INSERT INTO menu VALUES ('6', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:user','/sys/user', '用户信息', 0, null, 'user/UserProfile', '3');
INSERT INTO menu VALUES ('7', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:role','/sys/role', '角色配置', 0, null, 'user/Role', '3');
INSERT INTO menu VALUES ('8', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:log','/sys/log', '日志管理', 0, null, 'content/BookManagement', '3');
INSERT INTO menu VALUES ('9', '2021-1-13 16:24:24', '2021-1-13 16:24:24','system:dict','/sys/dict', '字典管理', 0, null, 'content/BannerManagement', '4');
INSERT INTO menu VALUES ('10','2021-1-13 16:24:24', '2021-1-13 16:24:24', 'system:user','/admin/content/article', '文章管理', 0, null, 'content/ArticleManagement', '4');

-- 数字字典
INSERT INTO dictionaries VALUES ('1', '2021-1-24 0:01:01', '2021-1-24 0:01:01','male', '男', 'sex', '性别', '0', '性别');
INSERT INTO dictionaries VALUES ('2', '2021-1-24 0:01:01', '2021-1-24 0:01:01','female', '女', 'sex', '性别', '1',  '性别');

