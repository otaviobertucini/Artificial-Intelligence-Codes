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
% SOLUÇÃO AO EXERCÍCIO 4:
% acrescentar nova regra: cidades de altitute também nevam independente
% se lá chove ou não. Potosi é uma cidade de altitude.
%
chuvosa(curitiba).
chuvosa(joinville).
chuvosa(saoJoaquim).
chuvosa(urubici).
fria(curitiba).
muitoFria(saoJoaquim).
muitoFria(urubici).
muitoFria(caxias).
cidadeAltitude(potosi).
neva(X) :- muitoFria(X), chuvosa(X).
neva(X) :- cidadeAltitude(X).









