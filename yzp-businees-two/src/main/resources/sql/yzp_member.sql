CREATE TABLE `yzp_member`
(
    `member_id`     int NOT NULL,
    `member_number` varchar(32)  DEFAULT NULL,
    `member_name`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;