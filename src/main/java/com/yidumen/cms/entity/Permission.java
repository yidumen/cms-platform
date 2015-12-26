package com.yidumen.cms.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author 蔡迪旻
 *         2015年12月15日
 */
@Entity
@Table(name = "cms_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description", length = 32)
    private String description;
    @Column(name = "role", length = 32)
    private String role;
    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Permission() {
    }

    public Permission(String role, String description) {
        this.description = description;
        this.role = role;
    }

    public Permission(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
