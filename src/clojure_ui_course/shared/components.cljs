(ns clojure-ui-course.shared.components
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rtfe]
            [markdown-to-hiccup.core :as m]
            [clojure-ui-course.util :as u]))

(defn md [text]
  (-> text m/md->hiccup m/component))

(defn my-md [text]
  [:div.my-column
   (md text)])

(defn h1 [text]
  [:h1 text])

(defn h2 [text]
  [:h2 text])

(defn h3 [text]
  [:h3 text])

(defn section [& children]
  [:div (u/keyify-children children)])

(defn p [text]
  [:div (md text)])

(defn internal-link [route label]
  [:a.text-blue-600.hover:text-blue-800.hover:underline
   {:href (apply rtfe/href route)}
   label])

(defn external-link [url label]
  [:a.text-blue-600.hover:text-blue-800.hover:underline.visited:text-purple-600
   {:href url}
   label])

(defn render-mixed [page]
  (mapv #(if (string? %) (my-md %) %) page))

(defn icon [El]
  [:div.w-4.h-4.text-slate-500
   [:> El]])

#_(defn collapsible [title & children]
  (r/with-let [open? (r/atom true)]
              ))