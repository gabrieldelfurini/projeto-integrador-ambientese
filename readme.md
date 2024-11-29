### Projeto Integrador Ambiente-se
## Como Rodar
## Passos inciais
- Clone o repositório e acesse a pasta criada:
  ~~~sh
  git clone https://github.com/Lucas-Dreveck/ProjetoIntegrador-Ambientese.git
  cd ProjetoIntegrador-Ambientese
  ~~~
- Acesse o arquivo env.properties.example que vai estar no caminho src/main/resources/env.properties.example;
- Duplique ele e coloque o nome env.properties;
- Insira os dados como aconselhado no próprio arquivo;
- Verifique o application.properties no mesmo diretório para caso precise alterar algo;
- Agora só seguir para inicialização.
## 1. Via docker
- Rode o docker-compose (versão embutida):
  ~~~sh
  docker compose up --build
  ~~~
  - ou (versão standalone):
  ~~~sh
  docker-compose up --build
  ~~~
- Tudo pronto, as credencias base de acesso estarão no terminal e agora é só abrir o seu localhost:8080
## 2. Pelo spring-boot
- Tenha certeza de que possui o maven na máquina: [Download](https://maven.apache.org/download.cgi)
- Recomendado o uso do Java 17 para tal: [Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Após tudo pronto, executar na pasta raiz o seguinte comando:
  ~~~sh
  mvn spring-boot:run
  ~~~
- Tudo pronto, as credencias base de acesso estarão no terminal e agora é só abrir o seu localhost:8080
  

## Changelog
Informações sobre as versões e mudanças do projeto.

## Versão 2.0.0 - 2024-06-23
### Adicionado
- Autenticação por token JWT com validação por front e back end;
- Hashs de todas as senhas da aplicação;
- Rotas protegidas utilizando dos cargos e propriedades dos usuarios;
- Criação de tela de cadastro de perguntas completo;
- Filtro e paginação para as telas de cadastro de empresas, funcionarios e perguntas;
- Paginação de tela de avaliação permitindo salvamento incompleto da mesma com verificações para retornar progresso ou abandonar;
- Sistema de geração de PDF com todos os dados detalhados das avaliações;
- Update de interface em todas as telas;
- Melhoria de feedback de erro em todos os tipos de formulários;
- Configurações docker para melhoria de testes e portabilidade.

## Versão 1.0.0 - 2024-05-24
### Adicionado
- Sistema de login com salvamento do usuario ativo;
- Tela de ranking 100% funcional, podendo filtrar todas as empresas doranking;
- Tela de avaliação completa das empresas com tela para selecionar a empresa destinada, avalia-la com perguntas salvas randomizadas e após isso visualizar o resultado da empresa;
- Tela de cadastro de empresa (CRUD) funcional;
- Tela de cadastro de funcionários (CRUD) funcional;