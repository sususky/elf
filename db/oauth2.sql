-- client用户表
DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE oauth_client_details
(
  `client_id` varchar(32) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端信息表';

INSERT INTO `oauth_client_details` VALUES
('client', null, '{bcrypt}$2a$10$bLm3XSl686Oqsd.KEKpRtu3AzFsz5d84bGVu1wQPw4JN/4PgW/Bo.', 'server', 'client_credentials,refresh_token', null, null, null, null, null, 'true'),
('password', null, '{bcrypt}$2a$10$bLm3XSl686Oqsd.KEKpRtu3AzFsz5d84bGVu1wQPw4JN/4PgW/Bo.', 'server', 'password,refresh_token', null, null, null, null, null, 'true'),
('code', null, '{bcrypt}$2a$10$bLm3XSl686Oqsd.KEKpRtu3AzFsz5d84bGVu1wQPw4JN/4PgW/Bo.', 'server', 'password,refresh_token,authorization_code,client_credentials', 'http://localhost:8090/login', null, null, null, null, 'true');
