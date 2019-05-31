domains
      NAME=symbol
      NUM=string
      AREA=integer
      CITY=string
      STREET=string
      HOUSE=integer
      Father=symbol
      Mother=symbol
      CHILD=symbol
      INT=integer
      PRODUCER=symbol
      PRICE=integer
      COLOR=symbol
      LABEL=string
      YEAR=integer
      NUMBER=string
      car_label=cl(LABEL,PRICE)
      
      address=address(CITY,STREET,HOUSE)
      
      items=
	     ihouse(string City,string Street,integer House);
	     iphone(string Num).
      
       I=integer
       
predicates
	maxof(I,I,I).
	
	likes(symbol,symbol)
	abonent(NAME,NUM)
	abonname(NAME,NUM)
	abonnum(NAME,NUM)
	house(NAME,AREA,CITY,STREET,HOUSE)
	man(NAME)
  	woman(NAME)
  	parent(NAME, NAME)
  	father(NAME, NAME)
  	mother(NAME, NAME)
	housesAddr(NAME,CITY,STREET,HOUSE)
	housesAREA(NAME,AREA)
	mult(NAME,NUM,CITY)
	factorial(INT,INT)
	factorial2(INT,INT,INT)
	max(integer,integer,integer)
  	max3(integer,integer,integer,integer)
	car(NAME, car_label, YEAR)
	%car_c(NAME,car_label, NUMBER) 	
	%carfilter2(NAME, NUM,COLOR)
	%cl(LABEL,PRICE)
	phonebystreet(NUM,STREET)
	
	person(NAME,items)
	fib(INT,INT)	
	myfib(INT,INT,INT,INT)
clauses
	
	person(alex,iphone("1111111")).
	person(alex,iphone("1112121")).
	person(ivan,iphone("2222222")).
	person(petr,iphone("3333333")).
	person(semen,iphone("444444")).
	person(evgen,iphone("555555")).
	person(dima,iphone("6666666")).
	person(semen,iphone("777777")).
	person(oleg,iphone("888888")).
	person(roman,iphone("9999999")).	
	
	person(alex,ihouse("Moscow","Ramenki",140)).
	person(alex,ihouse("Lonodon","Baker",221)).
	person(alex,ihouse("NY","Baker",14)).
	person(ivan,ihouse("Moscow","Ramenki",10)).
	person(semen,ihouse("Lonodon","TROLOLO",14)).
	person(dima,ihouse("NY","Broadway",221)).
	
	phonebystreet(STREET,NUM):- person(NAME,ihouse(_,STREET,_)),person(NAME,iphone(NUM)).
    	 
	likes(ellen,tennis).
	likes(john,football).
	likes(tom,baseball).
	likes(eric,swimming).
	likes(mark,tennis).
	likes(bill,Activity):-likes (tom, Activity).
	abonent(alex,"1111111").
	abonent(alex,"1112121").
	abonent(ivan,"2222222").
	abonent(petr,"3333333").
	abonent(semen,"444444").
	abonent(evgen,"555555").
	abonent(dima,"6666666").
	abonent(semen,"777777").
	abonent(oleg,"888888").
	abonent(roman,"9999999").
	
	house(alex,2500,"Moscow","Ramenki",140).
	house(alex,560,"Lonodon","Baker",221).
	house(alex,70,"NY","Baker",14).
	house(ivan,2500,"Moscow","Ramenki",10).
	house(semen,56,"Lonodon","TROLOLO",14).
	house(dima,700,"NY","Broadway",221).
	
	
	car(alex1, cl("label1", 1), 2001).
  	car(alex2, cl("label2", 2), 2002).
  	car(alex1, cl("label3", 3), 2003).
  	car(alex4, cl("label1", 4),2004).
	
  	man(peter).
  	man(paul). 
  	woman(marry).
  	woman(eve).  
  	parent(adam,peter). % means adam is parent of peter
  	parent(eve,peter).
  	parent(adam,paul).
  	parent(marry,paul).
  
  	father(F,C):-man(F),parent(F,C).
  	mother(M,C):-woman(M),parent(M,C).
	
	abonname(NAME,NUM):-abonent(NAME,NUM).
	abonnum(NAME,NUM):-abonent(NAME,NUM).
	housesAddr(NAME,CITY,STREET,HOUSE):-house(NAME,_,CITY,STREET,HOUSE).
	housesAREA(NAME,AREA):-house(NAME,AREA,_,_,_).
	mult(NAME,NUM,CITY):-house(NAME,_,CITY,_,_),abonent(NAME,NUM).
	
	maxof(X,Y,Res) :- X>=Y, Res=X,!; Res=Y.
	 
 	max(X,Y,X):-X>Y.
 	max(X,Y,Y):- X <= Y.
  
  	max3(X,Y,Z,X):- X>Y, X>Z,!.
  	max3(_,Y,Z,Y):- Y>Z,!.
  	max3(_,_,Z,Z).
  	
	factorial(1,1):-!.
	factorial(N,R):-N1=N-1,factorial(N1,R1),R=R1*N.
	
	factorial2(1,M, M):-!.
  	factorial2(N, RES, M):- N1 = N-1,
                     		M1 = M * N, 
                     		factorial2(N1, RES, M1).
                  			
	myfib(1,R,R,_) :- !.	
	myfib(N,R,A,B) :- N1 = N-1,
		B1 = B+A , A1 = B, myfib(N1,R,A1,B1).
	fib(N,R) :-
		myfib(N,R,1,1).
	
goal
	%fib2(4,0,1,R).
	%abonname(alex,NUM).
	%housesAddr(alex,CITY,STREET,HOUSE).
	%housesAddr(NAME,"Lonodon",STREET,HOUSE).
	%housesAREA(alex,AREA).
	%mult(alex,NUM,ADDRESS).
	%house(alex,_,ADDRESS,_,_),abonent(alex,NUM).
	factorial(4,F).
	%phonebystreet("Ramenki",NUM).
	%owns("John", Thing).
	%maxof(6, 7,RES).
	%max3(1,2,3,T).
	%fib(7,R).
	%mother(T,peter).
	%car(alex1, cl(LABEL,PRICE), 2003).