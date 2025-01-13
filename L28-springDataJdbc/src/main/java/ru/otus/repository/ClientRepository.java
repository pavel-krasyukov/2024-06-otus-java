package ru.otus.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.model.Client;

import java.util.List;

public interface ClientRepository extends ListCrudRepository<Client, Long> {

    @Modifying

    @Query(value="insert into client (name) values (:id, :name)")
    void saveClient(@Param("name") String name);

    @Query(value="select * from client", resultSetExtractorClass = ClientResultSetExtractor.class)
    List<Client> findAllClients();
}
