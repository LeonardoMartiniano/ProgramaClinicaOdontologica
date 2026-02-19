package br.com.clinica.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagamentos")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;

    private Double valor;

    private LocalDate data;

    private String descricao;


    @ManyToOne
    @JoinColumn(name= "paciente_id")
    private Paciente paciente;

    public Pagamento(){

    }
    public Pagamento(TipoPagamento tipo, Double valor, String descricao, Paciente paciente) {
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
        this.data = LocalDate.now();
        this.paciente = paciente;
    }

    public Long getId() { return id; }
    public TipoPagamento getTipo() { return tipo; }
    public Double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public String getDescricao() { return descricao; }
    public Paciente getPaciente() { return paciente; }


}

