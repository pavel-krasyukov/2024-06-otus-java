/**
 * @author administrator on 30.12.2024.
 */
package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/list")
    List<Client> findAllClients() {
	System.out.println("findAllClients");
	return clientRepository.findAllClients();
    }

    @GetMapping()
    public ModelAndView showClientsPage() {
	log.info("showClientsPage");
	var modelAndView = new ModelAndView("clients");

	var clients = clientRepository.findAllClients();
	modelAndView.addObject("clients", clients);

	return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add() {
	var modelAndView = new ModelAndView("add-client");
	modelAndView.addObject("client", new Client());

	return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView save(@ModelAttribute Client client) {
	try {
	    clientRepository.save(client);
	    log.info("Client saved successfully with id: {}", client.getId());
	} catch (Exception e) {
	    log.error("Error saving client: ", e);
	}
	return new RedirectView("/clients");
    }
}
