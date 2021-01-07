-- version_upgrade 版本升级信息表
drop table if exists version_upgrade;
CREATE TABLE `version_upgrade` (
    `id` smallint(4) unsigned NOT NULL AUTO_INCREMENT,
    `create_time` datetime NOT NULL,
    `modifier_time` datetime NOT NULL,
    `version_id` smallint(4) unsigned DEFAULT '0' COMMENT '大版本号id',
    `version_mini` mediumint(8) unsigned DEFAULT '0' COMMENT '小版本号',
    `version_code` varchar(10) DEFAULT NULL COMMENT '版本标识 1.2',
    `type` int(11) unsigned DEFAULT NULL COMMENT '是否升级 1升级，0不升级，2强制升级',
    `apk_url` varchar(255) DEFAULT NULL,
    `upgrade_point` varchar(255) DEFAULT NULL COMMENT '升级提示',
    `status` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists sys_log;
CREATE TABLE `sys_log` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `create_time` datetime NOT NULL,
    `modifier_time` datetime NOT NULL,
    `username` varchar(50)  DEFAULT NULL COMMENT '用户名',
    `user_ip` varchar(50)  DEFAULT NULL COMMENT '用户ip',
    `request_method` varchar(50)DEFAULT NULL COMMENT '请求方法',
    `request_desc` varchar(10)  DEFAULT NULL COMMENT '方法描述',
    `params` varchar(255) DEFAULT NULL COMMENT '请求参数',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
