(ns clojure-ui-course.events
  (:require [re-frame.core :refer [reg-event-db]]
            [reitit.frontend.controllers :as rtfc]
            [clojure-ui-course.state :as s]))

(reg-event-db
 :init-index-state
 (fn [_ _]
   s/default-index-state))

(reg-event-db 
 :init-assignment-state 
 (fn [app-state [_ name]]
   app-state))

(reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match   (:current-route db)
         controllers (rtfc/apply-controllers (:controllers old-match) new-match)]
     (assoc db :current-route (select-keys (assoc new-match :controllers controllers) [:data :parameters])))))
