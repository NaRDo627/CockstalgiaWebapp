package net.hkpark.cockstalgia.webview.admin.controller;

import net.hkpark.cockstalgia.core.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebViewAdminControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void 관리자_페이지_접속_시도_리다이렉션_테스트() throws Exception {
        // given
        String url = "/admin";

        // when
        mvc.perform(get(url))

        // then
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 관리자_페이지_접속_시도_비인가_테스트() throws Exception {
        // given
        String url = "/admin";

        // when
        mvc.perform(get(url))

                // then
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void 관리자_페이지_접속_시도_인가_리다이렉션_테스트() throws Exception {
        // given
        String url = "/admin";


        // when
        mvc.perform(get(url))

                // then
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void 관리자_페이지_접속_시도_인가_테스트() throws Exception {
        // given
        String url = "/admin";
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("currentMember", new Member());

        // when
        mvc.perform(get(url)
                .session(mockHttpSession))

                // then
                .andExpect(status().isOk());
    }
}