
package com.progettospring.repository;

import com.progettospring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    boolean existsByEmail(String email);

    List<User> getByEmail(String email);

    User getById(long id);


}