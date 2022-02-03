(ns clojure-ui-course.routes.assignments.lab01
  (:require [reagent.core :as r]
            [clojure-ui-course.shared.components :as c]
            [clojure-ui-course.shared.codemirror :refer [editor]]
            [clojure-ui-course.shared.sci :as sci]
            [clojure-ui-course.util :as u]
            ["@headlessui/react" :refer [Popover]])
  (:require-macros [clojure-ui-course.util :refer [slrp]]))

(def program1 (slrp  "resources/lab01/game1.cljs"))
(def program2 (slrp  "resources/lab01/game2.cljs"))

(defn state-comp [*state]
  [:pre (str @*state)])

(defn fallback []
  [:div "didn't work!"])

(defn render [{:keys [editor-string namespaces user-ns]}]
  (let [[ctx eval'] (sci/init-evaluation namespaces)
        _ (eval' editor-string)
        *main    (get-in @(:env ctx) [:namespaces user-ns 'main])
        *state   (get-in @(:env ctx) [:namespaces user-ns 'state])]
    [:div.space-y-4.bg-slate-100
     {:class "w-2/5"}
     [:div.flex.justify-end.not-prose.text-slate-500
      [state-comp @*state]]
     [:div.p-2.flex.justify-center
      [@*main]]]))

(defn widget [{:keys [namespaces user-ns program ls-key]}]
  (r/with-let [cached (.getItem js/localStorage ls-key)
               *editor-string (r/atom (if cached cached program))
               *error (r/atom false)]
    ;;(eval' @*editor-string)
    [:<>
     [:div.flex.space-x-2.rounded.border.divide-x
      [:div
       {:class "w-3/5"}
       [editor
        {:on-blur       #(do (reset! *error false)
                             (reset! *editor-string %)
                             (.setItem js/localStorage ls-key %))
         :default-value program}]]
      [c/component-boundary
       *error
       [fallback]
       [render {:editor-string @*editor-string
                :namespaces namespaces
                :user-ns user-ns}]]]
     [:div.h-1]
     [:div.flex.justify-end
      [:> Popover {:class "relative"}
       [:> (.-Button Popover)
        [:button.px-2.py-1.rounded.hover:bg-slate-200.text-sm.uppercase {:on-click #()} "Reset"]]
       [:> (.-Panel Popover) {:class "absolute z-10"}
        [:div.overflow-hidden
         [:div "Are you sure? This will overwrite your progress."]
         [:button {:on-click #(do (reset! *editor-string program)
                                  (.setItem js/localStorage ls-key program))}
          "Yes, I'm sure."]]]]]]))


(defn game1 []
  [widget
   {:namespaces {'reagent.core (ns-publics 'reagent.core)}
    :user-ns 'example.core
    :ls-key "lab01gam1"
    :program program1}])

(def game-info {:tile-size 20
                :width 20 ;; in tiles
                :height 20 ;; in tiles
                })

(def ts (:tile-size game-info))

(def objects [{:type :wall
               :rect [[0 1] [0 20]]}
              {:type :wall
               :rect [[1 20] [19 20]]}
              {:type :wall
               :rect [[19 20] [0 20]]}
              {:type :wall
               :rect [[1 19] [0 1]]}
              {:type :wall
               :rect [[6 7] [1 13]]}
              {:type :wall
               :rect [[7 13] [12 13]]}
              {:type :wall
               :rect [[12 13] [6 12]]}
              {:type :flag
               :rect [[9 10] [9 10]]}])

(def tile-colors
  {:wall "bg-slate-400"
   :flag "bg-orange-300"})

(defn px [x]
  (str (* x ts) "px"))

(defn render-object [{type              :type
                      [[x1 x2] [y1 y2]] :rect}]
  [:div.absolute
   {:class (type tile-colors)
    :style {:left (px x1)
            :bottom (px y1)
            :width (px (- x2 x1))
            :height (px (- y2 y1))}}])

(def won? (r/atom false))

(defn valid-state? [{:keys [x y]}]
  (when (and (= x 9) (= y 9))
    (reset! won? true))
  (every?
   (fn [{[[x1 x2] [y1 y2]] :rect}]
     (or (>= x x2) (< x x1) (>= y y2) (< y y1)))
   (filter #(= :wall (:type %)) objects)))

(defn gamemap []
  [:div.bg-slate-200
   {:style {:width  (px (:width game-info))
            :height (px (:height game-info))}}
   (for [obj objects]
     ^{:key (hash obj)} [render-object obj])])

(defn game2 []
  [:div
   [widget
    {:namespaces {'reagent.core (ns-publics 'reagent.core)
                  'game.core {'valid-state? valid-state?
                              'gamemap gamemap
                              'get-px px}}
     :user-ns 'game.student
     :ls-key "lab01game2"
     :program program2}]
   (when @won? [:div.center-row "secret message!"])])

(def main
  [:div
   "# Lab01: Win the Game and Basic Clojure

This lab has two parts:
* Part A will give you a taste of how UI development in Clojure looks like,
* Part B will start getting you comfortable in Clojure (though that's a long path).
    
*You may do the parts in any order*. If you get stuck on Part A, just do Part B and then return.
    
Part A has no setup and is completely in the browser! Part B requires you to set up VSCode with a Clojure REPL.

## Part A

In this part of the lab, you will first read a simple Clojure program that renders a button with a counter, and then try to fix up an unfinished game where you have to reach the goal by moving the player. 
    
The code in this part contains a lot of things you'll probably not be able to understand yet, and all these parenthesees may make you dazed and confused. This is partly the intention, partly a tradeoff to make the first lab cool and exciting. By the end of this course, you will understand everything in this code and more.
    
We recommend that you treat this part as a *linguistics exercise* – try to infer as much as you can from the code, and copy and paste all the parts that do similar things."

   "### UI \"Hello World\"

Below, you can see one of the most basic piece of UI – a simple button which increments a counter. Try it!

 "
   [:div.max-w-6xl.w-full.mx-auto
    [game1]]
   "Except for the CSS class `my-button`, nothing is hidden from you. Don't worry about the first form starting with `(ns ...`, it's just declaring the namespace and importing `reagent`, which is a cljs wrapper around React you will learn about more later in the course."
   [c/task 0 "Try changing `inc` to `dec` and see what happens. This should give you a feel how the editing works. If you see any errors not caused by you, please report them. "]
   "### Win the Game!
_You have suddenly found yourself in the middle of a game. Unfortunately, the controls are broken, oh no! Fix the controls and win the game._
    "
   [:div.max-w-6xl.w-full.mx-auto
    [game2]]
   "Here, a good chunk of the game is actually hidden from you. These functions are implemented elsewhere:
* `valid-state?` checks whether the next state isn't in a wall or whether you've won,
* `gamemap` is the background/environment component,
* `get-px` takes in an x or y coordinate and returns the corresponding pixel position on the board.


There are two main places you will need to make edits at, marked [1] and [2]. Here are some hints for them: 
1. You will definitely need the [case](https://clojuredocs.org/clojure.core/case) form. You may use the keywords `:up`, `:left`, `:down`, and `:right` as a way to represent `dir`ection.
2. You may want to split your controls into two `div`s, one for the up button, and one for the left, down, and right buttons. (the syntax would be: `[:div.center-row [:button...]]`). Passing arguments to functions works like this in Clojure `(move-player! :up)`."


   [c/task 1 "Move the player to the yellow block in the center to win the game. **After you win the game, you will receive a secret code**, which you will submit along with your Part B submission later."]

   "## Part B  
_To Clojure, or to ClojureScript, that is the question._

Clojure compiles to JVM (Java Virtual Machine) bytecode, ClojureScript compiles to JavaScript. In this course, we're going to be using exclusively ClojureScript (cljs), either in `node.js` (local JavaScript runtime) or in the browser.

Because the browser tooling can get a bit complicated, we're going to start on our local machines, using `node.js`. The best way to use cljs with `node.js` for small projects is `nbb`, a small fast cljs interpreter. It works similarly to the `python` command line interface.

We're going to be using VS Code with the Calva extension, which provides everything we'll need (syntax highlighting, formatting, and the REPL).

" [c/collapsible "## Setup"
   "### VS Code

Install VS Code [here](https://code.visualstudio.com/download) (_unless you already have it_). Then, install the **Calva: Clojure & ClojureScript Interactive Programming** extension (it might take a while).

### Node.js

Follow these steps:
1. Install node.js [here](https://nodejs.org/en/) or (on Mac) by running `brew install node` (_unless you already have it, see below_)
    - a. Check by running `node -v` and `npm -v`.

### Install nbb
Install nbb by running `npm install -g nbb`.
  - a. Check by running `nbb`. 
  - b. You're likely going to run into a permission error. [Here](https://docs.npmjs.com/resolving-eacces-permissions-errors-when-installing-packages-globally) is how to fix it.

### Caltech Gitlab SSH key

Follow these steps:
1. Create an `ssh` key by running `ssh-keygen -t rsa -b 4096` (_unless you already have one, see next step`). Use an empty password.
2. Open the file located at `~/.ssh/id_rsa.pub` and copy the contents.
3. Go here [https://gitlab.caltech.edu/-/profile/keys](https://gitlab.caltech.edu/-/profile/keys) (sign in if necessary) and paste into the big text box labeled 'Key'.
4. Give your key some title and don't fill out the expiration.
5. Click 'Add key'"]

   "## The REPL

Finally, clone the repository for this lab!
[link](https://gitlab.caltech.edu/cs12-clojureui-22sp/lab01)

    "])

