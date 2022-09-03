package com.progettospring.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "sentiment_analysis")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "email", unique = true)
    private String email;

    @Basic
    @Column(name = "nome")
    private String nome;

    @Basic
    @Column(name = "cognome")
    private String cognome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "UtentePizzeria{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User utente = (User) o;
        return id == utente.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public User() {
        super();
    }

    public User(Long id, String nome, String cognome, String email, String password) {
        super();
        this.setId(id);
        this.setNome(nome);
        this.setCognome(cognome);
        this.setEmail(email);
        this.setPassword(password);
    }

}