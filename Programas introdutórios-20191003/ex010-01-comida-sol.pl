%% EXEMPLO 020-00
%% como faco para expressar, a partir do exemplo 010-00,
%% toda pessoa gosta de uma comida quando a pessoa
%% e a comida forem do mesmo pais.
%
% A consulta produz inferencias indesejadas
% ?- gosta(ana, COMIDA).
% COMIDA = pizza ;
% COMIDA = calzone ;
% COMIDA = lazanha ;
% COMIDA = ana ;    <== INDESEJADO
% COMIDA = luiza ;  <== INDESEJADO
% false.

%% o modo abaixo nao eh o ideal - muito trabalhoso, pois
%% eh preciso uma regra para cada nacionalidade
gosta(Pessoa, Comida) :- italiana(Pessoa), italiana(Comida).
gosta(Pessoa, Comida) :- brasileira(Pessoa), brasileira(Comida).

%% comidas
italiana(pizza).
italiana(calzone).
italiana(lazanha).
%% pessoas
italiana(ana).
italiana(luiza).
brasileira(maria).
brasileira(anastacia).
%% regras
brasileira(X) :- mineira(X).
brasileira(X) :- gaucha(X).
%% comidas
mineira(tutu).
mineira(feijoada).
gaucha(churrascada).
uruguaia(churrascada).
argentina(churrascada).
%% pessoa
argentina(cecilia).





