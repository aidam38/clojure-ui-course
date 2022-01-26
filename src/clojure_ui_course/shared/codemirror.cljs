(ns clojure-ui-course.shared.codemirror
  (:require [reagent.core :as r]
            [applied-science.js-interop :as j]
            ["@codemirror/commands" :refer [defaultKeymap indentWithTab]]
            ["@codemirror/state" :refer [EditorState]]
            ["@codemirror/view" :refer [EditorView keymap]]
            ["@codemirror/history" :refer [history historyKeymap]]
            ["@codemirror/matchbrackets" :refer [bracketMatching]]
            ["@codemirror/closebrackets" :refer [closeBrackets closeBracketsKeymap]]
            ["@codemirror/gutter" :refer [lineNumbers highlightActiveLineGutter]]
            ["@codemirror/highlight" :refer [defaultHighlightStyle classHighlightStyle]]
            ["@codemirror/language" :refer [indentOnInput]]
            ["@codemirror/stream-parser" :refer [StreamLanguage]]
            ["@codemirror/legacy-modes/mode/clojure" :refer [clojure]]))

(defn js-concat [& xs]
  (clj->js
   (remove nil? (apply concat xs))))

(defn editor [{:keys [on-change
                      on-blur
                      read-only?
                      default-value]}]
  (r/with-let [extensions
               (js-concat
                [(.. EditorView -lineWrapping)
                 (.. EditorState -allowMultipleSelections (of true))
                 (.. EditorState -readOnly (of read-only?))
                 (.theme EditorView
                         (j/lit {"&.cm-focused" {:outline "none !important"}}))
                 (bracketMatching)
                 (closeBrackets)
                 (indentOnInput)
                 (lineNumbers)
                 (highlightActiveLineGutter)
                 (history)
                 defaultHighlightStyle
                 classHighlightStyle
                 (.. keymap (of (js-concat
                                 closeBracketsKeymap
                                 defaultKeymap
                                 historyKeymap
                                 [indentWithTab])))
                 (.define StreamLanguage clojure)
                 (when on-change (.. EditorView -updateListener (of #(on-change (.. % -state -doc toString)))))])
               *editor-view (r/atom nil)
               mount! (fn [el]
                        (when el
                          (let [editor-view (new EditorView
                                                 #js{:state (.create EditorState #js {:extensions extensions
                                                       :doc        default-value})
                                                     :parent el})]
                            (reset! *editor-view editor-view))))]
    [:div (merge {:ref mount!}
                 (when on-blur {:on-blur #(on-blur (.. @*editor-view -state -doc toString))}))]
    (finally (j/call @*editor-view :destroy))))