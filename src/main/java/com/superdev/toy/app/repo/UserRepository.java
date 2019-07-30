package com.superdev.toy.app.repo;

import com.superdev.toy.app.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
