domains
	kfc=carr(symbol,symbol).
predicates
	phone_number(symbol,symbol)
	%car(kfc,symbol)
	bank(symbol,symbol,real,symbol)
	mycar(symbol,integer,integer,symbol)
	byPrice(integer)
	getBank(symbol,symbol)
clauses
	phone_number("Albert","111111111111111111").
	phone_number("Betty","2222222222222222222").
	phone_number("Carol","3333333333333333333").
	phone_number("Carol","5555555555555555555").
	phone_number("Betty","4444444444444444444").
	
	%name	price	year	color  
	mycar(focus,1500,2009,red).
	mycar(focus,1000,2007,pink).
	mycar(focus,500,2006,yellow).
	mycar(focus,2000,2010,black).
	mycar(focus,1000,2009,red).
	
	mycar(pajero,1500,2009,red).
	mycar(pajero,1000,2008,pink).
	mycar(pajero,500,2007,yellow).
	mycar(pajero,1500,2011,black).
	mycar(pajero,1000,2009,red).
	
	mycar(lada,1500,2009,pink).
	mycar(lada,1000,2007,pink).
	mycar(lada,1500,2006,yellow).
	mycar(lada,2000,2010,black).
	mycar(lada,1000,2009,red).

	%car(carr("Albert","a"),"Focus").
	%car(carr("Carol","b"),"Pajero").
	%car(carr("Carol","c"),"Kia").
	%car(carr("Betty","d"),"Lada").
	%car("Betty","Focus").
	%car("Carol","Pajero").
	%car("Carol","Kia").
	
	bank("Albert","SBER",100,"Moscow").
	bank("Albert","VTB",1000,"Moscow").
	bank("Albert","VTB",2000,"NY").
	bank("Albert","SBER",5000,"Moscow").
	
	bank("Betty","VTB",200,"Moscow").
	bank("Betty","SBER",2000,"NY").
	bank("Betty","VTB",1500,"Moscow").
	bank("Betty","VTB",500,"NY").
	
	
	
	
    byPrice(Price) :-
    	mycar(_,CarPrice,CarYear,_),CarPrice<Price.
    
    getBank(Name,Bank):-
    	bank(Name,Bank,Money,_),mycar(_,CarPrice,_,_),CarPrice<Money. 
   
    
	
	
	%bank("Carol","SBER",300,"NY").
	%bank("Albert","VTB",4000,"Moscow").
	
	%bank("Albert","SBER",100,"Moscow").
	%bank("Betty","VTB",200,"NY").
	%bank("Carol","SBER",300,"NY").
	%bank("Albert","VTB",4000,"Moscow").
	
	
GOAL
	%car("Carol",Car),phone_number("Carol",Number).
	%bank(USER,"SBER",_,"NY"),phone_number(USER,Number).
	%car(carr(XAXAXA,"c"),NAME).
	
	getBank("Albert",Bank).
	%bank("Albert",BANK,VALUE,_),byPrice(Value).