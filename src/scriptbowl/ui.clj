(ns scriptbowl.ui
  (:import (javax.swing JFrame JPanel JTextField JButton JLabel)
           (java.awt.event ActionListener)
           (java.awt GridLayout))
  (:require [scriptbowl.main :as main]))

(defn -main []
  (let [frame (JFrame. "Super Ultra Stock Shredder 2000, 3D Edition")
        ticker-text (JTextField.)
        results (JLabel.)
        button (JButton. "Lookup")]
    (.addActionListener button
                        (reify ActionListener
                          (actionPerformed [this evt]
                            (.setText results (prn-str
                                               (main/ticker-for (.getText ticker-text)))))))
    (doto frame
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (.setLayout (GridLayout. 2 2 3 3))
      (.add ticker-text)
      (.add button)
      (.add results)
      (.setSize 650 100)
      (.setVisible true))))