(ns clojure-ui-course.assignments.lab01.lab01
  (:require [reagent.core :as r]
            [clojure-ui-course.shared.components :as c]
            [clojure-ui-course.shared.codemirror :refer [editor]]
            [clojure-ui-course.shared.sci :as sci]
            [clojure-ui-course.setup.setup :as setup]
            [clojure-ui-course.util :as u])
  (:require-macros [clojure-ui-course.util :refer [slrp]]))

(def program1 (slrp "src/clojure_ui_course/assignments/lab01/game1.cljs"))
(def program2 (slrp "src/clojure_ui_course/assignments/lab01/game2.cljs"))
(def program2c (slrp "src/clojure_ui_course/assignments/lab01/game2c.cljs"))


(defn state-comp [*state]
  [:pre (str @*state)])

(defn widget [{:keys [namespaces user-ns program]}]
  (r/with-let [*editor-string (r/atom program)
               [ctx eval'] (sci/init-evaluation namespaces)
               _ (eval' @*editor-string)
               *main    (get-in @(:env ctx) [:namespaces user-ns 'main])
               *state   (get-in @(:env ctx) [:namespaces user-ns 'state])]
    (eval' @*editor-string)
    [:div.flex.space-x-2.p-4.rounded.shadow.border
     [:div.border.rounded
      {:class "w-3/5"}
      [editor
       {:on-blur       #(reset! *editor-string %)
        :default-value program}]]
     [:div.space-y-4
      {:class "w-2/5"}
      [:div.flex.justify-end.not-prose
       [state-comp @*state]]
      [:div.p-2.border.rounded.flex.justify-center
       [@*main]]]]))


(defn game1 []
  [widget
   {:namespaces {'reagent.core (ns-publics 'reagent.core)}
    :user-ns 'example.core
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
     :program program2}]
   (when @won? [:div.center-row "secret message!"])])

(defn game2c []
  [:div
   [widget
    {:namespaces {'reagent.core (ns-publics 'reagent.core)
                  'game.core {'valid-state? valid-state?
                              'gamemap gamemap
                              'get-px px}}
     :user-ns 'game.student
     :program program2c}]
   (when @won? [:div.center-row "secret message!"])])


(def main
  [:div
   "# Lab01: \"Win the Game\" and Basic Clojure

This lab has two parts:
* Part 1 will give you a taste of how UI development in Clojure looks like,
* Part 2 will start getting you comfortable in Clojure (though that's a long path).
    
*You may do the parts in any order*. 
    
Part 1 has no setup and is completely in the browser! Part 2 requires you to set up VSCode with a Clojure REPL.

## Part 1

In this part of the lab, you will first read a simple Clojure program that renders a button with a counter, and then try to fix up an unfinished game where you have to reach the goal by moving the player. 
    
The code in this part contains a lot of things you have no chance of understanding yet, and all these parenthesees may just leave you dazed and confused. This is partly the intention (by the end of this course, you will understand everything in this code and more), partly a tradeoff to make the first lab cool and exciting. 
    
We recommend you treat this part as a *linguistics exercise* – try to infer as much as you can from the code, and copy and paste all the parts that do similar things.

### Example"
   [:div.max-w-7xl.w-full.mx-auto
    [game1]]
   "### Challenge"
   [:div.max-w-7xl.w-full.mx-auto
    [game2]]
   "### Challenge solution"
   [:div.max-w-7xl.w-full.mx-auto
    [game2c]]

   "## Part 2
    
_This part is still very much unfinished and I haven't worked on it much since..._

_To Clojure, or to ClojureScript, that is the question._

Clojure compiles to JVM (Java Virtual Machine) bytecode, ClojureScript compiles to JavaScript. In this course, we're going to be using exclusively ClojureScript (cljs), either in `node.js` (local JavaScript runtime) or in the browser.

Because the browser tooling can get a bit complicated, we're going to start on our local machines, using `node.js`. The best way to use cljs with `node.js` for small projects is `nbb`, a small fast cljs compiler. Once installed, typing `nbb` to the terminal will spawn a cljs console similar to typing `python`.

We're going to be using VS Code with the Calva extension, which provides everything we'll need (syntax highlighting, formatting, and the REPL).

## Setup
### VS Code

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
5. Click 'Add key'

Finally, clone the repository for this lab!
[link](https://gitlab.caltech.edu/cs12-clojureui-22sp/lab01)

    "])

