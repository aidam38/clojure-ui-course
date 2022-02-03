(ns game.student
  (:require [reagent.core :as r]
            [game.core :refer [valid-state? gamemap get-px]]))

(def state (r/atom {:x 3 :y 3}))

(defn move-player! [dir]
  (let [next-state (case dir
                     :up    @state 
                     ;; [1] This currently does nothing to the state, change it so it updates the corresponding coordinate similar to the previous example (note: the opposite function to inc is dec)
                     ;; [2] Add movement left, down, and right here
                     )]
    (when (valid-state? next-state)
      (reset! state next-state))))

(defn controls []
  [:div
   [:div.center-row
    [:button.my-button {:on-click #(move-player! :up)}    "▲"]]
   ;; [2] Add another row with three buttons left, down, and right here (here's the ascii symbols for convenience ◀, ▼, ▶).
   ])

(defn player []
  [:div.absolute.player
   {:style {:left   (get-px (:x @state))
            :bottom (get-px (:y @state))}}
   "🥰"])

(defn board []
  [:div.relative
   [gamemap]
   [player]])

(defn main []
  [:div.center-col
   [board]
   [controls]])