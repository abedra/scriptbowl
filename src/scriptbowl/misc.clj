(ns scriptbowl.misc)

(use 'scriptbowl.main)

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

(in-ns 'scriptbowl.main)

(js/alert (high-for "A"))

(in-ns 'user)

(ns dom.test
  (:require [clojure.browser.dom :as dom]
            [goog.events :as event]
            [scriptbowl.main :as sb]))

(dom/append (dom/get-element "results")
            (dom/element [:ul [:li (pr-str (sb/ticker-for "MSFT"))]]))

(dom/remove-children "results")

(defn do-tracker-clicked []
  (dom/append (dom/get-element "results")
              (dom/element [:li (pr-str (sb/ticker-for (.value (dom/get-element "ticker"))))])))

(event/listen (dom/get-element :ticker-button)
              "click"
              do-tracker-clicked)

(dom/append (dom/get-element "results")
            (dom/html->dom "<h4>Test</h4>"))
