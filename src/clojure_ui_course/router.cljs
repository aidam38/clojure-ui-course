(ns clojure-ui-course.router
  (:require [reitit.core :as r]
            [reitit.frontend :as rtf]
            [reitit.frontend.easy :as rtfe]
            [re-frame.core :refer [dispatch]]
            [clojure-ui-course.util :as u]))

(def routes
  [["/" {:name :home
         :controllers [{:start #(rtfe/push-state :index)}]}]
   ["/22sp" {:term "22sp"}
    ["/" {:name :index}]
    ["/assignment/:name" {:name :assignment
                          :controllers [{:parameters {:path [:name]}
                                         :start #(dispatch [:init-assignment-state (-> % :path :name)])}]}]]])

(def router
  (rtf/router
   routes))

(defn match-by-name [path]
  (apply r/match-by-name router path))

(defn match-by-path [url]
  (r/match-by-path router url))

(defn init-routes! []
  (rtfe/start!
   router
   #(dispatch [:navigated %])
   {:use-fragment true}))