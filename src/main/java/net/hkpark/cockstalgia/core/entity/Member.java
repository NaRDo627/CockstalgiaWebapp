package net.hkpark.cockstalgia.core.entity;
import lombok.*;
import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.core.constant.MemberType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter @Setter
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
    @Column(name = "member_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private final MemberType memberType = MemberType.NEWBIE;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Builder.Default
    @Column(name = "reg_date")
    private final LocalDateTime regDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "mod_date")
    private LocalDateTime modDate = LocalDateTime.now();
}
