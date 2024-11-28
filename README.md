
# Projeto ORM - Sistema de Funcionários

Este é um projeto acadêmico desenvolvido por Matheus Borges e Matheus Rezende, cujo objetivo foi aplicar o conceito de **ORM (Mapeamento Objeto Relacional)** em um sistema fictício de gestão de funcionários.

---

## 📋 Descrição do Projeto

O sistema foi projetado para gerenciar funcionários em uma empresa fictícia, considerando:
- Cada funcionário está associado a um **cargo** e a um **departamento**.
- O cálculo do salário é realizado com base no **salário base do cargo** somado aos **benefícios atribuídos ao funcionário**.
- O funcionário pode ter 0 ou muitos benefícios

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java
- **Banco de Dados**:MySql, Hibernate (JPA - ORM)
- **Ferramentas**: IntelliJ IDEA, Maven
- **Versionamento**: Git

---

## 🗂️ Estrutura do Projeto

### Principais Classes
1. **Funcionário**: Representa o funcionário, incluindo atributos como nome, cargo, departamento e salário.
2. **funcionarioViewHandler**: Responsável por capturar as informações do usuário.
3. **funcionarioService**: Realiza as operações principais no banco de dados utilizando o EntityManager.
4. Outras classes seguem o mesmo modelo, organizadas para manter a coesão e a responsabilidade única.

### Diagramas e Modelos
- **Modelo Conceitual e Lógico**: Representa a modelagem do banco de dados e as relações entre as entidades.
- **Diagrama de Classes**: Mostra as relações entre as classes do sistema.

---

## 💡 Funcionalidades do Sistema

1. Cadastro de funcionários com associação a cargo e departamento.
2. Cálculo do salário com base em salário base e benefícios.
3. Gestão de informações utilizando conceitos de ORM.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK 17 ou superior instalado.
- Banco de dados configurado (MySQL).
- Maven configurado para gerenciamento de dependências.

### Passo a Passo para rodar o projeto:
- Crie um schema no MySql com o nome de pousotech
- Caso crie com outro nome, configure o arquivo persistence.xml dentro da pasta META-INF

---


## 🤝 Contribuições

Feedbacks e sugestões são bem-vindos!

---

## 🧑‍💻 Autores

- [Matheus Borges](https://www.linkedin.com/in/matheus-borges-37ab512b5/)
- [Matheus Rezende](https://www.linkedin.com/in/matheusrezend/)

---
