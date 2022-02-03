(ns clojure-ui-course.shared.components
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rtfe]
            [markdown-to-hiccup.core :as m]
            [clojure-ui-course.util :as u]
            ["@heroicons/react/outline" :refer [PencilAltIcon ChevronRightIcon ChevronDownIcon]]))

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

(defn task [i text]
  [:div.py-2.px-4.bg-sky-100.rounded.border.border-sky-200.text-sky-900.my-column
   [:div.flex.items-center.space-x-1
    [:div.w-5.h-5
     [:> PencilAltIcon]]
    [:span.font-semibold (str "Task " i ".")]]
   (md text)])

(defn collapsible [title & children]
  (r/with-let [open? (r/atom true)]
    [:div.my-column
     [:div.flex.items-center.-translate-x-8
      [:button {:on-click #(swap! open? not)}
       [:h2.p-1.mr-1.rounded.hover:bg-slate-200
        [:div.w-5.h-5
         (if @open?
           [:> ChevronDownIcon]
           [:> ChevronRightIcon])]]]
      (md title)]
     (when @open?
       (map #(if (string? %) (md %) %) children))]))


(defn component-boundary
  "Wraps a component in an error boundary. Can pass in a map with fallback-view. If the fallback-view is a fn, then it will recieve the error as the first argument"
  [*error _ _]
  (r/create-class
   {:get-derived-state-from-error
    (fn [e]
      (reset! *error e)
      #js {})
    :reagent-render
    (fn [*error fallback child]
      (if @*error
        fallback
        child))}))