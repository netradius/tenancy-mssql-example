package com.mastercontrol.data.repository;

import com.mastercontrol.data.model.User;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

/**
 * @author Kevin Hawkins
 */
public interface UserRepository extends PagingAndSortingRepository<User, UUID>,
		QueryDslPredicateExecutor<User> {}
