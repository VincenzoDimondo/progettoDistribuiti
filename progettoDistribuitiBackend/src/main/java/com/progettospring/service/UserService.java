
package com.progettospring.service;

import com.progettospring.entity.User;
import com.progettospring.exceptions.EmailAlreadyExistException;
import com.progettospring.exceptions.IdAlreadyExistException;
import com.progettospring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OrderBy;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getUtenti() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void addUtente(User utente) throws Exception {
        if (userRepository.existsById(utente.getId())) {
            throw new IdAlreadyExistException();
        } else if (userRepository.existsByEmail(utente.getEmail())) {
            throw new EmailAlreadyExistException();
        }
        userRepository.saveAndFlush(utente);
    }

    @Transactional(readOnly = true)
    public boolean esisteUtenteEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @OrderBy(value = "cognome,nome,email")
    @Transactional(readOnly = true)
    public List<User> getUtenteByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Transactional(readOnly = false)
    public void updateUtente(User utenteNuovo) {
        userRepository.saveAndFlush(utenteNuovo);
    }

    @Transactional(readOnly = false)
    public void removeUtente(User utente) {
        userRepository.deleteById(utente.getId());
    }

    @Transactional
    public User getUtenteById(long id) {
        return userRepository.getById(id);
    }

}