(ns main
  (:require [io-points :refer [reader print-points]])
  (:require [interpolation :refer [execute-linear execute-lagrange]]))

(defn -main []
  (let [seq (reader)
        points (reductions conj [] seq)]

    (doseq [po points]
      (when (>= (count po) 2)
        (println "Linear:")
        (print-points (execute-linear po)))
      (when (>= (count po) 4)
        (println "Lagrange:")
        (print-points (execute-lagrange po))))))