package ru.otus.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@Table("client")
public class Client implements Cloneable {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @MappedCollection(idColumn = "address_id")
    private Address address;

    @MappedCollection(idColumn = "client_id")
    private List<Phone> phoneList = new ArrayList<>();

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phoneList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneList = List.copyOf(phoneList);
        this.phoneList.forEach(phone -> phone.setClient(this));
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phoneList);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
