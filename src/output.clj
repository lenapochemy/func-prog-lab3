(ns output)


(defn print-point [point]
  (let [x (format "%.3f" (:x point))
        y (format "%.3f" (:y point))]
    (println "x =" x " y =" y)))