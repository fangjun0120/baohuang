package jfang.games.baohuang.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @author jfang
 */
@Data
@Entity
@Table(name = "user")
public class UserPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_time")
    private Long created;

    @Column(name = "updated_time")
    private Long updated;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "portrait")
    private String portrait;

    @Column(name = "roles")
    private String roles;
}
