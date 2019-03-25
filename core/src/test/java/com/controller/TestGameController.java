package com.controller;

import com.config.WebConfig;
import com.repository.NumberRepository;
import com.service.NumberServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("com.config")
@ContextConfiguration(classes = WebConfig.class)
public class TestGameController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    NumberRepository numberRepository;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void TestStart() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("entityManagerFactory"));
    }

    @Test
    public void TestGivenUrl_whenMockMVC_ExpectedResponseHello() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello!"))
                .andReturn();
        assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void TestGivenUrlUpdate_whenMockMVC_ExpectedResponseLooser() throws Exception {
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        numberServiceImpl.setNumber(5);
        numberServiceImpl.updateNumberById(1,1);
        MvcResult mvcResult = this.mockMvc.perform(get("/1/guess/2"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Looser!"))
                .andReturn();
        assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void TestGivenUrlUpdate_whenMockMVC_ExpectedResponseWinner() throws Exception {
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        numberServiceImpl.setNumber(5);
        numberServiceImpl.updateNumberById(1,1);
        MvcResult mvcResult = this.mockMvc.perform(get("/1/guess/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Winner!"))
                .andReturn();
        assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void TestGivenUrlUpdate_whenIdIsInvalid_ExpectedException() throws Exception {
        NumberServiceImpl numberServiceImpl = new NumberServiceImpl(numberRepository);
        MvcResult mvcResult = this.mockMvc.perform(get("/100/guess/1"))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exmessage").value("There is No User with id :100 !"))
                .andReturn();
        assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());
    }

}