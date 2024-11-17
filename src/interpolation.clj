(ns interpolation
  (:require [output :refer [print-point]]))

(defrecord Point [x y])

;; (defn linear-interpolation [x1 y1 x2 y2 x]
;;   (let [a (/ (- y2 y1) (- x2 x1))
;;         b (- y1 (* a x1))]
;;     ;; (print a b)
;;     (+ (* a x 1.0) b)))

(defn linear-a [point1 point2]
  (/ (- (:y point2) (:y point1)) (- (:x point2) (:x point1))))

(defn linear-b [a point1]
  (- (:y point1) (* a (:x point1))))

(defn linear-new-point [a b x]
  (->Point x  (+ (* a x 1.0) b)))

(defn execute-linear [points]
  (let [step 1
        last2 (take-last 2 points)
        point1 (first last2)
        point2 (first (rest last2))
        a (linear-a point1 point2)
        b (linear-b a point1)]

    (loop [i (:x point1)]
      (let [last-i (dec i)]
    ;; (print-point point1)
        (when (< last-i (:x point2))
      ;;  (print-point point1)
          (let [new-point (linear-new-point a b i)]
            (print-point new-point))
          (recur (+ i step)))))

;; (print-point point1)
      ;; (print-point point2)
    ))
