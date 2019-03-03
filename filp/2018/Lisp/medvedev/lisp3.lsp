(defun pow_(x y r)
	(if (= y 0)
		r
		(pow_ x (- y 1) (* x r))
	)
)
(defun pow (x y)
	(pow_ x y 1)
)


(defun ffff(a plb) (* a (pow 3 (apply '+ (cdr plb)))))

(apply '+ (mapcar 'ffff '(1 2 3) (mapcon #'list '(1 2 3))))








