(ns clojure-ui-course.util)

(defn keyify-children [children]
  (map-indexed #(with-meta %2 {:key %1}) children))

(defn log [x]
  (js/console.log (clj->js x)))

(defn pad-number
  "Zero Pad numbers - takes a number and the length to pad to as arguments"
  [n c]
  (loop [s (str n)]
    (if (< (count s) c)
      (recur (str "0" s))
      s)))
