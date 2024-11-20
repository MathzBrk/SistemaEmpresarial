package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cargos")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double salarioBase;

    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionarios;

    public Cargo() {}

    public Cargo( String descricao, Double salarioBase ) {
        this.descricao = descricao;
        this.salarioBase = salarioBase;
        this.funcionarios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }

    public Double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase( Double salarioBase ) {
        this.salarioBase = salarioBase;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios( List<Funcionario> funcionarios ) {
        this.funcionarios = funcionarios;
    }
}
