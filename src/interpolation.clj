(ns interpolation)

(defrecord Point [x y])

(defn linear [point1 point2 x]
  (let [a  (/ (- (:y point2) (:y point1)) (- (:x point2) (:x point1)))
        b  (- (:y point1) (* a (:x point1)))]
    (->Point x  (+ (* a x 1.0) b))))

(defn lagrange [points x]
  (let [y (reduce (fn [acc i]
                    (let [xi (:x (nth points i))
                          yi (:y (nth points i))
                          term (reduce (fn [prod j]
                                         (if (= i j)
                                           prod
                                           (* prod (/ (- x (:x (nth points j))) (- xi (:x (nth points j)))))))
                                       1
                                       (range 4))]
                      (+ acc (* yi term))))
                  0
                  (range 4))]
    (->Point x y)))

(defn count-generating-points [first-point last-point step]
  (Math/ceil (/ (+ (- (:x last-point) (:x first-point)) step) step)))

(defn generating-points [points step]
  (let [point-count (count-generating-points (first points) (last points) step)]
    (take point-count (iterate (fn [x] (+ x step)) (:x (first points))))))

(defn execute-linear [points step]
  (let [last2 (take-last 2 points)
        generated-points (generating-points last2 step)]
    (map (fn [x] (linear (first last2) (last last2) x)) generated-points)))

(defn execute-lagrange [points step]
  (let [last4 (take-last 4 points)
        generated-points (generating-points last4 step)]
    (map (fn [x] (lagrange last4 x)) generated-points)))




