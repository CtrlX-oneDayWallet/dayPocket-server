package hyu.dayPocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.SignupData;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.utils.JwtTokenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private Member testMember;



    @BeforeEach
    void setUp() {
        testMember = Member.builder().
                name("test")
                .password("test")
                .asset(12334L)
                .fiScore(1000L)
                .fiPoint(1000)
                .phoneNumber("010-2222-2222")
                .build();

    }

    @Test
    void testSignupApi() throws Exception {
        SignupData data = new SignupData("myname", "pass", "010-5585-9203");

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());

        Member member = memberRepository.findByPhoneNumber("010-5585-9203").get();
        assertThat(member.getName()).isEqualTo("myname");
    }


}
