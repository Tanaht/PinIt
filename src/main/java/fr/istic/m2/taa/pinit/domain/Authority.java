package fr.istic.m2.taa.pinit.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authority implements GrantedAuthority {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";


    @Id
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
