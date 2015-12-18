package com.yidumen.cms.test;

import com.yidumen.cms.entity.Account;
import com.yidumen.cms.entity.Duty;
import com.yidumen.cms.entity.Permission;
import com.yidumen.cms.repository.HibernateRepository;
import junit.framework.TestCase;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 蔡迪旻
 *         2015年12月02日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test.xml")
public class HibernateTester extends TestCase {

    @Qualifier("accountRepository")
    @Autowired
    private HibernateRepository<Account> accountRepository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Transactional
    @Rollback(false)
    public void initDatabase() throws Exception {
        final Account entity = new Account();
        entity.setName("cnjimmyshao");
        entity.setPhone("15801801018");
        entity.setEmail("zhining@yidumen.com");
        entity.setRealname("邵宁");
        entity.setBuddhismname("智宁");
        entity.setNickname("白开水");
        entity.setPassword(DigestUtils.md5Hex("666666"));
        entity.setStatus(true);
        final Duty duty = new Duty();
        duty.setName("管理员");
        duty.addPermission(new Permission("ADMIN", "易度门网站管理员"));
        entity.setDuty(duty);
        final Long id = accountRepository.create(entity);
        final Account account = accountRepository.find(id);
        System.out.println(account);
        assertEquals("15801801018", account.getPhone());
    }

    @Test
    public void testDatabase() {
        assertEquals("15801801018", accountRepository.find(4L).getPhone());
    }

}