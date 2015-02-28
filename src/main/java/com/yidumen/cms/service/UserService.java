package com.yidumen.cms.service;

import com.yidumen.cms.model.Account;
import com.yidumen.cms.service.exception.IllDataException;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;

/**
 *
 * @author 蔡迪旻
 */
public interface UserService {

    Account register(String username, final String password, final String ip) throws AccountException, IllDataException;

    void update(Account account);

    Account verify(final String username, final String password) throws AccountNotFoundException, FailedLoginException;

    Account find(Long id);
    
}
