package net.hkpark.cockstalgia.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.hkpark.cockstalgia.core.annotation.ExcludeMapping;
import net.hkpark.cockstalgia.core.constant.LiquorType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cocktail")
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cocktail_no", nullable = false)
    private Integer cocktailNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "simple_recipe")
    private String simpleRecipe;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "base", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LiquorType base;

    @Builder.Default
    @Column(name = "reg_date")
    private final LocalDateTime regDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "mod_date")
    private LocalDateTime modDate = LocalDateTime.now();
}
