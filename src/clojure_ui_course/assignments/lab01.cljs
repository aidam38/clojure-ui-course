(ns clojure-ui-course.assignments.lab01
  (:require [clojure-ui-course.shared.components :as c]))

(defn main []
  [:div
   (c/md "
# Lab01: Basic Clojure

_To Clojure, or to ClojureScript, that is the question._

Clojure compiles to JVM (Java Virtual Machine) bytecode, ClojureScript compiles to JavaScript. In this course, we're going to be using exclusively ClojureScript (cljs), either in `node.js` (local JavaScript runtime) or in the browser.

Because the browser tooling can get a bit complicated, we're going to start on our local machines, using `node.js`. The best way to use cljs with `node.js` for small projects is `nbb`, a small fast cljs compiler. Once installed, typing `nbb` to the terminal will spawn a cljs console similar to typing `python`.

We're going to be using VS Code with the Calva extension, which provides everything we'll need (syntax highlighting, formatting, and the REPL).

## Setup
### VS Code

Install VS Code [here](https://code.visualstudio.com/download) (_unless you already have it_). Then, install the **Calva: Clojure & ClojureScript Interactive Programming** extension (it might take a while).

### Node.js and nbb

Follow these steps:
1. Install node.js [here](https://nodejs.org/en/) or (on Mac) by running `brew install node` (_unless you already have it, see below_)
    - a. Check by running `node -v` and `npm -v`.
2. Install nbb by running `npm install -g nbb`.
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

## Part 1

## Part 2")])
