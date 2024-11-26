package model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private Double salario;
    private LocalDate dataAdmissao;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "funcionarios_beneficios",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "beneficio_id")
    )
    private List<Beneficio> beneficios;

    public Funcionario() {}

    public Funcionario( String nome, String cpf, LocalDate dataAdmissao, Cargo cargo, Departamento departamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataAdmissao = dataAdmissao;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = calcularSalario();
    }

    public Double calcularSalario() {
        double valorBeneficios = 0.0;

        if (beneficios != null) {
            valorBeneficios = beneficios.stream()
                    .mapToDouble(Beneficio::getValor)
                    .sum();
        }

        return cargo != null
                ? cargo.getSalarioBase() + valorBeneficios
                : 0.0;
    }


    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", salario=" + salario +
                ", dataAdmissao=" + dataAdmissao +
                ", cargo=" + cargo +
                ", departamento=" + departamento +
                '}';
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf( String cpf ) {
        this.cpf = cpf;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario( Double salario ) {
        this.salario = salario;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao( LocalDate dataAdmissao ) {
        this.dataAdmissao = dataAdmissao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo( Cargo cargo ) {
        this.cargo = cargo;
        this.salario = calcularSalario();
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento( Departamento departamento ) {
        this.departamento = departamento;
    }

    public List<Beneficio> getBeneficio() {
        return beneficios;
    }

    public void setBeneficio( List<Beneficio> beneficio ) {
        this.beneficios = beneficio;
        this.salario = calcularSalario();
    }

}
