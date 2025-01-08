package ru.otus.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public class Address implements Cloneable {

    @Column("id")
    private Long id;

    @Column("street")
    private String street;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address(String street) {
        this.id = null;
        this.street = street;
    }

    @Override
    public Address clone(){
        return new Address(this.id, this.street);
    };
}
