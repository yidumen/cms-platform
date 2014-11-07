package com.yidumen.cms.service.impl;

import com.yidumen.cms.service.UserService;
import com.yidumen.cms.service.exception.IllDataException;
import com.yidumen.dao.AccountDAO;
import com.yidumen.dao.constant.AccountGroup;
import com.yidumen.dao.entity.Account;
import com.yidumen.dao.entity.VerifyInfo;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger LOG;
    @Autowired
    private AccountDAO accountDAO;

    public UserServiceImpl() {
        this.LOG = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public Account find(Long id) {
        return accountDAO.find(id);
    }

    @Override
    public Account verify(final String username, final String password)
            throws AccountNotFoundException, FailedLoginException {
        Account result = accountDAO.find(username.toLowerCase());
        if (result == null || result.getUserGroup().equals(AccountGroup.USER)) {
            throw new AccountNotFoundException("没有这个用户");
        }
        if (result.getPassword().equals(password)) {
            return result;
        } else {
            throw new FailedLoginException("密码错误");
        }
    }

    @Override
    public Account register(String username, final String password, final String ip)
            throws AccountException, IllDataException {
        username = username.toLowerCase();
        Account user = accountDAO.find(username);
        if (user != null) {
            throw new AccountException("用户已存在");
        }
        user = new Account();
        if (checkPhone(username)) {
            user.setPhone(username);
        } else if (checkEmail(username)) {
            user.setEmail(username);
        } else {
            throw new IllDataException("用户名格式不对");
        }
        user.setPassword(password);
        user.setUserGroup(AccountGroup.USER);
        user.setCreatedate(new Date(System.currentTimeMillis()));
        final VerifyInfo verifyInfo = new VerifyInfo();
        user.setVerifyInfo(verifyInfo);
        user.setStatus(true);
        accountDAO.create(user);
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
    public void update(Account account) {
        accountDAO.edit(account);
    }

}
