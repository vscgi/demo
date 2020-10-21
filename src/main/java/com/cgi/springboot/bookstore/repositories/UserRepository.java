package com.cgi.springboot.bookstore.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.cgi.springboot.bookstore.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	ArrayList<User> findDistinctUsersByNameLikeOrEmailLikeAllIgnoreCaseOrderByIdAsc(String name, String email);
}
