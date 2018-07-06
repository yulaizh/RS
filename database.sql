CREATE TABLE `article` (
	`id` varchar(20) NOT NULL,    
	`title` varchar(1000) CHARACTER SET utf8mb4 DEFAULT '',
-- 	`title` varchar(1000),
	`origin` varchar(50) NOT NULL,
	-- 把varchar(100)修改成TEXT，TEXT不能有default value，所以删除DEFAULT ''
	`content` TEXT,
	`tag` varchar(10) DEFAULT '',
	`keywords` varchar(100) DEFAULT '',
	`description` varchar(10000) DEFAULT '',
	-- 因为reads是mysql的关键字之一，所以将源文件中reads修改为read_s(workbench下)
	`read_s` varchar(6) DEFAULT '0',
	`likes` varchar(6) DEFAULT '0',
	`dislikes` varchar(6) DEFAULT '0',
	`source_url` varchar(1000) DEFAULT '',
	`image_list` varchar(1000) DEFAULT '',
	`crawl_time` varchar(13) NOT NULL,       -- 需要确定位数
	PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_log`(
	`id` int(20) NOT NULL AUTO_INCREMENT,
	`account` varchar(20) NOT NULL,
	`password` varchar(20) NOT NULL,
	PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 如果使用workbench，就不要使用下面这一段，使用后面的
-- CREATE TABLE `user_info`(
-- 	`id` varchar(20) NOT NULL,
-- 	`sex` varchar(10) CHECK(`sex` in ('男','女')) DEFAULT NULL,
-- 	`birth` date DEFAULT NULL,
--     `address_province` varchar(20) DEFAULT NULL,
--     `address_city` varchar(20) DEFAULT NULL,
--     `address_district` varchar(20) DEFAULT NULL,
--     `address_detailed` varchar(100) DEFAULT NULL,
--     `pic_url` varchar(100) DEFAULT NULL,
-- 	PRIMARY KEY(`id`)
-- )ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- workbench下：
CREATE TABLE `user_info` (
  `id` varchar(20) NOT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `address_province` varchar(20) DEFAULT NULL,
  `address_city` varchar(20) DEFAULT NULL,
  `address_district` varchar(20) DEFAULT NULL,
  `address_detailed` varchar(100) DEFAULT NULL,
  `pic_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `comment`(
	`comment_id` varchar(20) NOT NULL,
	`article_id` varchar(20) NOT NULL,
	`user_id` varchar(20) NOT NULL,
	`comment_time` varchar(50) NOT NULL DEFAULT '',
	`comment_content` varchar(1000) NOT NULL DEFAULT '',
	PRIMARY KEY(`comment_id`),
	FOREIGN KEY(`article_id`) REFERENCES `article`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `comment_comment`(
	`comment_comment_id` varchar(20) NOT NULL,
	`comment_id` varchar(20) NOT NULL,
	`article_id` varchar(20) NOT NULL,
	`user_id` varchar(20) NOT NULL,
	`comment_comment_time` varchar(50) NOT NULL DEFAULT '',
	`comment_comment_content` varchar(1000) NOT NULL DEFAULT '',
	PRIMARY KEY(`comment_comment_id`),
	FOREIGN KEY(`comment_id`) REFERENCES `comment`(`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `recomment`(
	`recomment_id` varchar(20) NOT NULL,
	`comment_comment_id` varchar(20) NOT NULL,
	`article_id` varchar(20) NOT NULL,
	`user_id` varchar(20) NOT NULL,
	`recomment_time` varchar(50) NOT NULL DEFAULT '',
	`recomment_content` varchar(1000) NOT NULL DEFAULT '',
	PRIMARY KEY(`recomment_id`),
	FOREIGN KEY(`comment_comment_id`) REFERENCES `comment_comment`(`comment_comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `record`(
	`user_id` varchar(20) NOT NULL,
	`article_id` varchar(20) NOT NULL,
	`comment_content`varchar(1000) NOT NULL DEFAULT '',
	`likeornot` varchar(1) NOT NULL DEFAULT '1' CHECK(`likeornot` in ('1','2','3')),
	`tag` varchar(10) NOT NULL DEFAULT '',
	`read_time` varchar(20) NOT NULL DEFAULT '',
	PRIMARY KEY(`user_id`,`article_id`,`read_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


select tag,count(tag) from article group by tag;


