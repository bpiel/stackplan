(ns com.billpiel.stackplan.core)

(defn assemble-stack
  [subplan]
  (filter (comp not nil?) (concat [(:top subplan)]
                                  (-> subplan :middle not-empty)
                                  [(:bottom subplan)])))

(defn descend
  [stack args]
  (if-let [top (first stack)]
    (let [args-safe (if (empty? args)
                      [nil]
                      args)
          r (rest stack)
          next (fn [& args] (descend r
                                     (vec args)))]
      (apply top (conj args-safe next)))))

(defn run-plan
  "I don't do a whole lot."
  [plan args]
  (-> (or plan {})
      assemble-stack
      (descend args)))
