package com.valdir.bookstore.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.valdir.bookstore.domain.Livro;
import com.valdir.bookstore.dtos.LivroDTO;
import com.valdir.bookstore.service.LivroService;

@RestController
@RequestMapping(value = "/livros")
public class LisvroResources {
	
	@Autowired
	private LivroService livroService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable Integer id){
		Livro obj = livroService.findById(id);
		
		return ResponseEntity.ok().body(obj);
		//http://localhost:8080/livros/{codigo do livro a ser encontrado}
	
	}
	
	@GetMapping
	public ResponseEntity<List<LivroDTO>> findAll(@RequestParam(value = "categoria", defaultValue = "0") Integer id_cat){
		List<Livro> list = livroService.findAll(id_cat);
		
		List<LivroDTO> listDTO = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
		
		//localhost:8080/livros?categorias=1
	}
	
	@PutMapping(value = "/{id}") // atualizar informação total
	public ResponseEntity<Livro> update(@PathVariable Integer id, @RequestBody Livro obj){ //variavel e corpo da requisção, resposta
		Livro newObj = livroService.update(id, obj);
		
		return ResponseEntity.ok().body(newObj);
		
		//http://localhost:8080/livros/id e passando um livro modificado
	}
	
	@PatchMapping(value = "/{id}") // atualizar informação parcial
	public ResponseEntity<Livro> updatePath(@PathVariable Integer id, @RequestBody Livro obj){ //variavel e corpo da requisção, resposta
		Livro newObj = livroService.update(id, obj);
		
		
		return ResponseEntity.ok().body(newObj);
		//http://localhost:8080/livros/id e passando um livro modificado
	}
	
	@PostMapping
	public ResponseEntity<Livro> create(@RequestParam(value = "categoria" , defaultValue = "0") Integer id_cat,
			@RequestBody Livro obj){ //defaul - se não passar valor, o valor é zero
		
		Livro newObj = livroService.create(id_cat, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/livros/{id}").buildAndExpand(newObj.getId()).toUri();//uri é o que vai editar do cabecalho da requisição
		return ResponseEntity.created(uri).build(); // vai passar a url onde se encontra o novo livro salvo
		//http://localhost:8080/livros?categoria=3  e passando um livro a ser incluido
	}
}
