package controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(locations = "classpath:spring-context.xml")
public class LoginControllerTest {
    @Autowired
    public  WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @Before
    public void setUp() {
       mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void download() throws Exception {
        String path = webApplicationContext.getServletContext().getRealPath("/upload");
        System.out.println(path);
        mockMvc.perform(get("/user/download/1")
                .header("Range", "bytes=0-20")
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3947.100 Safari/537.36"))
                .andExpect(status().isPartialContent());
    }

    @Test
    public void downloadHeaders() {
    }
}
