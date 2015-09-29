(ns gpx.geometry-test
  (:require [gpx.geometry :refer :all]
            [clojure.test :refer :all]))

(deftest test-geometric-things
  (testing "rounding of numbers is sane"
    (is (= 42.02 (round2 42.01983)))))

#_(deftest test-cider-and-precise-numbers
    (testing "if this still crashes cider via the nrepl-server ;)"
      (is (= 42.02 (round2 42.0198723645983645823)))))
