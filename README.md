# 🐾 AmigoPet — Backend

API REST desenvolvida em Java com Spring Boot para um sistema de gerenciamento administrativo de um pet shop.

O AmigoPet tem como objetivo centralizar funcionalidades de gerenciamento, autenticação e controle de acesso, oferecendo diferentes permissões para usuários e administradores.

**API em produção:** https://amigopet-backend-uua0.onrender.com

📖 **Documentação Swagger:** https://amigopet-backend-uua0.onrender.com/swagger-ui/index.html

## Tecnologias utilizadas

- Java 26
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Spring OAuth2 Resource Server
- JWT
- BCrypt
- PostgreSQL
- Maven
- Docker

## Funcionalidades

- Cadastro e autenticação de usuários.
- Autenticação utilizando JWT com chaves RSA.
- Criptografia de senhas com BCrypt.
- Controle de acesso baseado em papéis (USER e ADMIN).
- Área administrativa com acesso restrito ao ADMIN.
- Gerenciamento de animais (cadastro, consulta, atualização e exclusão).
- Associação dos animais aos respectivos tutores.
- Restrição de acesso para garantir que cada tutor visualize e gerencie apenas seus próprios animais.
- Recuperação de senha por e-mail.
- Tratamento centralizado de exceções.
- Testes unitários dos serviços.
- Documentação dos endpoints com Swagger.

## Segurança

A aplicação utiliza Spring Security para proteger os endpoints e JWT para autenticação e autorização.

A autorização é baseada em papéis, diferenciando usuários comuns e administradores. Também são aplicadas regras de acesso para garantir que os tutores tenham acesso somente aos próprios registros.

## Arquitetura e infraestrutura

O backend foi desenvolvido como uma API REST independente do frontend.

- **Render:** hospedagem da API.
- **Neon:** banco de dados PostgreSQL.
- **Docker:** containerização da aplicação.
- **GitHub:** versionamento e integração com o deploy.

## Aplicação relacionada

**Frontend:** https://amigopet-frontend.vercel.app/login

**Repositório do frontend:** https://github.com/devgusta07/amigopet-frontend

## Observações

A API está publicada e pode ser explorada através da documentação Swagger.

A execução em outro ambiente exige a configuração das variáveis de ambiente e das credenciais necessárias para conexão com o banco de dados, autenticação JWT e serviço de e-mail.

---

Desenvolvido como projeto de portfólio, com foco em desenvolvimento backend Java, segurança, persistência de dados e integração entre aplicações.
