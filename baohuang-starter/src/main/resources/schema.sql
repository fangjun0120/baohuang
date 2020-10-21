CREATE TABLE `game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_time` bigint DEFAULT NULL,
  `updated_time` bigint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  `player_list` varchar(100) DEFAULT NULL,
  `king` bigint DEFAULT NULL,
  `agent` bigint DEFAULT NULL,
  `agent_card` varchar(10) DEFAULT NULL,
  `has_revolution` enum('Y','N') DEFAULT NULL,
  `is_king_over_four_public` enum('Y','N') DEFAULT NULL,
  `player_rank` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

CREATE TABLE `game_hand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_time` bigint DEFAULT NULL,
  `updated_time` bigint DEFAULT NULL,
  `game_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `hand` varchar(255) DEFAULT NULL,
  `init` enum('Y','N') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
