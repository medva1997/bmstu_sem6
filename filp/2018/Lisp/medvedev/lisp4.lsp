(defun delit(line a r)
	(if (null line)
		r
		(cons (/ (car line) a) (delit (cdr line) a r))
	)
)

(defun minusline(linea lineb r)
	(if (null linea)
		r
		(cons (- (car lineb) (car linea)) (minusline (cdr linea) (cdr lineb) r))	
	)
)

(defun minus(marix line r)
	(if (null marix)
		r
		(cons (minusline 
				line (car marix) (minus (cdr marix) line r)))	
	)
)



