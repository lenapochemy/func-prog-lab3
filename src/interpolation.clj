(ns interpolation
  ;; (:require [clojure.java.io])
  (:require [clojure.string :as str])
  ;; (:require [java.io.BufferedReader])
  )

(defn hello []
  (print "Hello\n"))

(defrecord Point [x y])

(def min-x 1.0)
(def max-x 5.0)

(defn linear-interpolation [x1 y1 x2 y2 x]
  (let [a (/ (- y2 y1) (- x2 x1))
        b (- y1 (* a x1))]
    ;; (print a b)
    (+ (* a x 1.0) b)))

(defn linear-a [point1 point2]
  (/ (- (:y point2) (:y point1)) (- (:x point2) (:x point1))))

(defn linear-b [a point1]
  (- (:y point1) (* a (:x point1))))

(defn linear-new-point [a b x]
  (->Point x  (+ (* a x 1.0) b)))

(defn lin []
  (let [x1 1 y1 1 x2 3 y2 2 step 1]
    (loop [i min-x]
      (when (<= i max-x)
        (print i, " ")
          ;;  (print i, (linear-interpolation x1 y1 x2 y2 i), "\n") 
        (recur (+ i step))))
    (print "\n")
    (loop [i min-x]
      (when (<= i max-x)
        (print (linear-interpolation x1 y1 x2 y2 i), " ")
        (recur (+ i step))))
    (print "\n")))


(defn parse-point [line]
  (if (str/blank? line)
    ((println "Empty input, exit.")
     (System/exit 0))
    (let [strs (str/split line #"[, ;]+")]
      (if (= (count strs) 2)
        (let [[str-x str-y] strs
              x (parse-double str-x)
              y (parse-double str-y)]
          (if (or (nil? x) (nil? y))
            ((println "Not number input, exit.")
             (System/exit 0))
            (->Point x y)))

        ((println "Bad input, exit.")
         (System/exit 0)))))

  )


(defn reader []
  (let [p (line-seq (java.io.BufferedReader. *in*))
        p2 (map str/trim p)
        p3 (map parse-point p2)]
    p3)

  )

(defn print-point [point]
  (let [x (format "%.3f" (:x point))
        y (format "%.3f" (:y point))]
    (println "x =" x " y =" y)))

(defn pypypy [points]

  ;; (let [point (first points)]
  ;; (println "x=" (:x point) " y=" (:y point))
  ;; )

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
    )

  )

(defn -main []
  ;; (hello)
  ;; (lin)
  ;; (print (read-line))
  ;; (print "mfvdfvd")

  ;; (reader)
  (let [seq (reader)
        points (reductions conj [] seq)]

    (doseq [po points]
  ;; (pypypy po)
      (when (>= (count po) 2)
        (pypypy po)))))