%% EXEMPLO 010-00
%% Nacionalidade e regionalidade de comidas.
%
% O objetivo eh compreender que um programa tem:
% 1. CONSTANTES (atomos) que designam objetos (ex. pizza)
% 2. CLAUSULAS que podem ser
% 2.1 FATOS (ex. calzone eh comida italiana = italiana(calzone)
% 2.2 REGRAS (toda comida mineira eh brasileira
%     PARA TODO x, mineira(x) -> brasileira(x)
% 2.3 CONSULTAS: no prompt, eh possivel verificar se sao satisfeitas
%     pela base de conhecimentos (no jargao PROLOG, diz-se
%     atingir um objetivo):
%     ?- italiana(lazanha).  pergunta se lazanha eh italiana
%     ?- brasileira(tutu).   pergunta se tutu eh comida brasileira
%     ?- brasileira(X).      pergunta por todas as comidas brasileiras
%     ?- brasileira(X), uruguaia(X). pergunta se ha uma comida que seja
%                                    brasileira AND uruguaia
%
% observe que:
%  1. variaveis iniciam com letra maiuscula. Ex. X
%  2. constantes iniciam com letra minuscula. Ex. tutu, feijoada
%  3. predicados iniciam com letra minuscula. Ex. italiana(X)
%
%  *** EXERCÍCIO ***
%  Altere o programa para expressar por meio de uma regra que uma pessoa
%  gosta de uma comida quando ambos forem do mesmo pais.



italiana(pizza).
italiana(calzone).
italiana(lazanha).
brasileira(X) :- mineira(X).
brasileira(X) :- gaucha(X).
mineiraAndGaucha(X) :- mineira(X), gaucha(X).
mineira(tutu).
mineira(feijoada).
gaucha(churrascada).
uruguaia(churrascada).
argentina(churrascada).
paraguaia(churrascada).



















