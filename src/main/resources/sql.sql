DROP TABLE user;
DROP TABLE student;

ALTER TABLE teacher DROP FOREIGN KEY teacher_ibfk_1;
ALTER TABLE classes DROP FOREIGN KEY classes_ibfk_1;

DROP TABLE teacher;
DROP TABLE classes;


CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

CREATE TABLE `teacher` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `tname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`tid`),
  KEY `cid` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `classes` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`cid`),
  KEY `tid` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `student` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `sname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `cid` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE teacher ADD CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `classes` (`cid`) ON DELETE SET NULL ON UPDATE SET NULL;
ALTER TABLE classes ADD CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `teacher` (`tid`) ON DELETE SET NULL ON UPDATE SET NULL;
ALTER TABLE student ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `classes` (`cid`) ON DELETE SET NULL ON UPDATE SET NULL;