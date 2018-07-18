package br.com.icarros.desafio.cnt;

public enum Filtros {

	FILTRO_NACIONALIDADE_PILOTO(0,"Busca todas corridas de acordo com a nacionalidade do piloto em determinada temporada/rodada"),
	FILTRO_NOME_PILOTO(1,"Busca todas corridas de acordo com o nome do piloto em determinada temporada/rodada"),
	FILTRO_SOBRENOME_PILOTO(2,"Busca todas corridas de acordo com o sobrenome do piloto em determinada temporada/rodada"),
	FILTRO_NACIONALIDADE_CONSTRUTOR(3,"Busca todas corridas de acordo com a nacionalidade do construtor em determinada temporada/rodada"),
	FILTRO_NOME_CONSTRUTOR(4,"Busca todas corridas de acordo com o nome do construtor em determinada temporada/rodada"),
	FILTRO_NOME_CORRIDA(5,"Busca todas corridas de acordo com o nome do circuito em determinada temporada/rodada"),
	FILTRO_POSICAO(6,"Busca de todas corridas quem ficou em determinada posição em determinada temporada/rodada"),
	FILTRO_GRID(7,"Busca de todas corridas quem largou em determinada posição no grid em determinada temporada/rodada"),
	FILTRO_MAIOR_PONTUACAO(8,"Busca de todas corridas que piloto/construtor teve a maior pontuação em determinada temporada/rodada"),
	FILTRO_MENOR_PONTUACAO(9,"Busca de todas corridas que piloto/construtor teve a menor pontuação em determinada temporada/rodada"),
	FILTRO_MAIOR_VOLTAS(10,"Busca de todas corridas que piloto/construtor teve o maior numero de voltas completas em determinada temporada/rodada"),
	FILTRO_MENOR_VOLTAS(11,"Busca de todas corridas que piloto/construtor teve o menor numero de voltas completas em determinada temporada/rodada"),
	FILTRO_MAIOR_VELOCIDADE(12,"Busca de todas as corridas quem alcançou a maior velocidade"),
	FILTRO_MENOR_VELOCIDADE(13,"Busca de todas as corridas quem alcançou a menor velocidade"),
	FILTRO_PILOTO_MAIS_VELHO(14,"Busca de todas as corridas qual é o piloto mais velho em determinada temporada/rodada"),
	FILTRO_PILOTO_MAIS_NOVO(15,"Busca de todas as corridas qual é o piloto mais novo em determinada temporada/rodada"),
	FILTRO_INVALIDO(-1,"Filtro não mapeado!");
	
    private final int code;
    private final String label;

    private Filtros(int code, String label) {
        this.code = code;
        this.label = label;
    }
    
	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static Filtros getFiltrosByCode(int code) {
		for (Filtros f  : Filtros.values()) {
			if(f.code == code){
        	  return f;
          }
      }
	  return FILTRO_INVALIDO;
   }    
}
