package jfang.games.baohuang.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @author jfang
 */
@Data
@Entity
@Table(name = "game_hand")
public class HandPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "user_id")
    private Long userId;

    @Column
    private String hand;

    @Column(columnDefinition = "enum('Y','N') DEFAULT 'N'")
    private Boolean init;

    @Column(name = "created_time")
    private Long created;

    @Column(name = "updated_time")
    private Long updated;
}
