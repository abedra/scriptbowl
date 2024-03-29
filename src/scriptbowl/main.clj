(ns scriptbowl.main
  (:require [clojure.java.io :as io]
            [scriptbowl.csv :as csv]
            [scriptbowl.data :as data]))

(defn read-data []
  (csv/csv-seq
   (java.io.StringReader. data/raw)))

(defn ticker []
  (into {} (map
            (juxt first identity)
            data/parsed)))

(defn ticker-for [symbol]
  ((ticker) symbol))

(defn open-for [symbol]
  (second (ticker-for symbol)))

(defn high-for [symbol]
  (nth (ticker-for symbol) 2))

(defn low-for [symbol]
  (nth (ticker-for symbol) 3))

(defn close-for [symbol]
  (nth (ticker-for symbol) 4))

(defn volume-for [symbol]
  (nth (ticker-for symbol) 5))