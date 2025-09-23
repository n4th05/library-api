package io.github.n4th05.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.n4th05.libraryapi.model.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {

   Client findByClientId(String clientId);
}
