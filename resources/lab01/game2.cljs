(ns game.student
  (:require [reagent.core :as r]
            [game.core :refer [valid-state? gamemap get-px]]))

(def state (r/atom {:x 3 :y 3}))

(defn move-player! [dir]
  (let [next-state @state] ;; 1
    (when (valid-state? next-state)
      (reset! state next-state))))

(defn controls []
  [:div
   ;; 2
   ;; ▲, ◀, ▼, ▶ for convenience
   ])

(defn player []
  [:div.absolute.player
   {:style {:left   (get-px (get @state :x))
            :bottom (get-px (get @state :y))}}
   "🥰"])

(defn board []
  [:div.relative
   [gamemap]
   [player]])

(defn main []
  [:div.center-col
   [board]
   [controls]])