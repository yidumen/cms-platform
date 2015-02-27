package com.yidumen.cms.model;

/**
 *
 * @author 蔡迪旻
 */
public final class Account extends BaseModel<Account> {

    public static final Account dao = new Account();

    public Account() {
        super("Account");
    }

    public Account findByName(final String name) {
        return findFirst("select * from Account where email = ? or phone = ?", name, name);
    }
}
