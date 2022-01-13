(ns clojure-ui-course.shared.components
  (:require [reitit.frontend.easy :as rtfe]))

(defn h1 [text]
  [:h1.text-3xl text])

(defn h2 [text]
  [:h2.text-xl text])

(defn internal-link [route label]
  [:a.text-blue-600.hover:text-blue-800.hover:underline
   {:href (apply rtfe/href route)}
   label])

(defn external-link [url label]
  [:a.text-blue-600.hover:text-blue-800.hover:underline.visited:text-purple-600
   {:href url}
   label])