-- version_upgrade 版本升级信息表
drop table if exists version_upgrade;
CREATE TABLE version_upgrade (
    id int(11) NOT NULL AUTO_INCREMENT,
    create_time datetime NOT NULL,
    modify_time datetime NOT NULL,
    version_code varchar(10) DEFAULT NULL COMMENT '版本标识 1.2',
    type int(11)  DEFAULT NULL COMMENT '是否升级 1升级，0不升级，2强制升级',
    download_url varchar(255) DEFAULT NULL COMMENT '下载路径',
    version_desc varchar(255) DEFAULT NULL COMMENT '升级提示',
    model varchar(255) DEFAULT NULL COMMENT '手机型号',
    status int(11) DEFAULT NULL COMMENT '0-停用，1-启用',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

insert into version_upgrade values(1, '2020-1-8 20:20:21', '2020-1-8 20:20:21', 'v1.0', '0', 'http://ip:8080/upload/file/aaaa.apk', '1.基于官方最新OneUI3.0\n2.更清晰、更简洁','G9880',1)

-- 操作日志
drop table if exists operation_log;
CREATE TABLE operation_log (
    id int(11) NOT NULL AUTO_INCREMENT,
    create_time datetime NOT NULL COMMENT '请求时间',
    modify_time datetime NOT NULL,
    model varchar(50) DEFAULT NULL COMMENT '功能模块',
    type varchar(50) DEFAULT NULL COMMENT '操作类型',
    operation_desc varchar(50) DEFAULT NULL COMMENT '操作描述',
    request_param text DEFAULT NULL COMMENT '请求参数',
    response_param text DEFAULT NULL COMMENT '响应参数',
    user_id int(11) DEFAULT NULL COMMENT '用户id',
    code varchar(50)  DEFAULT NULL COMMENT '用户code',
    ip varchar(50)  DEFAULT NULL COMMENT '请求ip',
    uri varchar(50) DEFAULT NULL COMMENT 'uri',
    method varchar(200) DEFAULT NULL COMMENT '操作方法',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 操作异常日志
drop table if exists exception_log;
CREATE TABLE exception_log (
    id int(11) NOT NULL AUTO_INCREMENT,
    create_time datetime NOT NULL COMMENT '请求时间',
    modify_time datetime NOT NULL,
    message varchar(50) DEFAULT NULL COMMENT '异常信息',
    name varchar(50) DEFAULT NULL COMMENT '异常名称',
    request_param varchar(500) DEFAULT NULL COMMENT '请求参数',
    user_id int(11) DEFAULT NULL COMMENT '用户id',
    code varchar(50)  DEFAULT NULL COMMENT '用户code',
    ip varchar(50)  DEFAULT NULL COMMENT '请求ip',
    uri varchar(50) DEFAULT NULL COMMENT 'uri',
    method text DEFAULT NULL COMMENT '操作方法',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 附件
DROP TABLE IF EXISTS attach;
CREATE TABLE attach (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  data_type int(11) DEFAULT NULL,
  ext varchar(50) DEFAULT NULL,
  file_name varchar(50) DEFAULT NULL,
  file_size int(11) DEFAULT NULL,
  file_type varchar(50) DEFAULT NULL,
  mime varchar(50) DEFAULT NULL,
  original_name varchar(50) DEFAULT NULL,
  path varchar(500) DEFAULT NULL,
  record_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  code varchar(50) DEFAULT NULL COMMENT 'UUID',
  model varchar(50) NOT NULL COMMENT '设备型号',
  version_code varchar(50) DEFAULT NULL COMMENT '应用版本',
  upload_counter int(11) NOT NULL COMMENT '下载次数',
  user_mode varchar(50) NOT NULL COMMENT '用户状态，normal 普通用户 powerful专业',
  is_enable int(2) DEFAULT 1 comment '0:禁用, 1；启用',
  source int(2) DEFAULT NULL comment '用户来源 0 或1',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 角色表
DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  name varchar(50) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 权限
DROP TABLE IF EXISTS auth;
CREATE TABLE auth (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  name varchar(50) NOT NULL COMMENT '权限名称',
  code varchar(50) NOT NULL COMMENT '权限值',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 用户角色(关系)表
DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  user_id int(11) NOT NULL COMMENT '用户id',
  role_id int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 角色权限(关系)表
DROP TABLE IF EXISTS role_auth;
CREATE TABLE role_auth (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  role_id int(11) NOT NULL COMMENT '角色id',
  auth_id int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 非会员表
DROP TABLE IF EXISTS nonmember;
CREATE TABLE nonmember (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  code varchar(50) DEFAULT NULL COMMENT 'UUID',
  model varchar(50) NOT NULL COMMENT '设备型号',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


insert into user values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 'af9526b5623d4752800fbc79782012ab', 'N9877', '1.0', 2, 'powerful', 1, 1);
insert into role values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '超级用户');
-- 系统权限
insert into auth values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理', 'system:index');
insert into auth values('2', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理', 'system:version:index');
insert into auth values('3', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理-新增', 'system:version:save');
insert into auth values('4', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理-修改', 'system:version:update');
insert into auth values('5', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-版本管理-检查', 'system:version:check');
insert into auth values('6', '2021-1-13 16:24:24', '2021-1-13 16:24:24', '系统管理-用户管理', 'system:user:index');

insert into role_auth values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 1);
insert into role_auth values('2', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 2);
insert into role_auth values('3', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 3);
insert into role_auth values('4', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 4);
insert into role_auth values('5', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 5);
insert into role_auth values('6', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 6);

insert into user_role values('1', '2021-1-13 16:24:24', '2021-1-13 16:24:24', 1, 1);