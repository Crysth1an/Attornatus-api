package br.com.attornatus.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatus.api.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {


}
