(ns gpx.core
  (:require [clojure.xml :refer :all]
            [clojure.algo.generic.math-functions :refer :all]
            [clj-time.format :as tf]
            [clj-time.core :as tc])
  (:gen-class))

(def RADIUS-OF-EARTH-IN-METERS 6367)

(defn rad [x]
  (* x (/ Math/PI 180)))

(defn meter [x] (* x 1000))

(defn haversine
  "Compute the distance between two points on the Earth's surface.
  See: <https://gist.github.com/frankvilhelmsen/1787462> and <https://en.wikipedia.org/wiki/Haversine_formula>"
  [position destination]
  (let [square_half_chord
          (+ (pow (sin (/ (rad (- (destination :lat) (position :lat))) 2)) 2)
             (* (cos (rad (position :lat)))
                (cos (rad (destination :lat)))
                (pow (sin (/ (rad (- (destination :lon) (position :lon))) 2)) 2)))
        angular_distance (* (asin (sqrt square_half_chord)) 2)]
    (* angular_distance RADIUS-OF-EARTH-IN-METERS)))

(defn parse-gpx
  "Gets the raw XML structure built from a file. Reads the whole dang thing."
  [path]
  (parse (java.io.ByteArrayInputStream. (.getBytes (slurp path)))))

(defn tag? [tag m]
  (when (= tag (:tag m)) m))

(defn find-tag [coll tag]
  (some (partial tag? tag) coll))

(defn transform-trkpt [p]
  (let [t   (find-tag (:content p) :time)
        ts  (tf/parse (first (:content t)))
        e   (find-tag (:content p) :ele)
        es  (first (:content e))
        lat (read-string (get-in p [:attrs :lat]))
        lon (read-string (get-in p [:attrs :lon]))]
    {:time ts
     :elevation es
     :lat lat
     :lon lon}))

(defn get-data [xml]
  (:content (find-tag (:content xml) :trk)))

(defn get-points [path]
  (let [raw (get-data (parse-gpx path))
        trks (:content (find-tag raw :trkseg))]
    (sort-by :time (map transform-trkpt trks))))

(defn calculate-time [coll]
  "in seconds"
  (tc/in-seconds
    (tc/interval (:time (first coll))
                 (:time (last coll)))))

;;; This gives a StackOverflowError when given 9830 points!
;;; 5000 works fine ;)
;;; davis-meadows-recon.gpx should be about 33.75 km long.
(defn calculate-distance [coll]
  (if (< (count coll) 2)
    0
    (+ (haversine (first coll)          ; is this holding on to the head the whole way down? seems like that might be a suspect in blowing the stack...
                  (second coll))
       (calculate-distance (rest coll)))))

(defn lazy-calculate-distance
  "Using simple tail-recursion, see if we can fix the StackOverflowError..."
  [coll]
  (letfn [(kapow [coll acc]
            (if (< (count coll) 2)
              acc
              (recur (rest coll)
                     (+ (haversine (first coll) (second coll))
                        acc))))]
    (kapow coll 0)))

(defn rround [n]
  (/ (round (* n 100)) 100.0))

(defn -main [& args]
  (when-let [path (first args)]
    (let [points        (get-points path)
          elapsed-time  (calculate-time points)
          distance      (rround (lazy-calculate-distance points))
          average-speed (rround (/ distance (/ elapsed-time 3600)))]
      (println "Distance:     " distance "km")
      (println "Elapsed time: " elapsed-time "s")
      (println "Average speed:" average-speed "km/h"))))
