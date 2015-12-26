package com.yidumen.cms.service;

import com.yidumen.cms.entity.Account;
import com.yidumen.cms.repository.HibernateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 蔡迪旻
 *         2015年12月14日
 */
@Service
public class AuthenticationService implements UserDetailsService {
    private final static Logger LOG = LoggerFactory.getLogger("用户验证服务");
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$");
    private final static Pattern PHONE_PATTERN = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])\\d{8}$");
    @Qualifier("accountRepository")
    @Autowired
    private HibernateRepository<Account> accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserDetails result;
        final Account model = new Account();
        model.setStatus(true);
        if (EMAIL_PATTERN.matcher(username).matches()) {
            model.setEmail(username);
        } else if (PHONE_PATTERN.matcher(username).matches()) {
            model.setPhone(username);
        } else {
            model.setName(username);
        }
        final Account account = accountRepository.findUnique(model);
        if (account != null) {
            result = getUserDetails(account);
        } else {
            LOG.info("尝试使用 {} 登录失败,原因是:没有这个用户", username);
            throw new UsernameNotFoundException("用户名 " + username + " 未使用");
        }
        LOG.info("尝试使用用户 {} 登录,密码应为 {}", result.getUsername(), result.getPassword());
        return result;
    }

    private UserDetails getUserDetails(Account account) {
        UserDetails result;
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        account.getPermissions().forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getRole())));
        account.getDuty().getPermissions().forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getRole())));
        result = new User(account.getName(), account.getPassword(), grantedAuthorities);
        return result;
    }
}
