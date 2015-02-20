(ns com.billpiel.stackplan.core_test
  (:require [midje.sweet :refer :all]
            [com.billpiel.stackplan.core :refer :all]))

(defn make-adder
  [new-value]
  (fn [v n]
    (n (conj v new-value))))

(defn inc-plus
  [value1 value2 next]
  (next (inc value1) (+ value1 value2)))

(defn diff-dec
  [value1 value2 next]
  (next (- value2 value1) (dec value2)))

(def plan {:agg {:top (make-adder :top)
                 :middle [(make-adder 1)
                          (make-adder 2)
                          (make-adder 3)]
                 :bottom (fn [v n] (conj v :bottom))}

           :math {:top inc-plus
                  :middle [inc-plus diff-dec inc-plus]
                  :bottom (fn [value1 value2 n] value2)}

           :short-circuit {:top (make-adder :top)
                           :middle [(make-adder 1)
                                    (fn [v n] (conj v :done)) ;; end execution here
                                    (make-adder 2)]
                           :bottom (make-adder :bottom)}})

(fact "agg test"
      (run-plan (:agg plan) [[:init]])
      => [:init :top 1 2 3 :bottom])

(fact "math test"
      ;; 1 5
      ;; 2 6
      ;; 3 8
      ;; 5 7
      ;; 6 12

      (run-plan (:math plan) [1 5])
      => 12)

(fact "short circuit test"
      (run-plan (:short-circuit plan) [[:init]])
      => [:init :top 1 :done])
