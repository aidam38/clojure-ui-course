(ns example.core
  (:require [reagent.core :as r]))

(def state (r/atom {:count 0}))

(defn inc-counter! []
  (let [next-state (update @state :count inc)]
    (reset! state next-state)))

(defn main []
  [:button.my-button {:on-click #(inc-counter!)} 
   "Count " (:count @state)])