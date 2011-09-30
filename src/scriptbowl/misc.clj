(ns scriptbowl.misc)

(use 'scriptbowl.core)

(read-data)
(first (read-data))
(take 5 (read-data))
(pprint (take 5 (read-data)))

(pprint (take 5 (ticker)))

(ticker-for "ORCL")
(high-for "ORCL")

(use 'cljs.closure)
(def opts {:output-to "main.js"
           :output-dir "out"})
(build "src" opts)

(do (require '[cljs.repl :as repl])
    (require '[cljs.repl.browser :as browser])
    (def env (browser/repl-env))
    (repl/repl env))

(js/alert "Hello World")

(js/alert (high-for "A"))

(ns dom.test
  (:require [clojure.browser.dom :as dom]
            [goog.events :as event]
            [scriptbowl.main :as sb]))

(dom/remove-children "results")

(defn do-tracker-clicked []
  (dom/append (dom/get-element "results")
              (dom/element [:li (pr-str (sb/ticker-for (.value (dom/get-element "ticker"))))])))

(event/listen (dom/get-element :ticker-button)
              "click"
              do-tracker-clicked)





(dom/append (dom/get-element "results")
            (dom/html->dom "<h4>Test</h4>"))



(in-ns 'scriptbowl.main)

