% Este exemplo modifica o anterior pela inversao da ordem
% das clausulas na última sentença.
%
% Desenhe a árvore de objetivos para as seguintes queries:
% a) ?cam(saojose, tijucasdosul).
% b) ?cam(saojose, guaratuba).
% c) ?cam(tijucasdosul, X).
%
% Observou algum problema na derivação da prova para alguma das
% situações acima?
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
cam(X, Y) :- cam(X, Z), est(Z, Y).  % cam e est foram invertidos
