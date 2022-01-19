(ns clojure-ui-course.util)

(defmacro inline-resource [path]
  (slurp path))