(ns gpx.geometry-test
  (:require [gpx.geometry :refer :all]
            [clojure.test :refer :all]))

(deftest test-geometric-things
  (testing "rounding of numbers is sane"
    (is (= 42.02 (round2 42.01983)))))

(deftest test-haversine-distance
  (testing "the distance between DEN and AUS"
    (let [den {:lat 39.861111 :lon -104.673056}
          aus {:lat 30.194444 :lon  -97.670000}]
      (is (= 1247.99 (round2 (haversine den aus)))))))

#_(deftest test-cider-and-precise-numbers
    (testing "if this still crashes cider via the nrepl-server ;)"
      (is (= 42.02 (round2 42.0198723645983645823)))))
