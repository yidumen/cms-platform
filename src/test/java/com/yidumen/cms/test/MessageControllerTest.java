package com.yidumen.cms.test;

import junit.framework.TestCase;
import me.chanjar.weixin.common.util.crypto.SHA1;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author 蔡迪旻
 *         2015年12月22日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@EnableTransactionManagement
@WebAppConfiguration
@ContextConfiguration("/test.xml")
public class MessageControllerTest extends TestCase {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private Logger logger;
    private Timestamp timestamp;
    private String nonce;
    private String echostr;
    private String signature;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new CharacterEncodingFilter("utf-8", true),"/api/*").build();
        logger = LoggerFactory.getLogger(this.getClass());
        final String token = "spring_test";
        timestamp = new Timestamp(System.currentTimeMillis());
        logger.info("timestamp: {}", timestamp);
        nonce = "123456";
        echostr = "random";
        final String[] sortArray = new String[]{token, timestamp.toString(), nonce};
        Arrays.sort(sortArray);

        signature = SHA1.gen(sortArray);
        logger.info("signature: {}", signature);
    }

    @Test
    public void testEchostr() throws Exception {
        mockMvc.perform(get("/api/wechat/message").servletPath("/api")
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .param("echostr", echostr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(echostr));
    }

    @Test
    public void testTextMatch() throws Exception {
        mockMvc.perform(post("/api/wechat/message").servletPath("/api").contentType(MediaType.TEXT_XML)
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .content("<xml>\n" +
                        " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                        " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                        " <CreateTime>1348831860</CreateTime>\n" +
                        " <MsgType><![CDATA[text]]></MsgType>\n" +
                        " <Content>1</Content>\n" +
                        " <MsgId>1234567890123456</MsgId>\n" +
                        " </xml>"))
                .andDo(print());
        mockMvc.perform(post("/api/wechat/message").servletPath("/api").contentType("text/xml;utf-8")
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .content("<xml>\n" +
                        " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                        " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                        " <CreateTime>1348831861</CreateTime>\n" +
                        " <MsgType><![CDATA[text]]></MsgType>\n" +
                        " <Content><![CDATA[一]]></Content>\n" +
                        " <MsgId>1234567890123458</MsgId>\n" +
                        " </xml>").characterEncoding("utf-8"))
                .andDo(print());
    }

    @Test
    public void testContainText() throws Exception {
        mockMvc.perform(post("/api/wechat/message").servletPath("/api").contentType("text/xml")
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .content("<xml>\n" +
                        " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                        " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                        " <CreateTime>1348831862</CreateTime>\n" +
                        " <MsgType><![CDATA[text]]></MsgType>\n" +
                        " <Content><![CDATA[介绍一下老师]]></Content>\n" +
                        " <MsgId>1234567890123459</MsgId>\n" +
                        " </xml>"))
                .andDo(print())
                .andDo(log());
        mockMvc.perform(post("/api/wechat/message").servletPath("/api").contentType("text/xml")
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .content("<xml>\n" +
                        " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                        " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                        " <CreateTime>1348831863</CreateTime>\n" +
                        " <MsgType><![CDATA[text]]></MsgType>\n" +
                        " <Content><![CDATA[give me a video, please.]]></Content>\n" +
                        " <MsgId>1234567890123460</MsgId>\n" +
                        " </xml>"))
                .andDo(print());
    }

    @Test(timeout = 1000)
    public void testDateSearch() throws Exception {
        mockMvc.perform(post("/api/wechat/message").servletPath("/api").contentType(MediaType.TEXT_XML)
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .content("<xml>\n" +
                        " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                        " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                        " <CreateTime>1348831863</CreateTime>\n" +
                        " <MsgType><![CDATA[text]]></MsgType>\n" +
                        " <Content><![CDATA[150121]]></Content>\n" +
                        " <MsgId>1234567890123455</MsgId>\n" +
                        " </xml>"))
                .andDo(print());
    }

    @Test
    public void testButtonClick() throws Exception {
        mockMvc.perform(post("/api/wechat/message").servletPath("/api")
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .content("<xml>\n" +
                        "<ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                        "<FromUserName><![CDATA[FromUser]]></FromUserName>\n" +
                        "<CreateTime>123456789</CreateTime>\n" +
                        "<MsgType><![CDATA[event]]></MsgType>\n" +
                        "<Event><![CDATA[CLICK]]></Event>\n" +
                        "<EventKey><![CDATA[CHAT_ROOM]]></EventKey>\n" +
                        "</xml>"))
                .andDo(print());
    }


    @Test
    public void other() throws Exception {
        mockMvc.perform(post("/api/wechat/message").servletPath("/api").content("中文中文")).andDo(print());
    }
}