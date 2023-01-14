package br.com.attornatus.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.attornatus.api.event.RecursoCriadoEvent;
import br.com.attornatus.api.model.Endereco;
import br.com.attornatus.api.repository.EnderecoRepository;
import br.com.attornatus.api.service.EnderecoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoResource {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private EnderecoService enderecoService;

	@GetMapping
	public List<Endereco> listar() {
		return enderecoRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Endereco> criar(@Valid @RequestBody Endereco endereco, HttpServletResponse response) {
		Endereco enderecoSalvo = enderecoRepository.save(endereco);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, enderecoSalvo.getId_endereco()));
		return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSalvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> buscarPeloCodigo(@PathVariable Long id) {
		Optional<Endereco> endereco = enderecoRepository.findById(id);
		return endereco.isPresent() ? ResponseEntity.ok(endereco.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		enderecoRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
		Endereco enderecoSalvo = enderecoService.atualizar(id, endereco);
		return ResponseEntity.ok(enderecoSalvo);
	}
	
	@PutMapping("/{id}/principal")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadePrincipal(@PathVariable Long id, @RequestBody Boolean principal) {
		enderecoService.atualizarPropriedadePrincipal(id, principal);
	}
}
