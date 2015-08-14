(defproject gpx "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-time "0.8.0"]
                 [org.clojure/algo.generic "0.1.2"]]
  :main ^:skip-aot gpx.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
