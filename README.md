# Desafio Icarros

API REST JSON para atender a proposta do Desafio 

## DESIGN PATTERNS FACADE

Para o desafio, escolhi o design patterns Facade na estrutura do projeto, pois a ideia que tive foi se em uma proxima versão for adicionado um novo tipo de consulta como por exemplo consulta de corridas de motos apenas adicionaria um novo módulo no sistema.  Basicamente a ideia foi simplificar para permitir uma melhor modularização do sistema.

Referencia https://www.ateomomento.com.br/facade-padrao-de-projeto/ 

## Estrutura do projeto

### RestController

4 métodos principais, sendo 2 GET (getDadosCorrida e getInfo) e 2 POST (getDadosCorridaByFilter e getDadosCarregaBase)
Embora ambos estejam documentados via SWAGGER segue breve descrição:

A ideia da utilização dos métodos POST, principalmente em getDadosCorridaByFilter é para deixar algo genérico, onde permite com facilidade adicionar um novo tipo de filtro a pesquisa sem a necessidade de criar um novo GET ou um novo método.

```
getDadosCorrida -> Metodo para realizar a busca específica de uma corrida essa operação tem como objetivo consultar os dados especificos de uma corrida de acordo com a temporada e rodada informados 
```

```
getInfo -> Metodo para exibir os tipos de consultas que podem ser realizadas por filtro, essa operação tem como objetivo exibir quais filtros podem ser utilizados na consulta por filtro. Atualmente são 15 tipos de filtro definidos.

Filtros: 
0 - Busca todas corridas de acordo com a nacionalidade do piloto em determinada temporada/rodada
1 - Busca todas corridas de acordo com o nome do piloto em determinada temporada/rodada
2 - Busca todas corridas de acordo com o sobrenome do piloto em determinada temporada/rodada
3 - Busca todas corridas de acordo com a nacionalidade do construtor em determinada temporada/rodada
4 - Busca todas corridas de acordo com o nome do construtor em determinada temporada/rodada
5 - Busca todas corridas de acordo com o nome do circuito em determinada temporada/rodada
6 - Busca de todas corridas quem ficou em determinada posição em determinada temporada/rodada
7 - Busca de todas corridas quem largou em determinada posição no grid em determinada temporada/rodada
8 - Busca de todas corridas que piloto/construtor teve a maior pontuação em determinada temporada/rodada
9 - Busca de todas corridas que piloto/construtor teve a menor pontuação em determinada temporada/rodada
10 - Busca de todas corridas que piloto/construtor teve o maior numero de voltas completas em determinada temporada/rodada
11 - Busca de todas corridas que piloto/construtor teve o menor numero de voltas completas em determinada temporada/rodada
12 - Busca de todas as corridas quem alcançou a maior velocidade
13 - Busca de todas as corridas quem alcançou a menor velocidade
14 - Busca de todas as corridas qual é o piloto mais velho em determinada temporada/rodada
15 - Busca de todas as corridas qual é o piloto mais novo em determinada temporada/rodada
```

```
getDadosCorridaByFilter -> Metodo para realizar a busca específica de uma corrida por filtros, essa operação tem como objetivo consultar os dados especificos de uma corrida através do filtro informado - Filtros disponíveis no /getInfo")
```
```
getDadosCarregaBase -> Metodo para realizar o carregamento da base atraves de um determinado periodo, essa operação tem como objetivo carregar a base de dados através do filtro definido com os dados vindos externamente do API JSON
```

### Facade

Contem as regras dos rests. Na estrutura existe uma classe BaseFacade, esta classe tem como objetivo caso venha numa versão futura o sistema ganhar novos módulos utilizar métodos que possam ser comuns entre os facades, como atualmente o RestTemplate getJson e os métodos de conversão de valores

### Repository 
Criado com o MongoDB existe um método além dos extendidos para realizar uma busca específica o findBySeasonAndRound para buscar os dados pela temporada e pela rodada da corrida.

End with an example of getting some data out of the system or using it for a little demo

## Testes Unitários

Para o desafio realizei a criação de testes de todas as principais camadas, Testes para o Repository, para o Facade, para as Controllers e para os Endpoints. 

Cada classe dos testes contém os testes que ao meu ver foram mais cruciais para o sistema operar com sucesso.

## Sugestões para próximas versões

Para as próximas versões seguem algumas sugestões:
```
-> Adicionar novos filtros, existem além dos 15 mapeados até o momento, mais algumas opções de filtros que podem ser mapeadas.
```
```
-> Possibilidade de adicionar novo módulo, se for necessário poderia adicionar novos módulos de tipos de corridas diferentes.
```
```
-> Incrementar a documentação do SWAGGER, se for necessário para deixar mais limpo a documentação pode ser incrementada.
```
```
-> Observar e se encontrar a possibilidade, realizar mais alguns refactorys no código para deixar o código mais limpo
```
```
-> Observar e se encontrar a possibilidade, realizar mais alguns testes unitários no código, para garantir melhor funcionamento do sistema
```

## Autores

* **Guilherme Vilela** - *Versão Inicial* - [gvilela1108](https://github.com/gvilela1108)

## Versão
1.0
