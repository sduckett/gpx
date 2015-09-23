(ns gpx.time-test
  (:require [gpx.time :refer :all]
            [clj-time.core :as t]
            [clojure.test :refer :all]))

(deftest test-elapsed-time
  (testing "if we have no observations, we cannot count the passing of time"
    (is (= 0 (elapsed-time []))))

  (testing "a regular day is finite, whether or not today comes before tomorrow"
    (let [today (t/now)
          tomorrow (t/plus today (t/days 1))
          time-points [{:time today} {:time tomorrow}]]
      (is (= 86400 (elapsed-time time-points)))
      (is (pos? (elapsed-time (reverse time-points))))))

  (testing "clj-time requires that time flow in the appropriate direction"
    (with-redefs [elapsed-time
                  (fn [coll] (t/in-seconds
                             (t/interval (:time (first coll))
                                         (:time (last coll)))))]
      (let [prior-time (t/epoch)
            latter-time (t/now)
            time-travel [{:time latter-time} {:time prior-time}]]
        (is (thrown? java.lang.IllegalArgumentException
                     (elapsed-time time-travel)))))))
