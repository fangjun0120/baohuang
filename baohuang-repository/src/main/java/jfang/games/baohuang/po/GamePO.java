package jfang.games.baohuang.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @author jfang
 */
@Data
@Entity
@Table(name = "game")
public class GamePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "player_list")
    private String playerList;

    @Column(name = "king")
    private Long king;

    @Column(name = "agent")
    private Long agent;

    @Column(name ="agent_card")
    private String agentCard;

    @Column(name = "has_revolution", columnDefinition = "enum('Y','N') DEFAULT 'N'")
    private Boolean hasRevolution;

    @Column(name = "is_king_over_four_public", columnDefinition = "enum('Y','N') DEFAULT 'N'")
    private Boolean isKingOverFourPublic;

    @Column(name = "player_rank")
    private String rankList;

    @Column(name = "created_time")
    private Long created;

    @Column(name = "updated_time")
    private Long updated;
}
