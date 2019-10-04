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
% nao.
% %
% EXERCICIO 2: faça a mesma prova do exercicio 1 agora utilizando
% encadeamento para tras (backward chaining). Compare a sua prova com o
% trace produzido pelo Prolog. Observe como Prolog considera a ordem da
% declaração das cláusulas no momento de realizar a inferência.
%
% EXERCICIO 3: idem ao exercício 1 para a query neva(Z), soh que
% agora adicione o predicado resposta a query. Compare a sua prova com o
% trace produzido pelo Prolog. a) Identifique os pontos de backtracking.
% b) Por que PROLOG nao tentou a substituicao de Z/caxias? c) A ordem
% dos predicados influencia no numero de passos da resolucao empregada
% pelo PROLOG? Caso sim, qual ordem causaria menos passos?
%
% EXERCÍCIO 4: para a query neva(Z),  a) acrescente uma nova regra que
% defina que cidades em grande altitude são muito frias e, independente
% de serem chuvosas ou não, neva nestas cidades. Adicione uma cidade de
% altitude à KB (ex. Potosi, Bolívia – 4.050 m de altitude).
% b) desenhe a árvore de objetivos para encadeamento para trás para Z/potosi
%

chuvosa(curitiba).
chuvosa(joinville).
chuvosa(saoJoaquim).
chuvosa(urubici).
fria(curitiba).
muitoFria(saoJoaquim).
muitoFria(urubici).
muitoFria(caxias).
neva(X) :- chuvosa(X), muitoFria(X).








