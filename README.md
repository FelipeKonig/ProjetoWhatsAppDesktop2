# ProjetoWhatsAppDesktop2

Projeto desenvolvido em Java utilizando a biblioteca JavaFX para criação das interfaces; banco de dados SQLite para armazenamento dos dados, juntamente com ORM Hibernate para o mapeamento objeto-relacional escrito em linguagem Java; realizando interação entre cliente e servidor através de socket e uso de thread para primeiro plano enquanto o cliente espera resposta do servidor.

O projeto consiste em manter na parte do cliente apenas as interfaces e a lógica de funcionamento das mesmas, buscando, recebendo e manipulando nas interfaces as informações recebidas pelo servidor. E por parte do servidor responsável por receber as informações passadas pelo cliente, manipula-lás através da regra de negócio do projeto e realizar consultas no banco. 

Algumas funcionalidades do projeto:

-Criação de conta;
-Adicionar contatos;
-Envio de status;
-Visualização do status de contatos;
-Ligação por chamada(apenas simulação, sem de fato, realizar a ligação ao contato);
-Edição de contatos, conta e afins.
