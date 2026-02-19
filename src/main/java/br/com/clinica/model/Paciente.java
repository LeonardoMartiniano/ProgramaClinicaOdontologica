package br.com.clinica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pacientes")

public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    private String telefone;

    @Column(name = "pagamento_em_dia")
    private boolean pagamentoEmDia;

    public Paciente() {
    }

    public Paciente(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.pagamentoEmDia = false; // começa inadimplente
    }

    // getters e setters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isPagamentoEmDia() {
        return pagamentoEmDia;
    }

    public void setPagamentoEmDia(boolean pagamentoEmDia) {
        this.pagamentoEmDia = pagamentoEmDia;
    }


}
