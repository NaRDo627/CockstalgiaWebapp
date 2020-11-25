package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception.MemberAlreadyExistsException;
import net.hkpark.cockstalgia.core.constant.ErrorMessage;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import net.hkpark.cockstalgia.core.util.SecurityUtil;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberEntityService {
    private final MemberRepository memberRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Member saveMember(Member newMember) {
        try {
            return memberRepository.saveAndFlush(newMember);
        } catch (DataIntegrityViolationException e) { // 이 경우 중복 데이터일 가능성이 높다.
            String msg = ErrorMessage.DB_INSERT_FAILURE_ALREADY_EXISTS;
            log.error(msg, "member", e);
            throw new MemberAlreadyExistsException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Member saveMemberWithKeyBaseVo(@Valid MemberIdentityKeyBaseVo baseVo) {
        Member newMember = Member.builder()
                .name(baseVo.getRealname())
                .memberIdentityKey(SecurityUtil.encodeUserKey(baseVo))
                .isActive(true)
                .build();

        return saveMember(newMember);
    }
}
