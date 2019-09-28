package com.adcash.challenge.service.dto;


import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class CategoryDTO implements Serializable {


    private String name;

    @Size(min = 3, message = "identifier min length is 3")
    private String identifier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, identifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(identifier, that.identifier);
    }
}
