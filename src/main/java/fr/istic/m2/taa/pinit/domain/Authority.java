package fr.istic.m2.taa.pinit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Authority {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String ANONYMOUS = "ANONYMOUS";

    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    private String name;

    public Authority(){

    }

    public Authority(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return name != null ? name.equals(authority.name) : authority.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
