(defproject forum "0.0.1"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "1.1.5"]
                 [enlive "1.0.1"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler forum.routes/forum
         :port 80})
