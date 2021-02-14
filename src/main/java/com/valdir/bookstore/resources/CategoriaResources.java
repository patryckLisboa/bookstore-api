package com.valdir.bookstore.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.valdir.bookstore.domain.Categoria;
import com.valdir.bookstore.dtos.CategoriaDTO;
import com.valdir.bookstore.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResources {
	@Autowired
	private CategoriaService categoriaSerice;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
		Categoria obj = categoriaSerice.findById(id);

		return ResponseEntity.ok().body(obj);
	}

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = categoriaSerice.findAll();

		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping	
	public ResponseEntity<Categoria> create(@RequestBody Categoria obj) {
		obj = categoriaSerice.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoriaDTO> update(@PathVariable Integer id, @RequestBody CategoriaDTO objDTO){
		Categoria newObj = categoriaSerice.update(id, objDTO);
		
		return ResponseEntity.ok(new CategoriaDTO(newObj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		categoriaSerice.delete(id);
		return ResponseEntity.noContent().build();
	}

}
