package com.progettospring.controller;

import com.progettospring.entity.User;
import com.progettospring.exceptions.EmailAlreadyExistException;
import com.progettospring.exceptions.IdAlreadyExistException;
import com.progettospring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/utente")
@Tag(name = "utente", description = "API auth utente")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid User utente) {
        try {
            userService.addUtente(utente);
            return new ResponseEntity("200", HttpStatus.OK);
        } catch (IdAlreadyExistException e) {
            return new ResponseEntity("Id already exist!", HttpStatus.BAD_REQUEST);
        } catch (EmailAlreadyExistException e) {
            return new ResponseEntity("Email already exist!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Errore interno al Server!", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() throws EntityNotFoundException {
        return ResponseEntity.ok(userService.getUtenti());
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<String> cercaEmail(@RequestParam String email) {
        if (userService.esisteUtenteEmail(email)) {
            return new ResponseEntity("200", HttpStatus.OK);
        } else {
            return new ResponseEntity("Email non trovata!", HttpStatus.OK);
        }
    }

    @GetMapping("getByEmail")
    public ResponseEntity<User> getByEmail(@RequestParam String email) {
        if (!userService.esisteUtenteEmail(email)) {
            return new ResponseEntity("Email non esistente!", HttpStatus.BAD_REQUEST);
        } else {
            User u = userService.getUtenteByEmail(email).get(0);
            return new ResponseEntity(u, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        if (!userService.esisteUtenteEmail(email)) {
            return new ResponseEntity("Email non esistente!", HttpStatus.BAD_REQUEST);
        } else {
            User u = userService.getUtenteByEmail(email).get(0);
            if (!u.getEmail().equals(email) || !u.getPassword().equals(password)) {
                return new ResponseEntity("Password errata!", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity("200", HttpStatus.OK);
            }
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody @Valid User utente) {
        if (!userService.esisteUtenteEmail(utente.getEmail())) {
            return new ResponseEntity("Email non esistente!", HttpStatus.BAD_REQUEST);
        } else {
            userService.updateUtente(utente);
            return new ResponseEntity("200", HttpStatus.OK);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity removeUtente(@RequestBody @Valid User utente) {
        // devo rimuovere dopo 30gg il carrello
        userService.removeUtente(utente);
        return ResponseEntity.ok("200");
    }


}