(ns fern.core
  (:require [quil.core :as q]))

(defn f1 [x y]
  [0 (* 0.16 y)])

(defn f2 [x y]
  [(+ (* 0.85 x) (* 0.04 y))
   (+ (* -0.04 x) (* 0.85 y) 1.6)])

(defn f3 [x y]
  [(+ (* 0.2 x) (* -0.26 y))
   (+ (* 0.23 x) (* 0.22 y) 1.6)])

(defn f4 [x y]
  [(+ (* -0.15 x) (* 0.28 y))
   (+ (* 0.26 x) (* 0.24 y) 0.44)])

(defn get-function []
  (let [probabilities [0.01 0.85 0.07 0.07]
        value (rand)]
    (cond
      (< value (nth probabilities 0)) f1
      (< value (+ (nth probabilities 0) (nth probabilities 1))) f2
      (< value (+ (nth probabilities 0) (nth probabilities 1) (nth probabilities 2))) f3
      :else f4)))

(defn draw-point
  "Draw a point at position (x, y)"
  [x y]
  (q/fill 0 255 0)
  (let [scale 2]
    (q/rect (* (+ x 2.1820) (/ 600 (+ 2.6558 2.1820))) (- 900 (* y (/ 900 9.9983))) scale scale)))

(defn update [x y]
  x)

(defn draw-fern
  "Draw a green fern"
  [x y iterations]

  (def xn (atom x))
  (def yn (atom y))

  (loop [i iterations]
    (when (> i 0)
      (let [position-function (get-function)
            position (position-function @xn @yn)
            x (first position)
            y (last position)]
        (draw-point x y)
        (swap! xn (partial update x))
        (swap! yn (partial update y)))
      (recur (dec i)))))

(defn setup
  "Set up for quil"
  []
  (q/smooth)
  (q/frame-rate 5)
  (q/background 0)
  (draw-fern 0 0 100000))

(defn display-fern
  "Displays a fern"
  []
  (q/defsketch fern
    :title "A Barnsley Fern"
    :setup setup
    :size [600 900]))

(display-fern)

