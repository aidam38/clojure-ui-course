(ns clojure-ui-course.views
  (:require [clojure-ui-course.shared.components :as c]))

(defn main []
  [:div.text-slate-800.bg-slate-50
   [:div.h-2]
   [:div.max-w-2xl.w-full.mx-auto.px-4.sm:px-0.h-screen
    [:div.text-2xl "UI Development in Clojure"]
    [:div.text-lg.text-slate-400 "sp22"]
    [:div.h-2]
    [:div "Welcome to the course sentence."]
    [:div.h-2]
    [c/h2 "Assignments"]
    [:ul 
     [:li 
      "Lab01"]
     [:li 
      "Final project"]]]])