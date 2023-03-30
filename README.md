Este é um código Java que implementa um formulário de cadastro de locação de quadras esportivas. O formulário é construído usando a biblioteca Swing, que fornece classes e componentes para criar interfaces gráficas de usuário em Java.

A classe principal do programa é chamada cadastro, que define os componentes do formulário, incluindo vários campos de texto para entrada de dados e botões para salvar, alterar, excluir e pesquisar registros. Também há uma tabela que exibe os registros cadastrados.

A conexão com o banco de dados é feita por meio do JDBC, que é um conjunto de classes e interfaces Java que permite a interação com bancos de dados relacionais. A classe cadastro possui um método conexao() que cria a conexão com o banco de dados e outro método table_load() que carrega os dados da tabela para a tabela exibida no formulário.

Quando o usuário clica no botão "Salvar", os dados inseridos no formulário são validados e, se estiverem corretos, são salvos no banco de dados. Além disso, um pop-up é exibido com as informações cadastradas. Se o usuário tiver menos de 18 anos, uma mensagem de erro é exibida.

Em geral, o código parece estar bem organizado e bem escrito. No entanto, há um trecho de código comentado que parece estar inacabado, que é responsável por aplicar um desconto de 10% para locações com mais de 2 horas (120 minutos). Se necessário, esse trecho de código pode ser retomado e finalizado.

![Sem título](https://user-images.githubusercontent.com/88213553/228251758-e4d27e50-2f49-4d7e-a788-daaf67835dfa.jpg)

