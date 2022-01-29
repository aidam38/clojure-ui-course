(ns clojure-ui-course.util)

(defn keyify-children [children]
  (map-indexed #(with-meta %2 {:key %1}) children))

(defn log [x]
  (js/console.log (clj->js x)))