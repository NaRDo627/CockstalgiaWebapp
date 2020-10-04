package net.hkpark.cockstalgia.core.controller;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.cockstalgia.core.dto.CocktailDto;
import net.hkpark.cockstalgia.core.dto.MemberDto;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.cockstalgia.core.util.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/data/cocktail/")
@RequiredArgsConstructor
public class CocktailDataController {
    private final CocktailRepository cocktailRepository;
    
    @GetMapping(value = {"v1"})
    public ResponseEntity<ResultDto> cocktailGet() {
        List<Cocktail> cocktails = cocktailRepository.findAll();
        return ResponseEntity.ok(ResultDto.builder().message("OK").data(cocktails).build());
    }

    @PrintArguments
    @PostMapping(value = {"v1"})
    public ResponseEntity<ResultDto> cocktailPost(@RequestBody CocktailDto cocktailDto) {
        cocktailRepository.save(ObjectMapper.mapObject(cocktailDto, Cocktail.class));
        return ResponseEntity.ok(ResultDto.builder().message("OK").build());
    }
}
