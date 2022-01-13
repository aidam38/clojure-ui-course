(ns clojure-ui-course.core
  (:require [reagent.dom :as rdom]
            [clojure-ui-course.views :refer [main]]))

(defn mount-root []
  (rdom/render [main] (.getElementById js/document "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  (mount-root))

(defn ^:export init []
  (mount-root))