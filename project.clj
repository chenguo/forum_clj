(defproject forum "0.0.1"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.json "0.2.2"]
                 [org.mongodb/mongo-java-driver "2.11.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]
                 [com.novemberain/monger "1.3.0"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler forum.routes/forum
         :port 3000})
