%% EXEMPLO 01 - LOGICA PROPOSICIONAL
%%
%  query: as proposicoes da KB permitem inferir
%  que estah se falando de uma crianca?
%  ?- crianca.
%
%  E de uma menina?
%  ?- menina.
%
%  As proposicoes que aparecem em vermelho nao
%  foram definidas (ao menos de forma explicita).

estudaNoPrimeiroGrau.
estudaNoJardimDeInfancia.
sexoFeminino.
crianca :- estudaNoPrimeiroGrau.
crianca :- estudaNoJardimDeInfancia.
menina :- sexoFeminino, crianca.












