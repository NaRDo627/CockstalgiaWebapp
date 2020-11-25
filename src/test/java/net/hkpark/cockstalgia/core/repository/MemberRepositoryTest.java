package net.hkpark.cockstalgia.core.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import net.hkpark.config.DbUnitConfig;
//import net.hkpark.config.TransactionConfig;
import net.hkpark.cockstalgia.core.constant.MemberType;

import net.hkpark.cockstalgia.core.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Import(DbUnitConfig.class)
@DataJpaTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DatabaseSetup("classpath:dbunit/dataset/dao/MemberRepositoryTest/멤버_삽입_테스트_setup.xml")
    @ExpectedDatabase(value = "classpath:dbunit/dataset/dao/MemberRepositoryTest/멤버_삽입_테스트_expected.xml"
            , assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 멤버_삽입_테스트() {
        // given - DatabaseSetup
        Member newMember = Member.builder()
                .name("사람이름")
                .memberIdentityKey("test")
                .kakaoBotUserId("asdfg")
                .kakaoPlusFriendKey("12345")
                .build();

        // when
        Member savedMember = memberRepository.saveAndFlush(newMember);


        // then - ExpectedDatabase
    }

    @Test
    @DatabaseSetup("classpath:dbunit/dataset/dao/MemberRepositoryTest/멤버_삽입_중복_테스트_setup.xml")
    public void 멤버_삽입_중복_테스트() {
        // given
        Member newMember = Member.builder()
                .name("사람이름")
                .memberIdentityKey("test")
                .kakaoBotUserId("asdf")
                .kakaoPlusFriendKey("1234")
                .build();

        // when
        assertThrows(DataIntegrityViolationException.class, () -> memberRepository.save(newMember));

        // then - throws
    }
}
