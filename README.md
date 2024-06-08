# Monitoramento de cultivo de hortaliças
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/LuscaKF/discography-manager-API/blob/main/LICENSE)

# Sobre o projeto

O projeto UserVegetableScreen é uma aplicação GUI (Graphical User Interface) desenvolvida em Java utilizando a biblioteca Swing. A aplicação tem como objetivo gerenciar um banco de dados de hortaliças, permitindo aos usuários realizar operações CRUD (Create, Read, Update, Delete) sobre os registros de hortaliças.

# Principais Funcionalidades

## Visualizar Hortaliças:
A aplicação permite visualizar uma lista de hortaliças armazenadas no banco de dados.
Os dados são exibidos em uma tabela, onde cada linha representa uma hortaliça e cada coluna representa um atributo da hortaliça (ID, Nome, Espécie, Data de Plantio, Observações).

## Adicionar Hortaliça:
O usuário pode adicionar uma nova hortaliça ao banco de dados.
Um formulário é exibido para que o usuário insira os detalhes da nova hortaliça (Nome, Espécie, Data de Plantio, Observações).
Após o preenchimento, os dados são inseridos no banco de dados e a tabela é atualizada para refletir a nova entrada.

## Editar Hortaliça:
O usuário pode editar os detalhes de uma hortaliça existente.
Ao selecionar uma hortaliça e clicar no botão "Editar", um formulário é exibido com os dados atuais da hortaliça, permitindo ao usuário modificar esses dados.
As mudanças são salvas no banco de dados e a tabela é atualizada.

## Remover Hortaliça:
O usuário pode remover uma hortaliça do banco de dados.
Ao selecionar uma hortaliça e clicar no botão "Remover", o registro correspondente é excluído do banco de dados e a tabela é atualizada.

## Logout:
O usuário pode fazer logout da aplicação.
Ao clicar no botão de logout, a tela atual é fechada e a tela de login é exibida, permitindo que outro usuário faça login.
Componentes do Projeto
Tela de Login (LoginScreen):

Permite ao usuário fazer login na aplicação.
Após um login bem-sucedido, a tela principal (UserVegetableScreen) é exibida.
Tela Principal (UserVegetableScreen):

Contém a tabela de hortaliças e botões para as operações CRUD.
Inclui um botão de logout para que o usuário possa sair da aplicação.
Tela de Edição (EditVegetableScreen):

Permite ao usuário editar os detalhes de uma hortaliça selecionada.
Exibe um formulário pré-preenchido com os dados atuais da hortaliça.
Conexão com o Banco de Dados (DatabaseConnection):

Gerencia a conexão com o banco de dados.
Fornece métodos para conectar e desconectar do banco de dados.
Estrutura do Código
Aqui está uma visão geral da estrutura do código:

UserVegetableScreen: Classe principal que exibe a tabela de hortaliças e os botões de controle.

Métodos principais:
viewVegetables(): Carrega e exibe as hortaliças do banco de dados.
addVegetable(): Adiciona uma nova hortaliça ao banco de dados.
editVegetable(): Edita uma hortaliça existente.
deleteVegetable(): Remove uma hortaliça do banco de dados.
logout(): Realiza o logout do usuário.
LoginScreen: Classe que representa a tela de login.

Permite ao usuário inserir credenciais e fazer login.
EditVegetableScreen: Classe que permite a edição de uma hortaliça.

Exibe um formulário pré-preenchido com os dados da hortaliça selecionada.
DatabaseConnection: Classe utilitária para gerenciar a conexão com o banco de dados.

Fornece métodos para obter uma conexão com o banco de dados.
Tecnologias Utilizadas
Java: Linguagem de programação utilizada para desenvolver a aplicação.
Swing: Biblioteca de interface gráfica do usuário (GUI) para Java.
JDBC: API para conexão e execução de operações em banco de dados.
Conclusão
O projeto UserVegetableScreen é uma aplicação completa para o gerenciamento de hortaliças, oferecendo uma interface amigável para realizar operações CRUD em um banco de dados. A aplicação é modular, com separação clara de responsabilidades entre as diferentes telas e componentes, e utiliza boas práticas de desenvolvimento, como a reutilização de código e a separação de lógica de negócio e apresentação.

# Como executar o projeto

Para executar o projeto UserVegetableScreen, você precisará seguir os seguintes passos:

Instalar as ferramentas necessárias.
Configurar o banco de dados.
Configurar o projeto Java.
Compilar e executar o projeto.

## Passo 1: Instalar as Ferramentas Necessárias

Java Development Kit (JDK):

Baixe e instale a versão mais recente do JDK (Java Development Kit) da Oracle ou OpenJDK.
Certifique-se de que o JDK esteja configurado no PATH do sistema.
IDE (Integrated Development Environment):

Baixe e instale uma IDE como IntelliJ IDEA, Eclipse ou NetBeans.
Essas IDEs oferecem ferramentas úteis para desenvolver, compilar e depurar projetos Java.
Banco de Dados:

Instale um SGBD (Sistema de Gerenciamento de Banco de Dados). Para este projeto, vamos utilizar o MySQL.
Baixe e instale o MySQL Community Server.
Instale também uma ferramenta para gerenciar o banco de dados, como MySQL Workbench.

## Passo 2: Configurar o Banco de Dados
Criar o Banco de Dados:

Abra o MySQL Workbench ou qualquer outra ferramenta de sua escolha.
Crie um novo banco de dados para o projeto com o seguinte comando SQL:
sql
Copiar código
CREATE DATABASE monitoramento_hortalicas;

## Criar a Tabela:
No banco de dados recém-criado, crie a tabela vegetables com o seguinte comando SQL:
sql
Copiar código
USE hortaliças;

CREATE TABLE hortalicas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    especie VARCHAR(255) NOT NULL,
    data_plantio DATE NOT NULL,
    observacoes TEXT
);

## Inserir Dados de Teste (Opcional):
Insira alguns dados de teste na tabela vegetables para verificar se a configuração está correta:
sql
Copiar código
INSERT INTO vegetables (nome, especie, data_plantio, observacoes)
VALUES
('Alface', 'Lactuca sativa', '2024-06-01', 'Plantar em local com bastante luz solar.'),
('Tomate', 'Solanum lycopersicum', '2024-05-15', 'Evitar encharcamento do solo.');

## Passo 3: Configurar o Projeto Java
Clonar o Repositório:

Se o projeto estiver em um repositório Git, clone-o para sua máquina local:
sh
Copiar código
git clone https://github.com/seu-usuario/seu-repositorio.git
Caso contrário, crie um novo projeto Java na sua IDE e adicione os arquivos fornecidos.
Adicionar Dependências JDBC:

Baixe o driver JDBC do MySQL do site oficial (https://dev.mysql.com/downloads/connector/j/).
Adicione o arquivo JAR do driver JDBC ao projeto na sua IDE.
Configurar Conexão com o Banco de Dados:

No arquivo DatabaseConnection.java, configure as credenciais de conexão com o banco de dados:
java
Copiar código
private static final String URL = "jdbc:mysql://localhost:3306/monitoramento_hortalicas";
private static final String USER = "seu-usuario";
private static final String PASSWORD = "sua-senha";

## Passo 4: Compilar e Executar o Projeto
Compilar o Projeto:

Na sua IDE, compile o projeto para verificar se há erros de compilação.
Executar a Aplicação:

Na sua IDE, execute a classe principal que inicia a aplicação. Supondo que UserVegetableScreen seja a classe principal, clique com o botão direito do mouse na classe e selecione "Run" (Executar).
Testar a Aplicação:

A aplicação deve iniciar e exibir a tela de login.
Faça login com as credenciais corretas e teste as funcionalidades (adicionar, editar, remover hortaliças e logout).
Dicas Adicionais

## Verificação de Erros:
Se encontrar problemas de conexão, verifique se o MySQL está em execução e se as credenciais de conexão estão corretas.
Verifique os logs de erro da aplicação para entender possíveis problemas.
Segurança:

Não deixe credenciais de banco de dados em texto claro no código-fonte. Use variáveis de ambiente ou arquivos de configuração seguros para armazenar essas informações.

## Dependências Externas:
Se utilizar bibliotecas externas, considere usar um gerenciador de dependências como Maven ou Gradle para facilitar o gerenciamento e a configuração dessas dependências.
Seguindo esses passos, você deve conseguir configurar e executar o projeto UserVegetableScreen corretamente. Boa sorte!

# Exemplo gráfico do projeto:

## Página de login (Seja com o usuário Administrador ou de função User para monitoramento de hortaliças):
![LoginScreen](https://github.com/LuscaKF/cultivoHort/assets/62342102/c18904a5-db86-4d04-966c-538966e1a7f7)

## Página de visualização e controle de usuários:
![AdminScreen](https://github.com/LuscaKF/cultivoHort/assets/62342102/e3ae66ca-e610-4836-9343-e97827e4dcd5)

## Página de edição de usuário (Somente para o usuário função Administrador):
![Editar Usuário](https://github.com/LuscaKF/cultivoHort/assets/62342102/f67dd571-e198-4ae2-a2b9-c95e54d0f89e)

## Página de registro de usuário (Somente para o usuário função Administrador):
![Registrar um usuário](https://github.com/LuscaKF/cultivoHort/assets/62342102/c3e7b51e-87dc-4d62-88e8-d094dc9031d8)

## Caso seja feito o login com o Usuário de monitoramento de hortaliças:
![Adicionar Hortaliças](https://github.com/LuscaKF/cultivoHort/assets/62342102/e6ac4cb6-f175-44ef-8faa-dcdde3cc2b4c)

## Visualizar hortaliças:
![Vizualisar Plantio](https://github.com/LuscaKF/cultivoHort/assets/62342102/1babd6c0-b337-4ee2-928d-f5797aa9f377)


