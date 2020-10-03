package net.hkpark.cockstalgia.core.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no", nullable = false)
    private Integer adminNo;

    @OneToOne
    @JoinColumn(name = "user_no", referencedColumnName = "user_no")
    private Member member;

    @Column(name = "authorized", nullable = false)
    private boolean authorized;
}
