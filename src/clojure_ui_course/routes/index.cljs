(ns clojure-ui-course.routes.index
  (:require ["@heroicons/react/outline" :refer [UserGroupIcon ClockIcon ChatAlt2Icon]]
            [clojure-ui-course.shared.components :as c]
            [clojure-ui-course.util :as u]))

(def events
  [{:type :lecture
    :title "Why UI and Why Clojure"
    :description "Course administrivia. Zoo of UI frameworks and languages. Functional programming and state management."
    :date (js/Date. 2022 2 28 14 00)
    :location "Annenberg 105"
    :slides nil}
   {:type :oh
    :title "Office Hours"
    :date (js/Date. 2022 2 29 18 00)
    :location "Annenberg 105"}
   {:type :lecture
    :title "Lecture 02"
    :description ""
    :date (js/Date. 2022 2 28 14 00)
    :location "Annenberg 105"
    :slides nil}
   {:type :lecture
    :title "Lecture 03"
    :description ""
    :date (js/Date. 2022 2 28 14 00)
    :location "Annenberg 105"
    :slides nil}
   {:type :lecture
    :title "Lecture 04"
    :description ""
    :date (js/Date. 2022 2 28 14 00)
    :location "Annenberg 105"
    :slides nil}
   {:type :deadline
    :title "Lab01"
    :date (js/Date. 2022 2 30 23 30)}])

(def days ["Sun" "Mon" "Tue" "Wed" "Thu" "Fri" "Sat"])

(def months ["Jan" "Feb" "Mar" "Apr" "May" "Jun" "Jul" "Aug" "Sep" "Oct" "Nov" "Dec"])

(defn pprint-date [date]
  (str
   (get days (.getDay date))
   ", "
   (get months (.getMonth date))
   " "
   (.getDate date)
   ", "
   (u/pad-number (.getHours date) 2)
   ":"
   (u/pad-number (.getMinutes date) 2)))

(defn render-event [{:keys [date type title description location slides]}]
  [:div.flex.h-12.py-1.px-3
   [:div.h-full.flex.items-center.w-4
    (case type
      :lecture [c/icon UserGroupIcon]
      :deadline [c/icon ClockIcon]
      :oh [c/icon ChatAlt2Icon]
      [:div])]
   [:div.px-4.flex.flex-col.justify-center.items-center.text-xs.text-slate-500.w-40
    [:div.uppercase (pprint-date date)]
    [:div location]]
   [:div
    [:div.flex
     [:div.font-semibold title]
     (when slides [:span "[ " [:a {:href slides} "slides"] " ]"])]
    [:div.italic description]]])

(defn main []
  [:<>
   [:div.my-column
    [:div.h-2]
    [c/h1 "UI Development in Clojure"]
    [:div.text-lg.text-slate-400 "sp22"]
    [:div.h-2]]
   (c/my-md "Welcome to the course sentence.
             
## Assignments")
   [:ul.list-disc.my-column
    [:li
     [c/internal-link [:assignment {:name "lab01"}] "Lab01"]]
    [:li
     [c/internal-link [:assignment {:name "final-project"}] "Final project"]]]
   (c/my-md "## Course staff
             
Adam K.")
   (c/my-md "## Schedule ")
   [:div.my-column.text-sm.p-2.bg-slate-100.rounded.border.border-slate-300.divide-y.space-y-1
    (for [event events]
      ^{:key (hash event)} [render-event event])]])
