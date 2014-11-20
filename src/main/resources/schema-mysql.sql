CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `api_token` varchar(64) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `api_token_UNIQUE` (`api_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='user table';


CREATE TABLE IF NOT EXISTS `followers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `following` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index ix_followers_username_following` (`username`,`following`),
  KEY `fk_source_users_idx` (`username`),
  KEY `fk_target_users_idx` (`following`),
  CONSTRAINT `fk_source_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_target_users` FOREIGN KEY (`following`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='tracks user followers';

CREATE TABLE IF NOT EXISTS `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(50) NOT NULL,
  `text` varchar(160) NOT NULL,
  `creation_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_author_messages_users_idx` (`author`),
  CONSTRAINT `fk_author_messages_users` FOREIGN KEY (`author`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='user message store';



