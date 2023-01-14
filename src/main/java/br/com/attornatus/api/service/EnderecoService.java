package br.com.attornatus.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.attornatus.api.model.Endereco;
import br.com.attornatus.api.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Endereco atualizar(Long id, Endereco endereco) {
		Endereco enderecoSalvo = buscarEnderecoPeloCodigo(id);
		BeanUtils.copyProperties(endereco, enderecoSalvo, "id");
		return this.enderecoRepository.save(enderecoSalvo);
	}

	public Endereco buscarEnderecoPeloCodigo(Long id) {
		Endereco enderecoSalvo = this.enderecoRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return enderecoSalvo;
	}
	
	public void atualizarPropriedadePrincipal(Long codigo, Boolean principal) {
		Endereco enderecoSalvo = buscarEnderecoPeloCodigo(codigo);
		enderecoSalvo.setPrincipal(principal);
		this.enderecoRepository.save(enderecoSalvo);
	}
}
