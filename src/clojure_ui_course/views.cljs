(ns clojure-ui-course.views
  (:require [cljs.pprint :refer [pprint]]
            [re-frame.core :refer [subscribe]]
            [reitit.frontend.easy :as rtfe]
            [clojure-ui-course.shared.components :as c]
            [clojure-ui-course.util :as u]
            [clojure-ui-course.routes.assignments.lab01 :as lab01]
            [clojure-ui-course.routes.assignments.final-project :as final-project]
            [clojure-ui-course.routes.index :as index]
            ["@heroicons/react/outline" :refer [ArrowLeftIcon]]))

(defn wrapper [& children]
  [:div.bg-slate-50.px-4.sm:px-0.h-full.min-h-screen.prose.max-w-full.pb-96
   (u/keyify-children children)])

(defn assignment []
  (let [assignment @(subscribe [:assignment-name])]
    [:<>
     [:div.h-2]
     [:div.my-column
      [:button.px-2.py-1.rounded.hover:bg-slate-200
       {:on-click #(rtfe/push-state :index)}
       [:span.flex.text-xs.text-slate-500 [c/icon ArrowLeftIcon] [:span.ml-1.uppercase "Back"]]]]
     (c/render-mixed
      (case assignment
        "lab01"  lab01/main
        "final-project" final-project/main))]))

(defn debug []
  (let [app-state @(subscribe [:app-state])]
    [:pre.absolute.top-0.right-0.text-xs (with-out-str (pprint app-state))]))

(defn main []
  (let [route-name @(subscribe [:route-name])]
    [wrapper
     (case route-name
       :index [index/main]
       :assignment [assignment]
       [index/main])
     (when false
       [debug])]))
