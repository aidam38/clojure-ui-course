(ns clojure-ui-course.views
  (:require [cljs.pprint :refer [pprint]]
            [re-frame.core :refer [subscribe]]
            [reitit.frontend.easy :as rtfe]
            [clojure-ui-course.shared.components :as c]
            [clojure-ui-course.assignments.lab01 :as lab01]
            [clojure-ui-course.assignments.final-project :as final-project]))

(defn wrapper [child]
  [:div.text-slate-800.bg-slate-50
   [:div.max-w-2xl.w-full.mx-auto.px-4.sm:px-0.h-screen
    child]])

(defn index []
  [wrapper
   [:<>
    [:div.h-2]
    [:div.text-2xl "UI Development in Clojure"]
    [:div.text-lg.text-slate-400 "sp22"]
    [:div.h-2]
    [:div "Welcome to the course sentence."]
    [:div.h-2]
    [c/h2 "Assignments"]
    [:ul
     [:li
      [:a
       {:href (rtfe/href :assignment {:name "lab01"})}
       "Lab01"]]
     [:a
      {:href (rtfe/href :assignment {:name "final-project"})}
      "Final project"]]]])

(defn assignment []
  (let [assignment @(subscribe [:assignment-name])]
    [wrapper
     (case assignment
       "lab01" [lab01/main]
       "final-project" [final-project/main])]))

(defn debug []
  (let [app-state @(subscribe [:app-state])]
    [:pre.absolute.top-0.right-0.text-xs (with-out-str (pprint app-state))]))

(defn main []
  (let [route-name @(subscribe [:route-name])]
    [:div
     (case route-name
       :index [index]
       :assignment [assignment]
       [index])
     (when false
       [debug])]))