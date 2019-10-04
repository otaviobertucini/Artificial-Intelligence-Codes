%% EXEMPLO 02 MODIFICADO
%  Enunciado original do ex02
%% como faco para expressar, a partir do exemplo 01,
%% que uma pessoa gosta de uma comida quando ambos
%% forem do mesmo pais.
%
% MODIFICACOES deste programa:
% (a) Impede inferencias desejadas do predicado gosta
% ?- gosta(ana, COMIDA).
% COMIDA = pizza ;
% COMIDA = calzone ;
% COMIDA = lazanha ;
% COMIDA = ana ;    <== INDESEJADO
% COMIDA = luiza ;  <== INDESEJADO
% false.
%
% (b) Faz declaracoes mais reduzidas para o predicado gosta.
%     O modo abaixo nao eh o ideal - muito trabalhoso, pois
%     eh preciso uma regra para cada nacionalidade
%  gosta(Pessoa, Comida) :- italiana(Pessoa), italiana(Comida).

gosta(P, C) :- pessoa(P), temNacionalidade(P, X), temNacionalidade(C, X), comida(C).

%% coisas tem nacionalidade
% temNacionalidade(coisa, nacionalidade)
temNacionalidade(pizza,italiana).
temNacionalidade(calzone,italiana).
temNacionalidade(lazanha, italiana).
temNacionalidade(ana, italiana).
temNacionalidade(churrascada, uruguaia).
temNacionalidade(churrascada, argentina).
temNacionalidade(X, brasileira) :- mineira(X).
temNacionalidade(X, brasileira) :- gaucha(X).
mineira(maria).
mineira(tutu).
mineira(feijoada).
gaucha(anastacia).
gaucha(churrascada).
argentina(cecilia).

% algumas coisas sao pessoas
pessoa(ana).
pessoa(maria).
pessoa(anastacia).
pessoa(cecilia).

%algumas coisas sao comidas
comida(pizza).
comida(lazanha).
comida(churrascada).
comida(calzone).
comida(tutu).
comida(feijoada).












