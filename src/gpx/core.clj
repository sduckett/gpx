(ns gpx.core
  (:require [gpx.parsing :refer [get-points track->distance]]
            [gpx.time :refer [elapsed-time]]
            [gpx.geometry :refer [rround]])
  (:gen-class))

(defn -main [& args]
  (when-let [path (first args)]
    (let [points        (get-points path)
          elapsed-time  (elapsed-time points)
          distance      (rround (track->distance points))
          average-speed (rround (/ distance (/ elapsed-time 3600)))]
      (println "Distance:     " distance "km")
      (println "Elapsed time: " elapsed-time "s")
      (println "Average speed:" average-speed "km/h"))))
