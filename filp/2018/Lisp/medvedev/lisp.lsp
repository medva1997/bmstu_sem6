(defun pow_(x y r)
	(if (= y 0)
		r
		(pow_ x (- y 1) (* x r))
	)
)
(defun pow (x y)
	(pow_ x y 1)
)

(defun summlst(lstb)
	(apply `+ (cdr lstb))
)
	
(defun mult( a plstb)
	(
		* a (pow 3 (summlst plstb))
	)
)

(defun func (lsta lstb r)
	(if (or (null lsta) (null lstb))
		r
		(func (cdr lsta) (cdr lstb)	(+ r (mult (car lsta) lstb)))
	)
)

(defun f (lsta lstb)
	(func lsta lstb 0)
)
