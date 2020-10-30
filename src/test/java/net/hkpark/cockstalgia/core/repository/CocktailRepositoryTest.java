package net.hkpark.cockstalgia.core.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import net.hkpark.config.DbUnitConfig;
import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Import(DbUnitConfig.class)
@DataJpaTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
class CocktailRepositoryTest {
    @Autowired
    CocktailRepository cocktailRepository;

    @Test
    @DatabaseSetup("classpath:dbunit/dataset/dao/CocktailRepositoryTest/칵테일_베이스_기반_조회_테스트_setup.xml")
    public void 칵테일_베이스_기반_조회_테스트() {
        // given - DatabaseSetup

        // when
        List<Cocktail> cocktails = cocktailRepository.findAllByBase(LiquorType.RUM);

        // then
        assertEquals(cocktails.size(), 1);
        assertEquals(cocktails.get(0).getCocktailNo(), 2);
        assertEquals(cocktails.get(0).getBase(), LiquorType.RUM);
        assertEquals(cocktails.get(0).getDescription(), "칵테일을 처음 접해보는 초심자, 여성들도 들어는 봤을 정도로 유명한 칵테일이다. 코코넛, 파인애플 맛을 위주로 달달함이 지배적이며, 끈적한 식감이 특징적이다. 조주기능사 레시피는 피나믹스를 사용하지만, 업장에서는 보통 생과일이나 통조림을 사용하기도 한다.");
        assertEquals(cocktails.get(0).getImageUrl(), "http://k.kakaocdn.net/dn/DIi7c/btqFujrTkjN/sx8fE7odsk2D58y60UyLE0/800x800.jpg");
        assertEquals(cocktails.get(0).getModDate().getYear(), 2020);
        assertEquals(cocktails.get(0).getModDate().getMonthValue(), 10);
        assertEquals(cocktails.get(0).getModDate().getDayOfMonth(), 7);
        assertEquals(cocktails.get(0).getName(), "피나콜라다");
    }
}
