(ns clojure-ui-course.shared.sci
  (:require [sci.core :as sci]
            [reagent.core]))

(def sci-ctx (sci/init
              {:namespaces {'reagent.core (ns-publics 'reagent.core)}}))

(defn render-string [source]
  (sci/eval-string* sci-ctx source))

(defn init-evaluation [namespaces]
  (let [ctx     (sci/init {:namespaces namespaces})
        eval    (fn [source] (sci/eval-string* ctx source))]
    [ctx eval]))