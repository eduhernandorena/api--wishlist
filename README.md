# Wishlist
Para implementar esse projeto, eu tomei como verdade algumas premissas. Vou citar algumas para o melhor entendimento do fluxo desenvolvido.

- Produtos e clientes são controlados pelos seus próprios serviços, não precisando me preocupar em acessa-los para consultas ou alterações.
- O serviço de lista de desejos é síncrono e de importância mediana par ao processo, não sendo interessante prende-lo com um processo de retry e não valendo a pena a implementação de um fluxo assíncrono para servir de solução. Sendo assim, assumo que a melhor alternativa seja retornar o erro para quem chamou o serviço com uma mensagem informativa para que assim possa ser refeita a operação.

## Tecnologias usadas

Para o desenvolvimento e testes foram utilizadas as tecnologias e recursos abaixo:

- Spring Boot
- Java 17
- MapStruct
- MongoDB
- Redis
- SonarQube
- Lombok
- Junit
- Mockito
- Docker

## Teste

Para executar os testes, assim como a análise do Sonar, utilizei do recurso de contêineres Docker. Deixei disponível no projeto o arquivo docker-compose para tal, restando apenas fazer algumas leves configurações para o projeto.

## Arquiteturas e padrões

Como arquitetura eu escolhi o Clean Arch para o desenvolvimento e organização do projeto, tomei a liberdade de fazer algumas alterações para melhor adequar as necessidades do projeto.
Além disso, também procurei seguir os padrões SOLID assim como deixar a arquitetura o mais limpa possível, me valendo de alguns padrões de projeto amplamente utilizados.

## Versionamento

O serviço foi versionado usando o Github como repositório, como estava trabalhando sozinho, não vi a necessidade de usar branchs diferentes para as features, usando apenas a branch master para o desenvolvimento, entendo que em um ambiente coorporativo ou quando se trabalha com mais desenvolvedores, o ideal seria ter utilizado uma estrutura de branch develop e branchs especificas para as features.