(ns output)


(defn print-point [point]
  (let [x (format "%.2f" (:x point))
        y (format "%.2f" (:y point))]
    (println "x =" x " y =" y)))