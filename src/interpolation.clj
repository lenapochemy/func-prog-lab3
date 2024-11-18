(ns interpolation
  (:require [output :refer [print-point]]))

(defrecord Point [x y])

(defn linear [point1 point2 x]
  (let [a  (/ (- (:y point2) (:y point1)) (- (:x point2) (:x point1)))
        b  (- (:y point1) (* a (:x point1)))]
    (->Point x  (+ (* a x 1.0) b))))

(defn execute-linear [points]
  (let [step 1
        last2 (take-last 2 points)
        point1 (first last2)
        point2 (first (rest last2))]
    (println "Linear:")
    (loop [i (:x point1)]
      (let [last-i (dec i)]
    ;; (print-point point1)
        (when (< last-i (:x point2))
      ;;  (print-point point1)
          (let [new-point (linear point1 point2 i)]
            (print-point new-point))
          (recur (+ i step)))))

;; (print-point point1)
      ;; (print-point point2)
    ))
(defn lagrange [points x]
  ;; (if (= (count points) 4)
  ;;   ())

  (let [n (count points)
        y (reduce (fn [acc i]
                    (let [xi (:x (nth points i))
                          yi (:y (nth points i))
                ;; [xi yi] (nth points i)
                          term (reduce (fn [prod j]
                                         (if (= i j)
                                           prod
                                           (* prod (/ (- x (:x (nth points j))) (- xi (:x (nth points j)))))))
                                       1
                                       (range n))]
                      (+ acc (* yi term))))
                  0
                  (range n))]
    (->Point x y)))

(defn execute-lagrange [points]
  (let [step 1
        last4 (take-last 4 points)
        point1 (first last4)
        point4 (last last4)]
    (println "Lagrange:")
    ;; (loop [pos last4]
    ;;   (let [po (first pos)]
    ;;     (when (some? po)
    ;;       (print-point po)
    ;;       (recur (rest pos)))))
    ;; (print-point point1)
    ;; (print-point point4)

(loop [i (:x point1)]
      (let [last-i (dec i)]
    ;; (print-point point1)
        (when (< last-i (:x point4))
      ;;  (print-point point1)
          (let [new-point (lagrange last4 i)]
            (print-point new-point))
          (recur (+ i step)))))

;; (print-point point1)
      ;; (print-point point2)
    ))

