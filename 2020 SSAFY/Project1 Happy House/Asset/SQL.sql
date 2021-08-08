CREATE TABLE `project_member` (
  `userid` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `userpwd` varchar(100) NOT NULL,
  `email` varchar(2000) DEFAULT NULL,
  `address` varchar(2000) DEFAULT NULL,
  `joindate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `approval_status` varchar(10) NOT NULL,
  `approval_key` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `houseinfo` (
  `no` int NOT NULL AUTO_INCREMENT,
  `dong` varchar(30) NOT NULL,
  `AptName` varchar(50) NOT NULL,
  `code` varchar(30) NOT NULL,
  `buildYear` varchar(30) DEFAULT NULL,
  `jibun` varchar(30) DEFAULT NULL,
  `lat` varchar(30) DEFAULT NULL,
  `lng` varchar(30) DEFAULT NULL,
  `img` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=6013 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `housedeal` (
  `no` int NOT NULL AUTO_INCREMENT,
  `dong` varchar(30) NOT NULL,
  `AptName` varchar(50) NOT NULL,
  `code` varchar(30) NOT NULL,
  `dealAmount` varchar(50) NOT NULL,
  `buildYear` varchar(30) DEFAULT NULL,
  `dealYear` varchar(30) DEFAULT NULL,
  `dealMonth` varchar(30) DEFAULT NULL,
  `dealDay` varchar(30) DEFAULT NULL,
  `area` varchar(30) DEFAULT NULL,
  `floor` varchar(30) DEFAULT NULL,
  `jibun` varchar(30) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `rentMoney` varchar(30) DEFAULT NULL,
  `saleno` int DEFAULT NULL,
  `userid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `userid` (`userid`),
  CONSTRAINT `housedeal_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `project_member` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68873 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `saleimg` (
  `no` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) DEFAULT NULL,
  `imgname` varchar(100) DEFAULT NULL,
  `saleno` int DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `userid` (`userid`),
  CONSTRAINT `saleimg_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `project_member` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sidocode` (
  `sido_code` varchar(10) NOT NULL,
  `sido_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`sido_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `guguncode` (
  `gugun_code` varchar(10) NOT NULL,
  `gugun_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`gugun_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `location` (
  `loccode` int DEFAULT NULL,
  `si` text,
  `gu` text,
  `dong` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `shop` (
  `no` varchar(30) NOT NULL,
  `shopname` varchar(30) DEFAULT NULL,
  `localname` varchar(30) DEFAULT NULL,
  `code1` varchar(30) DEFAULT NULL,
  `codename1` varchar(30) DEFAULT NULL,
  `code2` varchar(30) DEFAULT NULL,
  `codename2` varchar(30) DEFAULT NULL,
  `code3` varchar(30) DEFAULT NULL,
  `codename3` varchar(30) DEFAULT NULL,
  `code4` varchar(30) DEFAULT NULL,
  `codename4` varchar(30) DEFAULT NULL,
  `citycode` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `gucode` varchar(30) DEFAULT NULL,
  `gu` varchar(30) DEFAULT NULL,
  `dongcode` varchar(30) DEFAULT NULL,
  `dong` varchar(30) DEFAULT NULL,
  `jibuaddress` varchar(30) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `oldpostcode` varchar(60) DEFAULT NULL,
  `postcode` varchar(60) DEFAULT NULL,
  `lng` varchar(30) DEFAULT NULL,
  `lat` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pollution` (
  `name` varchar(50) NOT NULL,
  `bizcode` varchar(30) NOT NULL,
  `address` varchar(80) NOT NULL,
  `dongcode` varchar(30) NOT NULL,
  `dong` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `subway` (
  `subname` text,
  `linenum` int DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `green` (
  `name` text,
  `address` text,
  `dongcode` int DEFAULT NULL,
  `dong` text,
  `lng` double DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `bizcode` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `notice` (
  `articleno` int NOT NULL AUTO_INCREMENT,
  `subject` varchar(30) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `click` int DEFAULT NULL,
  `regtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleno`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `qna_board` (
  `qna_no` int NOT NULL AUTO_INCREMENT COMMENT '질문번호',
  `qna_title` varchar(300) NOT NULL COMMENT '질문제목',
  `qna_content` varchar(4000) NOT NULL COMMENT '질문내용',
  `qna_datetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '질문일시',
  `reply_content` varchar(4000) DEFAULT NULL COMMENT '답변내용',
  `reply_datetime` timestamp NULL DEFAULT NULL COMMENT '답변일시',
  `reply_userid` varchar(20) DEFAULT NULL COMMENT '답변자아이디',
  `qna_userid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`qna_no`),
  KEY `qna_to_user_fk2` (`reply_userid`),
  KEY `qna_userid` (`qna_userid`),
  CONSTRAINT `qna_board_ibfk_1` FOREIGN KEY (`qna_userid`) REFERENCES `project_member` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `qna_to_user_fk2` FOREIGN KEY (`reply_userid`) REFERENCES `project_member` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='질문게시판';

CREATE TABLE `reply_board` (
  `qna_no` int DEFAULT NULL,
  `reply_userid` varchar(20) DEFAULT NULL,
  `reply_content` varchar(4000) DEFAULT NULL,
  `reply_datetime` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `favorite` (
  `favno` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) NOT NULL,
  `loccode` varchar(80) NOT NULL,
  PRIMARY KEY (`favno`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `zzim` (
  `zzimno` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) NOT NULL,
  `dealno` int NOT NULL,
  PRIMARY KEY (`zzimno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
