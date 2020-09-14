package jfang.games.baohuang.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @author jfang
 */
@Data
@Entity
@Table(name = "user")
public class GamePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_time")
    private Long created;

    @Column(name = "updated_time")
    private Long updated;
}
