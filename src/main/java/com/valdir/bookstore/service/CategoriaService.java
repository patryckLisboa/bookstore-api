package com.valdir.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.valdir.bookstore.domain.Categoria;
import com.valdir.bookstore.dtos.CategoriaDTO;
import com.valdir.bookstore.repositories.CategoriaRepository;
import com.valdir.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria findById(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", tipo:" + Categoria.class.getName()));

	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Categoria create(Categoria obj) {
		obj.setId(null);

		return categoriaRepository.save(obj);
	}

	public Categoria update(Integer id, CategoriaDTO objDTO) {
		Categoria obj = findById(id);

		obj.setNome(objDTO.getNome());
		obj.setDescricao(objDTO.getDescricao());
		return categoriaRepository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);

		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) { // do spring
			throw new com.valdir.bookstore.service.DataIntegrityViolationException(
					"Categoria não pode ser deletado. Pois possui livros associados");
		}

	}
}
