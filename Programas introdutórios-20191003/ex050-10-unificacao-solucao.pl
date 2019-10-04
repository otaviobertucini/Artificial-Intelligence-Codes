% O objetivo deste exemplo eh mostrar como
% funciona resolucao, unificacao e substituicao em
% PROLOG.
%%
% O aluno devera ser capaz de identificar quando ocorre
% substituicao, unificacao e backtracking. Devera observar
% que o modo de encadeamento eh para tras = backward chaining.
% Tambem deve observar o funcionamento do metodo de prova por
% resolucao e relacionar o trace do PROLOG com a prova manual.
%
%
% Fazer queries, porem, ligue o trace antes:
% ?- trace.
% [trace]?- neva(curitiba). <teclar c (de creep) para avancar>
% [trace]?- neva(Z).
%
% EXERCICIO 1: coloque a KB abaixo na FNC e pelo metodo da
% resolucao diga se a query neva(curitiba) eh uma conclusao da KB ou
% nao. Compare a sua prova com o trace produzido pelo Prolog.
%
% EXERCICIO 2: idem para a query 2 (neva(Z), soh que agora
% observando a adicao do predicado resposta a query.
% Compare a sua prova com o trace produzido pelo Prolog.
% a) Identifique os pontos de backtracking.
% b) Por que PROLOG nao tentou a substituicao de Z/caxias?
% c) A ordem dos predicados influencia no numero de passos da
%    resolucao empregada pelo PROLOG? Caso sim, qual ordem causaria
%    menos passos?
%
chuvosa(curitiba).
chuvosa(joinville).
chuvosa(saoJoaquim).
chuvosa(urubici).
fria(curitiba).
muitoFria(saoJoaquim).
muitoFria(urubici).
muitoFria(caxias).
neva(X) :-  muitoFria(X), chuvosa(X).









