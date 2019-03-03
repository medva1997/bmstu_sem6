domains
	ownership =
		house(string Address, real Price);
		car(string Model, string Color, integer Year, integer Price);
		boat(string Name, real Price, real Lenght);
		ground(real Area, real PriceFormet);
		dog(string Name, string Breed)
predicates
	owns(string Name, ownership).
  
clauses
	owns("John", house("Moscow",10000000)).
	owns("John", house("NY",1000000)).
	owns("Kate", house("Moscow",15000000)).
	owns("Ira", house("Tula",3000000)).
	owns("Ira", boat("Sun", 15000000000, 300)).
	owns("Kate", boat("Fishing", 15000, 3)).
	owns("Ira", boat("Small Sun", 1000000, 20)).
	owns("Ira", dog("Rufus", "breed1")).
	owns("Kate", dog("Bobic", "breed2")).
	owns("Kate", dog("Name2", "breed1")).
	
	owns("Ira", ground(5, 100)).
	owns("Ira", ground(15, 1000)).
	owns("Kate", ground(200, 100)).
	owns("Kate", ground(6, 1000)).
	owns("Ira", car("Lada", "Pink", 1997, 10000)).
	owns("Kate", car("Volvo", "Black", 2018, 10000000)).
	owns("Kate", car("Lada", "Green", 2005, 100000)).
goal
	owns("Kate", house(Location,Price)).	