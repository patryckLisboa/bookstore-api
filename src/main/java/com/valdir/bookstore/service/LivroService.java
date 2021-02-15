package com.valdir.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.bookstore.domain.Livro;
import com.valdir.bookstore.repositories.LivroRepository;
import com.valdir.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class LivroService {
	
	@Autowired //o spring que vai gerenciar esse instancia/ hora que vai gerar e destrouir
	private LivroRepository livroRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	
	public Livro findById(Integer id) {
		Optional<Livro> obj = livroRepository.findById(id); //optional é pq ele pode receber nulo
	
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado, id:" + id + ", tipo:"+ Livro.class.getName()));
	}

	public List<Livro> findAll(Integer id_cat) {
		categoriaService.findById(id_cat);
		
		
		return livroRepository.findAllByCategoria(id_cat);
	}

	public Livro update(Integer id, Livro obj) {
		Livro newObj = findById(id);
		updateData(newObj, obj);
		
		return livroRepository.save(newObj);
	}

	private void updateData(Livro newObj, Livro obj) {
		newObj.setTitulo(obj.getTitulo());
		newObj.setNome_autor(obj.getNome_autor());
		newObj.setTexto(obj.getTexto());
		
	}
	
}
