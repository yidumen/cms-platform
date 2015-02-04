package com.yidumen.cms.dao;

/**
 *
 * @author 蔡迪旻
 */
public class Account extends BaseModel<Account> {

    public static final Account dao = new Account();

    public Account() {
        super("Account");
    }

    public Account findByName(final String name) {
        return findFirst("select * from Account where email = ? or phone = ?", name, name);
    }
}
