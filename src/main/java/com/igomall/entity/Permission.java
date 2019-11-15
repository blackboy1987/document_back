package com.igomall.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Permission extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Menu menu;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
