package com.bridgelabz.greetingsapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgelabz.greetingsapp.DTO.Greeting;
import com.bridgelabz.greetingsapp.repository.GreetingRepository;

import java.util.List;

@Service
@RequestMapping("/api/greetings")
public class GreetingService {
	
	private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }
	
	public String getHelloGreeting() {
        return "Hello World";
    }

    public String getGreeting(String firstName, String lastName) {
        if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            return "Hello " + firstName + " " + lastName;
        } else if (firstName != null && !firstName.isEmpty()) {
            return "Hello " + firstName;
        } else if (lastName != null && !lastName.isEmpty()) {
            return "Hello " + lastName;
        } else {
            return getHelloGreeting();
        }
    }
    
    // UC4: Save greeting to repository
    public Greeting saveGreeting(String message) {
        Greeting greeting = new Greeting(message);
        return greetingRepository.save(greeting);
    }

    // UC4: Get greeting by ID
    public Greeting getGreetingById(Long id) {
        return greetingRepository.findById(id).orElse(null);
    }

    // UC4: Get all greetings
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }
    
}
