package br.com.clinica.model;


import jakarta.persistence.*;

@Entity
@Table(name = "dentistas")
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cro;

    private String telefone;

    public Dentista() {
    }

    public Dentista(String nome, String cro, String telefone) {
        this.nome = nome;
        this.cro = cro;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCro() {
        return cro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
