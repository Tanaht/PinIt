package fr.istic.m2.taa.pinit.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Authority implements GrantedAuthority {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String ANONYMOUS = "ANONYMOUS";

    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    private String authority;

    public Authority(){

    }

    public Authority(String name) {
        this.authority = name;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "name='" + authority + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return authority != null ? authority.equals(authority.authority) : authority.authority == null;
    }

    @Override
    public int hashCode() {
        return authority != null ? authority.hashCode() : 0;
    }


}
