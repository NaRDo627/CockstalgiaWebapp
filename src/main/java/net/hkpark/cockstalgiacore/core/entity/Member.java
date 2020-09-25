package net.hkpark.cockstalgiacore.core.entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false)
    private Integer userNo;

//    @Column(name = "id", unique = true, nullable = false)
//    private String id;
//
//    @Column(name = "password", nullable = false)
//    private String password;

    @Column(name = "name", nullable = false)
    private String name;

//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "phone")
//    private String phone;

    @Column(name = "kakao_bot_user_id", unique = true)
    private String kakaoBotUserId;

    @Column(name = "kakao_plus_friend_key", unique = true)
    private String kakaoPlusFriendKey;

    @Builder.Default
    @Column(name = "reg_date")
    private final LocalDateTime regDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "mod_date")
    private LocalDateTime modDate = LocalDateTime.now();

    @OneToOne
    private Admin admin;

    public void registerAdmin() {
        this.admin = Admin.builder().member(this).authorized(false).build();
        this.modDate = LocalDateTime.now();
    }
}
