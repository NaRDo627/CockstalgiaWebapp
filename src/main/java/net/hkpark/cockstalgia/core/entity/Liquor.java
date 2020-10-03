package net.hkpark.cockstalgia.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.hkpark.cockstalgia.core.constant.LiquorType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "liquor")
public class Liquor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liquor_no", nullable = false)
    private Integer liquorNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LiquorType type;

    @OneToMany(mappedBy = "base")
    private List<Cocktail> mappedCocktails;
}
