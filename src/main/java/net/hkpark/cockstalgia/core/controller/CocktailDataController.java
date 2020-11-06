package net.hkpark.cockstalgia.core.controller;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.dto.CocktailDto;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.core.service.CocktailEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/data/cocktail/")
@RequiredArgsConstructor
public class CocktailDataController {
    private final CocktailEntityService cocktailEntityService;

    @GetMapping(value = {"v1"})
    public ResponseEntity<ResultDto> cocktailGet() {
        List<CocktailDto> cocktails = cocktailEntityService.getAllCocktailList();
        return ResponseEntity.ok(ResultDto.builder().message("OK").data(cocktails).build());
    }

    @GetMapping(value = {"{cocktailNo}/v1"})
    public ResponseEntity<ResultDto> cocktailGetOne(@PathVariable("cocktailNo") Integer cocktailNo) {
        CocktailDto cocktail = cocktailEntityService.getCocktailById(cocktailNo);
        return ResponseEntity.ok(ResultDto.builder().message("OK").data(cocktail).build());
    }

    @PostMapping(value = {"v1"})
    public ResponseEntity<ResultDto> cocktailPost(@RequestBody CocktailDto cocktailDto) {
        cocktailEntityService.saveCocktail(cocktailDto);
        return ResponseEntity.ok(ResultDto.builder().message("OK").build());
    }

    @PutMapping(value = {"v1"})
    public ResponseEntity<ResultDto> cocktailPut(@RequestBody CocktailDto cocktailDto) {
        cocktailEntityService.updateCocktail(cocktailDto);
        return ResponseEntity.ok(ResultDto.builder().message("OK").build());
    }
}
