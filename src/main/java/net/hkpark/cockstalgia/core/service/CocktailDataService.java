package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.constant.ErrorMessage;
import net.hkpark.cockstalgia.core.dto.CocktailDto;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import net.hkpark.cockstalgia.core.exception.BusinessException;
import net.hkpark.cockstalgia.core.exception.EntityNotFoundException;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.cockstalgia.core.util.ObjectMapper;
import net.hkpark.cockstalgia.core.util.RequestValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    public CocktailDto getCocktailById(Integer cocktailNo) {
        Cocktail cocktail = cocktailRepository.findById(cocktailNo).orElseThrow(EntityNotFoundException::new);
        return ObjectMapper.mapObject(cocktail, CocktailDto.class);
    }

    @Transactional
    public void saveCocktail(CocktailDto cocktailDto) {
        Cocktail cocktail = ObjectMapper.mapObject(cocktailDto, Cocktail.class);
        cocktailRepository.save(cocktail);
        if (cocktail.getCocktailNo() == null) {
            log.error(ErrorMessage.DB_INSERT_FAILURE, "cocktail");
            throw new BusinessException();
        }
    }

    @Transactional
    public void updateCocktail(CocktailDto cocktailDto) {
        RequestValidator.requireNonNull(cocktailDto.getCocktailNo());
        Cocktail destCocktail = cocktailRepository.findById(cocktailDto.getCocktailNo()).orElseThrow(EntityNotFoundException::new);
        Cocktail sourceCocktail = ObjectMapper.mapObject(cocktailDto, Cocktail.class);
        BeanUtils.copyProperties(sourceCocktail, destCocktail, "id", "regDate");
    }
}
