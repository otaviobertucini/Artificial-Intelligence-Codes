%% EXEMPLO 01 - LOGICA PROPOSICIONAL
%%
%  query: as proposicoes da KB permitem inferir
%  que estah se falando de uma menina?
%  ?- menina.
%
%  As proposicoes que aparecem em vermelho nao
%  foram definidas, por isto, ao fazermos as
%  queries abaixo, obtemos erros:
%  ?- menino.
%  ?- crianca.
%
sexoFeminino.

crianca :- estudaNoPrimeiroGrau, !.
crianca :- estudaNoJardimDeInfancia.

estudaNoJardimDeInfancia.
estudaNoJardimDeInfancia :-  \+ (estudaNoPrimeiroGrau).

estudaNoPrimeiroGrau.
estudaNoPrimeiroGrau :- \+ (estudaNoJardimDeInfancia).

sexoMasculino :- \+ (sexoFeminino).
menino :- sexoMasculino, crianca.
menina :- sexoFeminino, crianca.













