(ns input
  (:require [interpolation :refer [->Point]])
  (:require [clojure.string :as str]))

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
         (System/exit 0))))))

(defn reader []
  (let [p (line-seq (java.io.BufferedReader. *in*))
        p2 (map str/trim p)
        p3 (map parse-point p2)]
    p3))
