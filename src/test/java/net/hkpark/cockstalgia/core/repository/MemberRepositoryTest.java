package net.hkpark.cockstalgia.core.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import net.hkpark.cockstalgia.core.entity.Member;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Disabled
    public void 멤버_삽입_테스트() {
        // given
        Member newMember = Member.builder()
                .name("사람이름")
                .kakaoBotUserId("asdf")
                .kakaoPlusFriendKey("1234")
                .build();

        // when
        Member savedMember = memberRepository.save(newMember);

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


        // when
        Member savedMember = memberRepository.save(newMember);
        Member newMember2 = Member.builder()
                .name("사람이름")
                .kakaoBotUserId("asdf")
                .kakaoPlusFriendKey("1234")
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> memberRepository.save(newMember2));

        // then
        assertNotNull(savedMember.getUserNo());
        assertEquals(savedMember.getName(), "사람이름");
        assertEquals(savedMember.getKakaoBotUserId(), "asdf");
        assertEquals(savedMember.getKakaoPlusFriendKey(), "1234");
        assertEquals(savedMember.getRegDate(), newMember.getRegDate());
    }
}