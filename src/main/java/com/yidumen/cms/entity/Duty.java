package com.yidumen.cms.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年12月15日
 */
@Entity
@Table(name = "cms_account_duty")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 24)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "related_duty_permission",
            joinColumns = @JoinColumn(name = "duty_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permissions;
    @OneToMany(mappedBy = "duty")
    private List<Account> accounts;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addPermission(Permission permission) {
        if (this.permissions == null) {
            this.permissions = new ArrayList<>();
        }
        this.permissions.add(permission);
    }

    public void addAccount(Account account) {
        if (this.accounts == null) {
            this.accounts = new ArrayList<>();
        }
        this.accounts.add(account);
    }


}
