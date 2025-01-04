/**
 * @author administrator on 04.01.2025.
 */
package ru.otus.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.NonNull;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientResultSetExtractor implements ResultSetExtractor<List<Client>> {

    @Override
    public List<Client> extractData(@NonNull
    ResultSet rs) throws DataAccessException, SQLException {
	var clients = new ArrayList<Client>();

	while (rs.next()) {
	    var clientId = rs.getLong("id");
	    var clientName = rs.getString("name");


	    var client = clients.stream()
			    .filter(c -> c.getId().equals(clientId))
			    .findFirst()
			    .orElseGet(() -> {
				var newClient = new Client(clientId, clientName);
				clients.add(newClient);
				return newClient;
			    });
	}

	return clients;
    }
}
