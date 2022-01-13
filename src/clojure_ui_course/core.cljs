(ns clojure-ui-course.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :refer [dispatch]]
            [clojure-ui-course.router :refer [init-routes!]]
            [clojure-ui-course.events]
            [clojure-ui-course.subs]
            [clojure-ui-course.views :refer [main]]))

(defn mount-root []
  (rdom/render [main] (.getElementById js/document "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  (mount-root))

(defn ^:export init []
  (init-routes!)
  (mount-root))