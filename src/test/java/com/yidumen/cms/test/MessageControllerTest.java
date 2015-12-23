package com.yidumen.cms.test;

import junit.framework.TestCase;
import me.chanjar.weixin.common.util.crypto.SHA1;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Test
    public void testProcessMessage() throws Exception {
        final String token = "spring_test";
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String nonce = "123456";
        final String echostr = "中文";
        final String[] sortArray = new String[]{token, timestamp.toString(), nonce};
        Arrays.sort(sortArray);

        final String signature = SHA1.gen(sortArray);
        logger.info("测试启动");
        mockMvc.perform(get("/api/wechat/message").servletPath("/api")
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .param("echostr", echostr))
                .andExpect(status().isOk())
                .andExpect(content().string("中文"));
        mockMvc.perform(post("/api/wechat/message").servletPath("/api")
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
                .andDo(MockMvcResultHandlers.print());
        mockMvc.perform(post("/api/wechat/message").servletPath("/api")
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .content("<xml>\n" +
                        " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                        " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                        " <CreateTime>1348831861</CreateTime>\n" +
                        " <MsgType><![CDATA[text]]></MsgType>\n" +
                        " <Content>2</Content>\n" +
                        " <MsgId>1234567890123458</MsgId>\n" +
                        " </xml>"))
                .andDo(MockMvcResultHandlers.print());
        mockMvc.perform(post("/api/wechat/message").servletPath("/api")
                .param("signature", signature)
                .param("timestamp", timestamp.toString())
                .param("nonce", nonce)
                .content("<xml>\n" +
                        " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                        " <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
                        " <CreateTime>1348831862</CreateTime>\n" +
                        " <MsgType><![CDATA[text]]></MsgType>\n" +
                        " <Content>3</Content>\n" +
                        " <MsgId>1234567890123457</MsgId>\n" +
                        " </xml>"))
                .andDo(MockMvcResultHandlers.print());
    }
}