(ns gpx.core
  (:require [gpx.parsing :refer [get-points track->distance]]
            [gpx.time :refer [elapsed-time]]
            [gpx.geometry :refer [round2]])
  (:gen-class))

(defn -main
  "One way to see the examples"
  [& args]
  (when-let [path (first args)]
    (let [points        (get-points path)
          elapsed-time  (elapsed-time points)
          distance      (round2 (track->distance points))
          average-speed (round2 (/ distance (/ elapsed-time 3600)))
          min-elevation (:elevation (first (sort-by :elevation points)))
          max-elevation (:elevation (last (sort-by :elevation points)))]
      (println "Distance:     " distance "km")
      (println "Elapsed time: " elapsed-time "s")
      (println "Average speed:" average-speed "km/h")
      (println "Min elevation" min-elevation "mz")
      (println "Max elevation: " max-elevation "m"))))
