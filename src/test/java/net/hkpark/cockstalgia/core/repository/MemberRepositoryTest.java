package net.hkpark.cockstalgia.core.repository;

import net.hkpark.cockstalgia.core.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// TODO DBunit 적용
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class,
//        TransactionDbUnitTestExecutionListener.class })
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 멤버_삽입_테스트() {
        // given
        Member newMember = Member.builder()
                .name("사람이름")
                .kakaoBotUserId("asdf")
                .kakaoPlusFriendKey("1234")
                .build();

        // when
        Member savedMember = memberRepository.saveAndFlush(newMember);


        // then
        assertNotNull(savedMember.getUserNo());
        assertEquals(savedMember.getName(), "사람이름");
        assertEquals(savedMember.getKakaoBotUserId(), "asdf");
        assertEquals(savedMember.getKakaoPlusFriendKey(), "1234");
        assertEquals(savedMember.getRegDate(), newMember.getRegDate());
    }

    @Test
    public void 멤버_삽입_중복_테스트() {
        // given
        Member newMember = Member.builder()
                .name("사람이름")
                .kakaoBotUserId("asdf")
                .kakaoPlusFriendKey("1234")
                .build();

        Member savedMember = memberRepository.saveAndFlush(newMember);
        Member newMember2 = Member.builder()
                .name("사람이름")
                .kakaoBotUserId("asdf")
                .kakaoPlusFriendKey("1234")
                .build();

        // when
        assertThrows(DataIntegrityViolationException.class, () -> memberRepository.save(newMember2));

        // then - throws
    }
}
