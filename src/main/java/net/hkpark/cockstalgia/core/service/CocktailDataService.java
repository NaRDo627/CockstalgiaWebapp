package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.dto.CocktailDto;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.cockstalgia.core.util.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CocktailDataService {
    private final CocktailRepository cocktailRepository;

    public List<CocktailDto> getAllCocktailList() {
        List<Cocktail> cocktailList = cocktailRepository.findAll();
        return cocktailList.stream()
                .map(x -> ObjectMapper.mapObject(x, CocktailDto.class))
                .collect(Collectors.toList());
    }
}
