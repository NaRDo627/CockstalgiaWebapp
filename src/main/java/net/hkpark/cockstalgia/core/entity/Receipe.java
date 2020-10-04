package net.hkpark.cockstalgia.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "receipe")
public class Receipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_no", nullable = false)
    private Integer recipeNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "cocktail_no", referencedColumnName = "cocktail_no")
    private Cocktail cocktail;

    @Column(name = "is_official", nullable = false)
    private boolean isOfficial;

    @Builder.Default
    @Column(name = "reg_date")
    private final LocalDateTime regDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "mod_date")
    private LocalDateTime modDate = LocalDateTime.now();
}
