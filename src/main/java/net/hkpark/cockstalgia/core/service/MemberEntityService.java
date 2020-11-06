package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception.MemberAlreadyExistsException;
import net.hkpark.cockstalgia.core.constant.ErrorMessage;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberEntityService {
    private final MemberRepository memberRepository;

    @Transactional
    public void saveMember(Member newMember) {
        try {
            memberRepository.save(newMember);
        } catch (DataIntegrityViolationException e) { // 이 경우 중복 데이터일 가능성이 높다.
            String msg = ErrorMessage.DB_INSERT_FAILURE_ALREADY_EXISTS;
            log.error(msg, "member", e);
            throw new MemberAlreadyExistsException(e);
        }
    }


}
