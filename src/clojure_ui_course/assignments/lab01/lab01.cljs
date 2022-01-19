(ns clojure-ui-course.assignments.lab01.lab01
  (:require [clojure-ui-course.shared.components :as c])
  (:require-macros [clojure-ui-course.util :refer [inline-resource]]))

(defn main []
  [:div
   (c/md (inline-resource "src/clojure_ui_course/assignments/lab01/lab01.md"))])