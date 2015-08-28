(ns gpx.time
  (:require [clj-time.core :as time]))

(defn elapsed-time [coll]
  "in seconds"
  (time/in-seconds
   (time/interval (:time (first coll))
                  (:time (last coll))))) ;; TODO benchmark
