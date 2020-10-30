package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.cocktail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.core.constant.TestJsonString;
import net.hkpark.cockstalgia.core.entity.Cocktail;
import net.hkpark.cockstalgia.core.repository.CocktailRepository;
import net.hkpark.cockstalgia.core.util.JsonUtil;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.response.ComponentDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CocktailBasicServiceTest {

    @InjectMocks
    private CocktailBasicService cocktailBasicService;

    @Mock
    private CocktailRepository cocktailRepository;

    @Test
    @Ignore
    public void getBaseRecipeLists() {

    }

    @Test
    public void 레시피_조회_기본_테스트() throws Exception {
        // given
        String requestJson = TestJsonString.REQUEST_CHATBOT_COCKTAIL_RECEIPE;
        SkillRequestDto skillRequestDto = JsonUtil.convertJsonStringToObjectClass(requestJson, SkillRequestDto.class);
        Cocktail cocktail = Cocktail.builder()
                .cocktailNo(1)
                .name("맨해튼")
                .description("맨해튼설명")
                .imageUrl("http://manhatten.imageurl.com")
                .base(LiquorType.WHISKEY)
                .simpleRecipe("맨해튼레시피")
                .build();
        when(cocktailRepository.findByNameContaining(eq("맨해튼"))).thenReturn(Optional.of(cocktail));

        // when
        SkillResponseDto skillResponseDto = cocktailBasicService.getRecipe(skillRequestDto);

        // then
        List<ComponentDto> outputs = skillResponseDto.getTemplate().getOutputs();
        assertEquals(outputs.size(), 2);
        assertNotNull(outputs.get(0).getBasicCard());
        assertEquals(outputs.get(0).getBasicCard().getTitle(), "맨해튼");
        assertEquals(outputs.get(0).getBasicCard().getDescription(), "맨해튼설명");
        assertEquals(outputs.get(0).getBasicCard().getThumbnail().getImageUrl(), "http://manhatten.imageurl.com");

//        assertNotNull(outputs.get(1).getSimpleText());
//        assertEquals(outputs.get(1).getSimpleText().getText(), "맨해튼레시피");
    }
}