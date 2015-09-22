(ns gpx.core-test
  (:require [gpx.core :refer :all]
            [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]))

(defn random-number [] (last (take-nth (rand) (gen/sample gen/int 300))))
(defn gen-float [] (+ (random-number) (/ (random-number) 10.0)))

(deftest gps-thing-test
  (testing "Something about GPS data"
    (is (= 1 2))))
