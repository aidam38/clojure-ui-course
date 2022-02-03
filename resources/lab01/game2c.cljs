(ns game.student
  (:require [reagent.core :as r]
            [game.core :refer [valid-state? gamemap get-px]]))

(def state (r/atom {:x 3 :y 3}))

(defn move-player! [dir]
  (let [next-state (case dir
                     :up    (update @state :y inc)
                     :down  (update @state :y dec)
                     :left  (update @state :x dec)
                     :right (update @state :x inc))]
    (when (valid-state? next-state)
      (reset! state next-state))))

(defn controls []
  [:div
   [:div.center-row
    [:button.my-button {:on-click #(move-player! :up)}    "▲"]]
   [:div.center-row
    [:button.my-button {:on-click #(move-player! :left)}  "◀"]
    [:button.my-button {:on-click #(move-player! :down)}  "▼"]
    [:button.my-button {:on-click #(move-player! :right)} "▶"]]])

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