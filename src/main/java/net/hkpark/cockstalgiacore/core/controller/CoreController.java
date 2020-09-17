package net.hkpark.cockstalgiacore.core.controller;

import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class CoreController {
    @RequestMapping(value = "hello")
    public ResponseEntity<ResultDto> hello() {
        return ResponseEntity.ok(ResultDto.builder().message("HI!").build());
    }
}
