package net.hkpark.cockstalgia.core.controller;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.cockstalgia.core.dto.CocktailDto;
import net.hkpark.cockstalgia.core.dto.MemberDto;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.cockstalgia.core.service.CocktailDataService;
import net.hkpark.cockstalgia.core.util.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/data/cocktail/")
@RequiredArgsConstructor
public class CocktailDataController {
    private final CocktailDataService cocktailDataService;

    @GetMapping(value = {"v1"})
    public ResponseEntity<ResultDto> cocktailGet() {
        List<CocktailDto> cocktails = cocktailDataService.getAllCocktailList();
        return ResponseEntity.ok(ResultDto.builder().message("OK").data(cocktails).build());
    }

    @GetMapping(value = {"{cocktailNo}/v1"})
    public ResponseEntity<ResultDto> cocktailGetOne(@PathVariable("cocktailNo") Integer cocktailNo) {
        CocktailDto cocktail = cocktailDataService.getCocktailById(cocktailNo);
        return ResponseEntity.ok(ResultDto.builder().message("OK").data(cocktail).build());
    }

    @PostMapping(value = {"v1"})
    public ResponseEntity<ResultDto> cocktailPost(@RequestBody CocktailDto cocktailDto) {
        cocktailDataService.saveCocktail(cocktailDto);
        return ResponseEntity.ok(ResultDto.builder().message("OK").build());
    }

    @PutMapping(value = {"v1"})
    public ResponseEntity<ResultDto> cocktailPut(@RequestBody CocktailDto cocktailDto) {
        cocktailDataService.saveCocktail(cocktailDto);
        return ResponseEntity.ok(ResultDto.builder().message("OK").build());
    }
}
