package net.hkpark.cockstalgia.core.controller;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.dto.MemberDto;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class CoreController {
    private final MemberRepository memberRepository;

    @GetMapping(value = "hello")
    public ResponseEntity<ResultDto> hello() {
        return ResponseEntity.ok(ResultDto.builder().message("HI!").build());
    }

    @PostMapping(value = "member")
    public ResponseEntity<ResultDto> registMember(@RequestBody MemberDto memberDto) {
        memberRepository.save(memberDto.toEntity());
        return ResponseEntity.ok(ResultDto.builder().message("OK").build());
    }
}
