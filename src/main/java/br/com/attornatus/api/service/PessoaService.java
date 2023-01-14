package br.com.attornatus.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.attornatus.api.model.Pessoa;
import br.com.attornatus.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(id);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return this.pessoaRepository.save(pessoaSalva);
	}

	public Pessoa buscarPessoaPeloCodigo(Long id) {
		Pessoa pessoaSalva = this.pessoaRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaSalva;
	}
}
