% O objetivo deste exemplo eh mostrar unificacao com recursividade,
% salientando a importancia da ordem das clausulas.
%
% Desenhe a árvore de busca do Prolog para a query
% ?- cam(tijucasdosul, Z).
%
% Ligar o trace e verificar o percurso do algoritmo de
% inferencia para saber todos os caminhos a partir de
% Curitiba (que estao na base de conhecimentos)
%
% ?- cam(ctba, Z).
%
% est = estrada - ligacao de um salto entre duas cidades
% cam = caminho - ligacao de um ou mais saltos entre cidades

% ctba - campo largo - ponta grossa
est(ctba, campolargo).
est(campolargo, pontagrossa).

% ctba - sao jose - tijucas do sul - guaratuba - garuva
est(ctba, saojose).
est(saojose, tijucasdosul).
est(tijucasdosul, guaratuba).
est(guaratuba, garuva).

cam(X, X).
cam(X, Y) :- est(X, Z), cam(Z, Y).


















