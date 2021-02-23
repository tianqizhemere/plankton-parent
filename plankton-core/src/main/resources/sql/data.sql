-- 版本信息
insert into version_upgrade values(1, '2020-1-8 20:20:21', '2020-1-8 20:20:21', 'v1.0', '0', 'http://ip:8080/upload/file/aaaa.apk', '1.基于官方最新OneUI3.0\n2.更清晰、更简洁','G9880',1)

-- 初始化用户
insert into user values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 'superAdmin', 'rts123!@#', '1.0', 2, 'powerful', 1, 1, '15575731038', '823507949');
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
insert into auth values('14', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理', 'system:version:index', '/system/version');
insert into auth values('15', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理-视图', 'system:version:view', '/system/version');
insert into auth values('16', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理-新增', 'system:version:save', '/system/version');
insert into auth values('17', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理-修改', 'system:version:update', '/system/version');
insert into auth values('18', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理-删除', 'system:version:delete', '/system/version');
insert into auth values('19', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-日志管理', 'system:log:index', '/system/log');
insert into auth values('20', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-日志管理-视图', 'system:log:view', '/system/log');
insert into auth values('21', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-外置应用', 'system:externalApplication:index', '/system/external');
insert into auth values('22', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-外置应用-视图', 'system:externalApplication:view', '/system/external');
insert into auth values('23', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-外置应用-新增', 'system:externalApplication:save', '/system/external');
insert into auth values('24', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-外置应用-修改', 'system:externalApplication:update', '/system/external');
insert into auth values('25', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-外置应用-删除', 'system:externalApplication:delete', '/system/external');
insert into auth values('26', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-通知管理-视图', 'system:notice:view', '/notice/notice');
insert into auth values('27', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-通知管理-新增', 'system:notice:save', '/notice/notice');
insert into auth values('28', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-通知管理-修改', 'system:notice:update', '/notice/notice');
insert into auth values('29', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-通知管理-删除', 'system:notice:delete', '/notice/notice');

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
insert into role_menu values('11', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 11);
insert into role_menu values('12', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 12);
insert into role_menu values('13', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 13);

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
insert into role_auth values('14', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 14);
insert into role_auth values('15', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 15);
insert into role_auth values('16', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 16);
insert into role_auth values('17', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 17);
insert into role_auth values('18', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 18);
insert into role_auth values('19', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 19);
insert into role_auth values('20', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 20);
insert into role_auth values('21', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 21);
insert into role_auth values('22', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 22);
insert into role_auth values('23', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 23);
insert into role_auth values('24', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 24);
insert into role_auth values('25', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 25);
insert into role_auth values('26', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 26);
insert into role_auth values('27', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 27);
insert into role_auth values('28', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 28);
insert into role_auth values('29', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 29);

insert into user_role values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 1);

-- 初始化菜单
INSERT INTO menu VALUES (1, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:index', '/home/intro', '首页', 0, 'el-icon-s-home', 'AdminIndex', 0);
INSERT INTO menu VALUES (2, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:dash', '/home/intro', '首页', 1, 'kt-icon-cengji', 'dashboard/admin/index', 1);
INSERT INTO menu VALUES (3, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:user:index', '/sys', '系统管理', 0, 'el-icon-s-tools', 'AdminIndex', 0);
INSERT INTO menu VALUES (5, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:user', '/admin', '系统配置', 0, 'el-icon-s-tools', 'AdminIndex', 0);
INSERT INTO menu VALUES (6, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:user', '/sys/user', '用户信息', 1, 'kt-icon-qunzu', 'user/UserProfile', 3);
INSERT INTO menu VALUES (7, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:role', '/sys/role', '角色配置', 1, 'kt-icon-weixiufuwu_o', 'user/Role', 3);
INSERT INTO menu VALUES (8, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:log', '/sys/log', '系统日志', 1, 'kt-icon-lishijilu', 'content/BookManagement', 3);
INSERT INTO menu VALUES (9, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:dict', '/sys/dict', '字典管理', 1, 'kt-icon-shuzhuangtu', 'content/BannerManagement', 3);
INSERT INTO menu VALUES (10, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:version', '/sys/version', '版本管理', 1, 'kt-icon-yunshangchuan', 'content/ArticleManagement', 3);
INSERT INTO menu VALUES (11, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:user', '/sys/external', '外置应用', 1, 'kt-icon-weixiufuwu', 'content/ArticleManagement', 3);
INSERT INTO menu VALUES (12, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'notice:notice', '/admin/notice', '通知管理', 1, 'kt-icon-weixiufuwu', 'content/ArticleManagement', 5);
INSERT INTO menu VALUES (13, '2021-01-13 16:24:24', '2021-01-13 16:24:24', 'system:exceptionLog', '/sys/exceptionLog', '异常日志', 1, 'kt-icon-weixiufuwu', 'content/ArticleManagement', 3);


-- 数字字典
INSERT INTO dictionaries VALUES (4, '2021-01-24 00:01:01', '2021-01-24 00:01:01', 'Galaxy S10', 0, 0, 0);
INSERT INTO dictionaries VALUES (5, '2021-01-24 00:01:01', '2021-01-24 00:01:01', 'Galaxy Note10', 0, 0, 0);
INSERT INTO dictionaries VALUES (6, '2021-01-24 00:01:01', '2021-01-24 00:01:01', 'Galaxy S20', 0, 0, 0);
INSERT INTO dictionaries VALUES (7, '2021-01-24 00:01:01', '2021-01-24 00:01:01', 'Galaxy Note20', 0, 0, 0);
INSERT INTO dictionaries VALUES (8, '2021-01-24 00:01:01', '2021-01-24 00:01:01', 'Galaxy S21U', 0, 0, 0);
INSERT INTO dictionaries VALUES (9, '2021-01-28 14:03:34', '2021-01-28 14:03:34', 'G973N', 4, 1, 0);
INSERT INTO dictionaries VALUES (10, '2021-01-30 12:13:58', '2021-01-30 12:13:58', 'G973F', 4, 1, 0);
INSERT INTO dictionaries VALUES (11, '2021-01-30 12:14:11', '2021-02-01 17:11:11', 'G975N', 4, 0, 0);
INSERT INTO dictionaries VALUES (12, '2021-01-30 12:14:20', '2021-01-30 12:14:20', 'G975F', 4, 0, 0);
INSERT INTO dictionaries VALUES (13, '2021-01-30 12:14:48', '2021-01-30 12:14:48', 'G977N', 4, 0, 0);
INSERT INTO dictionaries VALUES (14, '2021-01-30 12:15:11', '2021-01-30 12:15:11', 'G977B', 4, 0, 0);
INSERT INTO dictionaries VALUES (15, '2021-01-30 12:15:26', '2021-01-30 12:15:26', 'N971N', 5, 0, 0);
INSERT INTO dictionaries VALUES (16, '2021-01-30 12:15:40', '2021-01-30 12:15:40', 'N976N', 5, 0, 0);
INSERT INTO dictionaries VALUES (17, '2021-01-30 12:15:49', '2021-01-30 12:15:49', 'N976B', 5, 0, 0);
INSERT INTO dictionaries VALUES (18, '2021-01-30 12:16:02', '2021-01-30 12:16:02', 'G981N', 6, 0, 0);
INSERT INTO dictionaries VALUES (19, '2021-01-30 12:16:15', '2021-01-30 12:16:15', 'G9810', 6, 0, 0);
INSERT INTO dictionaries VALUES (20, '2021-01-30 12:16:25', '2021-01-30 12:16:25', 'G986N', 6, 0, 0);
INSERT INTO dictionaries VALUES (21, '2021-01-30 12:16:36', '2021-01-30 14:49:45', 'G9860', 6, 0, 0);
INSERT INTO dictionaries VALUES (22, '2021-01-30 12:16:44', '2021-01-30 12:16:44', 'G988N', 6, 0, 0);
INSERT INTO dictionaries VALUES (23, '2021-01-30 12:16:52', '2021-01-30 19:46:20', 'G9880', 6, 0, 0);
INSERT INTO dictionaries VALUES (24, '2021-01-30 12:17:05', '2021-01-30 12:17:05', 'N986N', 7, 0, 0);
INSERT INTO dictionaries VALUES (25, '2021-01-30 12:17:16', '2021-01-30 12:17:16', 'N9860', 7, 0, 0);
INSERT INTO dictionaries VALUES (26, '2021-01-30 12:17:29', '2021-01-30 12:17:29', 'G998N', 8, 0, 0);
INSERT INTO dictionaries VALUES (27, '2021-01-30 12:17:41', '2021-01-30 12:17:41', 'G9980', 8, 0, 0);