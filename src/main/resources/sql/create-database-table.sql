#
#
# CREATE DATABASE IF NOT EXISTS coupon_db DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
# create user 'coupon'@'localhost' identified by 'password_dev!';
# grant all privileges on coupon_db.* to 'coupon'@'localhost';

# 模板
CREATE TABLE IF NOT EXISTS coupon_template_entity
(
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    template_name             VARCHAR(50)                       NOT NULL COMMENT '模板名称',
    coupon_name               VARCHAR(50)                       NOT NULL COMMENT '优惠券显示名称',
    cover_image_url           VARCHAR(100) COMMENT '封面图片',
    coupon_type               INT UNSIGNED DEFAULT 0            NOT NULL COMMENT '类型：1=满减券,2=折扣券',
    full_limit                INT UNSIGNED DEFAULT 0            NOT NULL COMMENT '满额限制：0不限制',
    face_value                DECIMAL(8, 2) UNSIGNED COMMENT '面额：优惠金额或者折扣',
    scope_of_application_type INT          DEFAULT 0            NOT NULL COMMENT '适用范围类型：1=指定商品，2=类目，3=品牌，0不限制',
    scope_of_application      VARCHAR(255)                      NOT NULL COMMENT '适用范围',
    validity_period_type      INT UNSIGNED DEFAULT 0            NOT NULL COMMENT '有效期类型：1=固定时间，2=领取后x天有效',
    begin_time                DATETIME COMMENT '有效期开始时间',
    end_time                  DATETIME COMMENT '有效期开始时间',
    valid_period_hours        INT          DEFAULT 0            NOT NULL COMMENT '有效期小时数',
    maximum_quantity          INT UNSIGNED DEFAULT 0            NOT NULL COMMENT '最大数量',
    maximum_per_user          INT UNSIGNED DEFAULT 1            NOT NULL COMMENT '每位用户最多领取个数：0不限制',
    quantity_issued           INT UNSIGNED DEFAULT 0            NOT NULL COMMENT '已经发放数量',
    version                   BIGINT       DEFAULT 0            NOT NULL,
    record_status             INT          DEFAULT 0            NOT NULL COMMENT '状态',
    sort_priority             INT          DEFAULT 0,
    remark                    VARCHAR(255),
    date_created              DATETIME                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified             DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT unique_template_name UNIQUE (template_name)
);


# 优惠券
CREATE TABLE IF NOT EXISTS coupon_entity
(
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    template_id               BIGINT COMMENT '模板id',
    coupon_name               VARCHAR(50)                       NOT NULL COMMENT '优惠券显示名称',
    cover_image_url           VARCHAR(100) COMMENT '封面图片',
    user_id                   VARCHAR(50),
    coupon_type               INT UNSIGNED DEFAULT 0            NOT NULL COMMENT '类型：1=满减券,2=折扣券',
    full_limit                INT UNSIGNED DEFAULT 0            NOT NULL COMMENT '满额限制：0不限制',
    face_value                DECIMAL(8, 2) UNSIGNED COMMENT '面额：优惠金额或者折扣',
    scope_of_application_type INT          DEFAULT 0            NOT NULL COMMENT '适用范围类型：1=指定商品，2=类目，3=品牌，0不限制',
    scope_of_application      VARCHAR(255)                      NOT NULL COMMENT '适用范围',
    validity_period_type      INT UNSIGNED DEFAULT 0            NOT NULL COMMENT '有效期类型：1=固定时间，2=领取后x天有效',
    begin_time                DATETIME COMMENT '有效期开始时间',
    end_time                  DATETIME COMMENT '有效期开始时间',
    version                   BIGINT       DEFAULT 0            NOT NULL,
    record_status             INT          DEFAULT 0            NOT NULL COMMENT '状态',
    sort_priority             INT          DEFAULT 0,
    remark                    VARCHAR(255),
    date_created              DATETIME                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified             DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

# 序列号
CREATE TABLE IF NOT EXISTS sequence_entity
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    sequence_name VARCHAR(50) COMMENT 'key值',
    current_value BIGINT   DEFAULT 1                NOT NULL
        COMMENT '当前值',
    version       INT      DEFAULT 0                NOT NULL,
    record_status INT      DEFAULT 0                NOT NULL,
    sort_priority INT      DEFAULT 0,
    remark        VARCHAR(255),
    date_created  DATETIME                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT unique_sequence_name UNIQUE (sequence_name)
);

# 资源表-角色表
CREATE TABLE IF NOT EXISTS resource_entity
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    url           VARCHAR(50)                       NOT NULL COMMENT 'url资源',
    roles         VARCHAR(50)                       NOT NULL COMMENT '该资源此类角色可访问,角色之间逗号分隔',
    version       INT      DEFAULT 0                NOT NULL,
    record_status INT      DEFAULT 0                NOT NULL,
    sort_priority INT      DEFAULT 0,
    remark        VARCHAR(255),
    date_created  DATETIME                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT unique_url UNIQUE (url)
);
