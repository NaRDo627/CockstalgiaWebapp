package net.hkpark.cockstalgia.core.repository;

import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import net.hkpark.cockstalgia.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CocktailRepository extends JpaRepository<Cocktail, Integer> {
    List<Cocktail> findAllByBase(LiquorType base);
    Optional<Cocktail> findByNameContaining(String name);
}
