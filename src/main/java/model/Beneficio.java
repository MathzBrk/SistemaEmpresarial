package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Beneficio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double valor;

    @ManyToMany(mappedBy = "beneficio", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Beneficio (){}

    @Override
    public String toString() {
        return "Beneficio{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                '}';
    }

    public Beneficio(String nome, String descricao, Double valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor( Double valor ) {
        this.valor = valor;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios( List<Funcionario> funcionarios ) {
        this.funcionarios = funcionarios;
    }
}
