CREATE TABLE `game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_time` bigint DEFAULT NULL,
  `updated_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `created_time` bigint DEFAULT NULL,
                        `updated_time` bigint DEFAULT NULL,
                        `username` varchar(45) DEFAULT NULL,
                        `password` varchar(100) DEFAULT NULL,
                        `portrait` varchar(200) DEFAULT NULL,
                        `roles` varchar(45) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;