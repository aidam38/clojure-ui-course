(ns clojure-ui-course.core
  (:require [reagent.dom :as rdom]))

(defn main []
  [:div.text-slate-800 "Hello, Tailwind!"])

(defn mount-root []
  (rdom/render [main] (.getElementById js/document "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  (mount-root))

(defn ^:export init []
  (mount-root))