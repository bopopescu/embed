package main.java.core.vo;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Objects;

/**
 * Created by digvijaysharma on 08/01/17.
 */

public class JawtUserVO implements Principal {

    private final BigDecimal id;
    private final String name;

    public JawtUserVO(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
    }

    public BigDecimal getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final main.java.core.vo.JawtUserVO myUser = (main.java.core.vo.JawtUserVO) o;
        return Objects.equals(id, myUser.id) && Objects.equals(name, myUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
