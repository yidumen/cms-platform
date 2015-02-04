package com.yidumen.cms.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.yidumen.cms.dao.Account;
import com.yidumen.cms.dao.constant.AccountGroup;
import com.yidumen.cms.service.UserService;
import com.yidumen.cms.service.exception.IllDataException;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public final class UserServiceImpl implements UserService {

    private final Logger LOG;
    private final Account dao;

    public UserServiceImpl() {
        this.LOG = LoggerFactory.getLogger(this.getClass());
        this.dao = Account.dao;
    }

    @Override
    public Account find(Long id) {
        return dao.findById(id);
    }

    @Override
    public Account verify(final String username, final String password)
            throws AccountNotFoundException, FailedLoginException {
        final Account result = dao.findByName(username.toLowerCase());
        if (result == null || result.get("userGroup").equals(AccountGroup.USER)) {
            throw new AccountNotFoundException("没有这个用户");
        }
        if (result.get("password").equals(password)) {
            return result;
        } else {
            throw new FailedLoginException("密码错误");
        }
    }

    @Override
    @Before(Tx.class)
    public Account register(String username, final String password, final String ip)
            throws AccountException, IllDataException {
        username = username.toLowerCase();
        Account user = dao.findByName(username);
        if (user != null) {
            throw new AccountException("用户已存在");
        }
        user = new Account();
        if (checkPhone(username)) {
            user.set("username", username);
        } else if (checkEmail(username)) {
            user.set("email", username);
        } else {
            throw new IllDataException("用户名格式不对");
        }
        user.set("password", password)
                .set("userGroup", AccountGroup.USER.ordinal())
                .set("createdate", new Date(System.currentTimeMillis()))
                .set("status", true)
                .save();
        return user;
    }

    private boolean checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email.toLowerCase());
        return matcher.matches();
    }

    private boolean checkPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[0,0-9])|(17[0,0-9])|(18[0,0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }

    @Override
    @Before(Tx.class)
    public void update(Account account) {
        account.update();
    }

}
