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
    status int(11) DEFAULT NULL COMMENT '0-停用，1-启用',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

insert into version_upgrade values(1, '2020-1-8 20:20:21', '2020-1-8 20:20:21', 'v1.0', '0', 'http://ip:8080/upload/file/aaaa.apk', '1.基于官方最新OneUI3.0\n2.更清晰、更简洁',1)

-- 操作日志
drop table if exists operation_log;
CREATE TABLE operation_log (
    id int(11) NOT NULL AUTO_INCREMENT,
    create_time datetime NOT NULL COMMENT '请求时间',
    modify_time datetime NOT NULL,
    model varchar(50) DEFAULT NULL COMMENT '功能模块',
    type varchar(50) DEFAULT NULL COMMENT '操作类型',
    operation_desc varchar(50) DEFAULT NULL COMMENT '操作描述',
    request_param varchar(100) DEFAULT NULL COMMENT '请求参数',
    response_param varchar(50) DEFAULT NULL COMMENT '响应参数',
    user_id int(11) DEFAULT NULL COMMENT '用户id',
    username varchar(50)  DEFAULT NULL COMMENT '用户名',
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
    request_param varchar(100) DEFAULT NULL COMMENT '请求参数',
    user_id int(11) DEFAULT NULL COMMENT '用户id',
    username varchar(50)  DEFAULT NULL COMMENT '用户名',
    ip varchar(50)  DEFAULT NULL COMMENT '请求ip',
    uri varchar(50) DEFAULT NULL COMMENT 'uri',
    method varchar(200) DEFAULT NULL COMMENT '操作方法',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;