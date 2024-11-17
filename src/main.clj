(ns main
  (:require [input :refer [reader]])
  (:require [interpolation :refer [execute-linear]]))

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
        (execute-linear po)))))