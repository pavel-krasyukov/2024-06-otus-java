/**
 * @author administrator on 30.12.2024.
 */
package ru.otus.service;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/")
    public String home() {
	return "clients"; // Возврат к clients.html (должен быть в src/main/resources/templates)
    }
}
