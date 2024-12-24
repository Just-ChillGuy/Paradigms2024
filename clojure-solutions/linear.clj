(defn operation [f]
      (fn [& args]
          (apply mapv f args)))

(defn opArr [f arr val]
      (mapv #(f % val) arr))

(def v+ (operation +))
(def v- (operation -))
(def v* (operation *))
(def vd (operation /))

(defn scalar [v1, v2] (reduce + (v* v1 v2)))

(defn vect [[a-x a-y a-z] [b-x b-y b-z]]
      [(- (* a-y b-z) (* a-z b-y))
       (- (* a-z b-x) (* a-x b-z))
       (- (* a-x b-y) (* a-y b-x))])

(defn v*s [v s] (opArr * v s))

; :NOTE: naming "operation"
(defn matrix-operation [operation m1 m2]
      (vec (mapv #(operation %1 %2) m1 m2)))

(defn m+ [m1 m2]
      (matrix-operation v+ m1 m2))
(defn m- [m1 m2]
      (matrix-operation v- m1 m2))
(defn m* [m1 m2]
      (matrix-operation v* m1 m2))
(defn md [m1 m2]
      (matrix-operation vd m1 m2))

(defn m*s [m s]
      (matrix-operation (partial v*s) m (repeat (count m) s)))

(defn m*v [m v]
      (mapv #(scalar % v) m))

(defn transpose [m]
      (vec (apply map vector m)))

(defn m*m [m1 m2]
      (let [m_tr (transpose m2)]
           (mapv #(m*v m_tr %) m1)))

(defn cuboid
      [op]
      (fn [x' y']
          (mapv op x' y')))

(def c+ (cuboid m+))
(def c- (cuboid m-))
(def c* (cuboid m*))
(def cd (cuboid md))
