(defn evaluate [operation args]
  (operation args))

(defn makeOpFun [f op]
  (fn [& args]
    (fn [vars]
      (apply f (map (fn [name] (name vars)) args)))))

(def add (makeOpFun + '+))

(def subtract (makeOpFun - '-))

(def multiply (makeOpFun * '*))

(defn div [expr & divisors]
  (case (count divisors)
    0 (/ 1.0 (double expr))
    (reduce (fn [expr1 expr2] (/ expr1 (double expr2))) expr divisors)))

(def divide (makeOpFun div '/))

(def negate (makeOpFun - 'negate))

(def sin (makeOpFun #(Math/sin %) 'sin))

(def cos (makeOpFun #(Math/cos %) 'cos))

(defn variable [var]
  (fn [args] (args var)))

(defn constant [const]
  (fn [args] const))
;hw11
(defn evaluate [this args] (((this :prototype) :evaluate) this args))

(defn toString [this] (((this :prototype) :toString) this))

(defn constructor
  [constr prototype]
  (fn [& args] (apply constr {:prototype prototype} args)))

(defn constant-constructor [this cnstant]
  (assoc this :cnstant cnstant))

(def Constant (constructor constant-constructor
                           {:evaluate (fn [this args] (this :cnstant))
                            :toString (fn [this] (str (this :cnstant)))
                            }))

(defn variable-constructor [this var]
  (assoc this :var var))

(def Variable (constructor variable-constructor
                           {:evaluate (fn [this args] (args (this :var)))
                            :toString (fn [this] (this :var))
                            }))

(defn op-constructor [this & op]
  (assoc this :operation op))

(defn makeOpObj [f op]
  (constructor op-constructor
               {:evaluate (fn [this args] (apply f (map #(evaluate % args) (this :operation))))
                :toString (fn [this] (str "(" op " " (clojure.string/join " " (map toString (this :operation))) ")"))
                }))

(def Add (makeOpObj + "+"))

(def Subtract (makeOpObj - "-"))

(def Multiply (makeOpObj * "*"))

(def Divide (makeOpObj div "/"))

(def Ln (makeOpObj #(Math/log %) 'ln))

(def Exp (makeOpObj #(Math/exp %) 'exp))

(def Negate (makeOpObj - "negate"))

;hw11
(def op-obj {'+ Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, 'exp Exp, 'ln Ln , 'cnst Constant, 'var Variable})
;hw10
(def op-fun {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'sin sin, 'cos cos, 'cnst constant, 'var variable})

(defn parser [expr operation]
  (cond
    (coll? expr) (apply (operation (first expr)) (map #(parser % operation) (next expr)))
    (number? expr) ((operation 'cnst) expr)
    :else ((operation 'var) (str expr))))


(defn parseFunction [input] (parser (read-string input) op-fun))
;hw11
(def parseObject (fn [input] (parser (read-string input) op-obj)))
