(ns main
  (:require [io-points :refer [reader print-points]])
  (:require [interpolation :refer [execute-linear execute-lagrange]])
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.string :as str]))

(def cli-options
  [["-a" "--algorithm ALG"
    :parse-fn #(str/lower-case (str/trim %))
    :validate [#(or (= % "linear") (= % "lagrange") (= % "all")) "Wrong algorithm."]]

   ["-s" "--step STEP"
    :default 1.0
    :parse-fn #(parse-double %)
    :validate [#(pos? %) "Negative step, exit."]]

   ["-h" "--help"]])

(defn usage [options-summary]
  (->> ["This is my program. There are many like it, but this one is mine."
        ""
        "Interpolation"
        ""
        "Options:"
        options-summary
        ""
        "Algorithms:"
        "  linear     Execute linear method"
        "  lagrange   Execute lagrange method"
        "  all        Execute all methods (linear and lagrange)"
        ""]
       (str/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn validate-args [args]
  (let [{:keys [options errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors))
      (empty? (:algorithm options)) (exit 1 "No algorithm, exit.")
      :else {:options options})))

(defn -main [& args]
  (let [{:keys [options]} (validate-args args)
        algorithm  (:algorithm options)
        step (:step options)
        seq (reader)
        points (reductions conj [] seq)]

    (doseq [po points]
      (when (and (or (= algorithm "linear") (= algorithm "all")) (>= (count po) 2))
        (println "Linear:")
        (print-points (execute-linear po step)))
      (when (and (or (= algorithm "lagrange") (= algorithm "all")) (>= (count po) 4))
        (println "Lagrange:")
        (print-points (execute-lagrange po step))))))
