(ns scriptbowl.main
  (:require [clojure.browser.repl :as repl]
            [scriptbowl.data :as data]))

(repl/connect "http://localhost:9000/repl")

(defn ticker []
  (into {} (map
            (juxt first identity)
            data/parsed)))

(defn ticker-for [symbol]
  ((ticker) symbol))

(defn open-for [symbol]
  (second (ticker-for symbol)))

(defn close-for [symbol]
  (nth (ticker-for symbol) 2))

(defn high-for [symbol]
  (nth (ticker-for symbol) 3))

(defn low-for [symbol]
  (nth (ticker-for symbol) 4))

(defn volume-for [symbol]
  (nth (ticker-for symbol) 5))
