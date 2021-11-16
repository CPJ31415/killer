/* 请确认以下SQL符合您的变更需求，务必确认无误后再提交执行 */

CREATE TABLE `user_info` (
                             `id` bigint NOT NULL,
                             `name` varchar(64) NOT NULL DEFAULT '64',
                             `gender` tinyint NOT NULL,
                             `age` int NOT NULL,
                             `telphone` varchar(13) NOT NULL DEFAULT '13',
                             `register_mode` varchar NOT NULL,
                             `third_party_id` varchar(64) NOT NULL DEFAULT '64',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8;


