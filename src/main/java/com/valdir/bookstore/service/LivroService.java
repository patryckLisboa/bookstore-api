package com.valdir.bookstore.service;

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
	
	public Livro findById(Integer id) {
		Optional<Livro> obj = livroRepository.findById(id); //optional é pq ele pode receber nulo
	
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado, id:" + id + ", tipo:"+ Livro.class.getName()));
	}
	
}
