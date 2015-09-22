(defproject gpx "0.1.0-SNAPSHOT"
  :description "What can you learn from a sequence of GPS Trackpoints?"
  :url "https://github.com/sduckett/gpx"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-time "0.8.0"]
                 [org.clojure/algo.generic "0.1.2"]]
  :main ^:skip-aot gpx.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[criterium "0.4.3"]
                                  [org.clojure/test.check "0.8.1"]]}})
