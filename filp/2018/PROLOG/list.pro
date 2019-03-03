domains
	list = integer*.
predicates
	%reverse
	reverse(list,list).
	reverse(list,list,list).
	
	%merge
	merge(list,list,list).
	
	even_pos(list, list).
	even_pos(list,list,list).
	
	myodd(integer)
	myeven(integer)
	elem(list,list)	
clauses
	reverse(List, Res) :- reverse(List, [], Res).
	reverse([], Res, Res).
	reverse([H|T], X, Res) :- reverse(T, [H|X], Res).
	
	merge([], List2, List2) :- !.
	merge([H1|T1], List2, [H1|T3]) :- merge(T1, List2, T3).
	
	even_pos(List,Res):- even_pos(List,[],Res).
	even_pos([],Res,Res).
	even_pos([_,H|T],X,Res):-even_pos(T,[H|X],Res).
	even_pos([_],X,Res):-even_pos([],X,Res).
	
	myodd(X):- X mod 2 = 1.
	myeven(X):- X mod 2 = 0.
	
	elem([],[]).	
	elem([H|T],Res):- myodd(H), elem(T,Res). 
	elem([H |T1],[H | T2]):- myeven(H), elem(T1, T2).
goal
	%reverse([1,2,3],Res).
	%merge([1,2,3],[4,5,6],Res).
	%even_pos([1,2,3,4,5],Res).
	elem([8,2,3,4,5,6],Res).

