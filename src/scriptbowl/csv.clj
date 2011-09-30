(ns scriptbowl.csv
  "CSV parser."
  (:use [clojure.string :only (split)])
  (:import (java.io Reader BufferedReader PushbackReader)))

(defn read-unquoted-csv-field [rdr]
  (let [sb (StringBuilder.)]
    (loop []
      (let [i (.read rdr)]
        (if (neg? i)
          (str sb)
          (let [c (char i)]
            (cond (= c \,)           (str sb)
                  (or (= c \newline)
                      (= c \return)) (do (.unread rdr i) (str sb))
                      :else          (do (.append sb c) (recur)))))))))

(defn read-quoted-csv-field [rdr]
  (let [sb (StringBuilder.)]
    (.read rdr) ;; the initial " character
    (loop []
      (let [i (.read rdr)]
        (if (neg? i)
          (str sb)
          (let [c (char i)]
            (cond
             (= c \\) (do (.append sb (char (.read rdr))) (recur))
             (= c \") (do (let [i (.read rdr)]
                            (when (pos? i)
                              (let [c (char i)]
                                (when (or (= c \newline)
                                          (= c \return))
                                  (.unread rdr i)))))
                          (str sb))
             :else    (do (.append sb c) (recur)))))))))

(defn read-csv-field [rdr]
  (let [i (.read rdr)
        c (char i)]
    (.unread rdr i)
    (if (= c \")
      (read-quoted-csv-field rdr)
      (read-unquoted-csv-field rdr))))

(defn read-csv-line [rdr]
  {:pre [(instance? PushbackReader rdr)]}
  (loop [fields []]
    (let [i (.read rdr)]
      (if (neg? i)
        fields
        (let [c (char i)]
          (if (or (= c \newline)
                  (and (= c \return)
                       (= \newline (char (.read rdr)))))
            fields
            (do (.unread rdr i)
                (recur (conj fields (read-csv-field rdr))))))))))

(defn read-csv-seq [rdr]
  (let [fields (read-csv-line rdr)]
    (when (seq fields)
      (lazy-seq
       (cons fields
             (read-csv-seq rdr))))))

(defn csv-seq [rdr]
  {:pre [(instance? Reader rdr)]}
  (let [rdr (PushbackReader. rdr)]
    (read-csv-seq rdr)))
