predicates
  car(symbol, integer, integer, symbol)
  bank(symbol, symbol, symbol, integer)
  comp(integer, symbol)
  moneyforcar(symbol, symbol, symbol)
clauses
  car("Lada", 2008, 103000, "red").
  car("Lada", 2010, 110000, "blue").
  car("KIA", 2010, 300000, "red").
  car("Mersedes", 2015, 600000, "blue").
  car("Audi", 2012, 550000, "black").
  car("Audi", 2014, 400000, "red").
  car("Lada", 2008, 110000, "black").
  
  bank("Sberbank", "Moscow", "Ivanov", 120000).
  bank("Tinkoff", "Moscow", "Ivanov", 95000).
  bank("VTB", "Moscow", "Ivanov", 320000).
  bank("Sberbank", "Saint Petersburg", "Petrov", 1000000).
  bank("Sberbank", "Volgograd", "Sidorov", 120000).
  bank("Tinkoff", "Moscow", "Sidorov", 150000).
  
  
  comp(Price, Color):-car(_, _, CarPrice, Color), CarPrice < Price.
  moneyforcar(People, Bank, Car):-bank(Bank, _, People, Sum), car(Car, _, Ssum, _), Ssum < Sum.
goal 
  %car(_,2008, Price, Color),Price < 100001.
  %bank(Bank, _, Name, Value).
  %comp(100001, Color).
  %moneyforcar("Ivanov", Bank, Car).
  moneyforcar("Sidorov", Bank, Car).