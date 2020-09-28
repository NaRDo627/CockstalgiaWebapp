package net.hkpark.cockstalgia.core.controller;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.dto.MemberDto;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class CoreController {
    private final MemberRepository memberRepository;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public ResponseEntity<ResultDto> hello() {
        return ResponseEntity.ok(ResultDto.builder().message("HI!").build());
    }

    @RequestMapping(value = "member", method = RequestMethod.POST)
    public ResponseEntity<ResultDto> registMember(@RequestBody MemberDto memberDto) {
        memberRepository.save(memberDto.toEntity());
        return ResponseEntity.ok(ResultDto.builder().message("OK").build());
    }
}
