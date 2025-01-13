package ru.otus.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public class Phone {

    @Column("id")
    private Long id;

    @Column("number")
    private String number;

    @MappedCollection(idColumn = "client_id")
    private Client client;

    public Phone(Long id, String number) {
	this.id = id;
	this.number = number;
    }

    public Phone(Long id, String number, Client client) {
	this.id = id;
	this.number = number;
	this.client = client;
    }

    public void setClient(Client client) {
	this.client = client;
    }

    public Phone clone() {
	return new Phone(this.id, this.number, this.client);
    }
}
