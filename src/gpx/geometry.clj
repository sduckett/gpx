(ns gpx.geometry
  (:require
   [clojure.algo.generic.math-functions :refer [pow sin cos asin sqrt round]]))

(def ^{:private true :doc "The radius of the earth is a little fuzzy here."}
  RADIUS-OF-EARTH-IN-METERS 6367)

(defn- rad
  "FIXME rad argument to haversine function (180° d/πR)."
  [d]
  (* d (/ Math/PI 180)))

(defn round2
  "Round to two decimal points"
  [n]
  (/ (round (* n 100)) 100.0))

(defn haversine
  "The haversine distance between two geographical points 1 and 2;
  see <https://en.wikipedia.org/wiki/Haversine_formula>"
  [p1 p2]
  (let [φ1 (p1 :lat)
        λ1 (p1 :lon)
        φ2 (p2 :lat)
        λ2 (p2 :lon)
        Δλ (Math/toRadians (- λ2 λ1))
        Δφ (Math/toRadians (- φ2 φ1))
        ϴ (+ (Math/pow (Math/sin (/ Δφ 2)) 2)
             (* (Math/cos (Math/toRadians φ1))
                (Math/cos (Math/toRadians φ2))
                (Math/pow (Math/sin (/ Δλ 2)) 2)))
        Δσ (* 2 (Math/asin (Math/sqrt ϴ)))]
    (* Δσ RADIUS-OF-EARTH-IN-METERS)))
