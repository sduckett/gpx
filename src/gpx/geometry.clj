(ns gpx.geometry
  (:require
   [clojure.algo.generic.math-functions :refer [pow sin cos asin sqrt round]]))

(def ^{:private true :doc "The radius of the earth is a little fuzzy here."}
  RADIUS-OF-EARTH-IN-METERS 6367)

(defn- rad
  "FIXME rad argument to haversine function (180° d/πR)."
  [d]

  (* d (/ Math/PI 180)))

(defn- meter
  "FIXME what is the deal, yo?"
  [x] (* x 1000))

(defn rround
  "FIXME WTF is this for?"
  [n]
  (/ (round (* n 100)) 100.0))

(defn haversin
  "TODO Compute the versed sine"
  [theta]
  (/ 2 (- 1 (cos theta))))

(defn haversine
  "Compute the distance between two points on the Earth's surface.

  See: <https://gist.github.com/frankvilhelmsen/1787462> and
       <https://en.wikipedia.org/wiki/Haversine_formula>"
  [position destination]
  (let [square_half_chord
          (+ (pow (sin (/ (rad (- (destination :lat) (position :lat))) 2)) 2)
             (* (cos (rad (position :lat)))
                (cos (rad (destination :lat)))
                (pow (sin (/ (rad (- (destination :lon)
                                     (position :lon)))
                             2))
                     2)))
        angular_distance (* (asin (sqrt square_half_chord)) 2)]
    (* angular_distance RADIUS-OF-EARTH-IN-METERS)))
