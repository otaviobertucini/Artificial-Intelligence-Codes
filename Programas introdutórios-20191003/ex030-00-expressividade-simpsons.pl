%% EXPRESSIVIDADE COM VARIAVEIS vs. SEM VARIAVEIS
%
%  Objetivo: estabelecer um paralelo entre expressividade
%  da logica de primeira-ordem e proposicional.
%  Tambem serve para introduzir posteriormente recursividade
%  em PROLOG (exercicios 3, 4 e 5)
%
%  observe que:
%  1. variaveis iniciam com letra maiuscula, nao ha variaveis
%  2. constantes iniciam com letra minuscula
%  3. predicados iniciam com letra minuscula
%
%  EXERCICIOS
%  1. Definir uma regra para especificar todas as maes: mae(X) :- ...
%  2. Definir uma regra para obter todas as irmas: irma(X, Y) :- ...
%     (sendo que as irmas sao ambas do sexo feminino)
%  3. Desenhe a arvore genealogica da familia observando o
%     predicado progenitor
%  4. Represente na arvore genealogica o predicado
%     antepassado
%  5. Definir uma regra para obter o antepassado mais
%     longinquo de lisa por parte de mae (sem usar variaveis)

antepassado(abraham, homer) :-
        progenitor(abraham, homer).
antepassado(abraham, bart) :-
	progenitor(abraham, homer),
	progenitor(homer, bart).

% resposta 1 
mae(X) :- fem(X), progenitor(X, Y).

% resposta 2
irma(X, Y) :- fem(X), fem(Y), progenitor(Z, X), progenitor(Z, Y).

% abraham Simpson eh progenitor de homerSimpson
progenitor(abraham, homer).
progenitor(homer,  bart).
progenitor(homer,  lisa).
progenitor(homer,  maggie).
progenitor(marge,  bart).
progenitor(marge,  lisa).
progenitor(marge,  maggie).
progenitor(mrBouvier, marge).
progenitor(jackieBouvier, marge).

% homerSimpson eh do sexo masculino
masc(homer).
masc(bart).
masc(abraham).        %pai homer
masc(mrBouvier).      %pai da marge

%jackieBouvier eh do sexo feminino
fem(jackieBouvier).   %mae da marge
fem(penelope).        %mae homer
fem(maggie).          %filha
fem(lisa).            %filha
fem(marge).           %mae
fem(selmaBouvier).    %irma da marge
fem(pattyBouvier).    %irma da marge



















