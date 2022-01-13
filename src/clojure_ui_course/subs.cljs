(ns clojure-ui-course.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :app-state
 (fn [app-state _] app-state))

(reg-sub
 :route-name
 (fn [app-state _] (-> app-state :current-route :data :name)))

(reg-sub
 :assignment-name
 (fn [app-state _] (-> app-state :current-route :parameters :path :name)))