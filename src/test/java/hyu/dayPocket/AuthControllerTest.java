package hyu.dayPocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.utils.JwtTokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository userRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private ObjectMapper objectMapper;



    @BeforeEach
    void setUp() {
    }

    @Test
    void testSignupApi() throws Exception {

    }
}
