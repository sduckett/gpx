(ns gpx.parsing.impl-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [gpx.parsing.impl :refer :all]))

(deftest test-find-tag
  (testing "find-tag filters a collection"
    (let [raw-data (slurp (io/resource "davis-meadows-sample.gpx"))
          parsed-xml (clojure.xml/parse
                      (java.io.ByteArrayInputStream. (.getBytes raw-data)))
          track  (:content (find-tag (:content parsed-xml) :trk))]
      (is (= :name (:tag (first track)))))))
