package com.bridgelabz.greetingsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.greetingsapp.DTO.Greeting;

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {
}