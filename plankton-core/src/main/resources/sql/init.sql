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

-- 版本
DROP TABLE IF EXISTS version_upgrade_item;
CREATE TABLE version_upgrade_item (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  version_info_id int(11) NOT NULL COMMENT '版本明细id',
  version_code varchar(50) NOT NULL COMMENT '版本编号',
  model varchar(50) NOT NULL COMMENT '版本编号',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 角色表
DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  name varchar(50) NOT NULL COMMENT '角色名称',
  remark varchar(50) NOT NULL COMMENT '角色描述',
  enabled int(2) DEFAULT NULL COMMENT '是否启用 1：启用，0禁用',
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
  url varchar (50) DEFAULT NULL COMMENT '资源路径',
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

-- 菜单
DROP TABLE IF EXISTS menu;
CREATE TABLE menu (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  url varchar(64) DEFAULT NULL COMMENT '访问路径',
  perms varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  name varchar(64) DEFAULT NULL COMMENT '模块名称',
  type int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  icon_cls varchar(64) DEFAULT NULL COMMENT 'icon图标',
  component varchar(64) DEFAULT NULL COMMENT '组件',
  parent_id int(11) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 角色与菜单(关系)表
DROP TABLE IF EXISTS role_menu;
CREATE TABLE role_menu (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  role_id int(11) NOT NULL COMMENT '角色id',
  menu_id int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 数据字典
DROP TABLE IF EXISTS dictionaries;
CREATE TABLE dictionaries (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  name varchar(100) NOT NULL COMMENT '数据值',
  parent_id int(20) DEFAULT NULL COMMENT '上级字典ID，一级字典为0',
  order_num int(11) DEFAULT NULL COMMENT '排序',
  del_flag int(1) DEFAULT '0' comment '删除状态（0，正常，1已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='字典管理表';

-- 外置应用
DROP TABLE IF EXISTS external_application;
CREATE TABLE external_application (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  create_time datetime NOT NULL,
  modify_time datetime NOT NULL,
  name varchar(100) NOT NULL COMMENT '应用名称',
  version_code varchar(100) DEFAULT NULL COMMENT '版本编号',
  version_desc varchar(100) DEFAULT NULL COMMENT '编号描述',
  download_url varchar(100) DEFAULT NULL COMMENT '下载路径',
  external_type int(11) DEFAULT NULL COMMENT '应用类型',
  type int(1) DEFAULT '0' comment '是否升级 1-升级，0-不升级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='外置应用表';
