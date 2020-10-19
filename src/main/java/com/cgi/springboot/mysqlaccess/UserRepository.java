package com.cgi.springboot.mysqlaccess;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	ArrayList<User> findDistinctUsersByNameLikeOrEmailLikeAllIgnoreCaseOrderByIdAsc(String name, String email);
}
