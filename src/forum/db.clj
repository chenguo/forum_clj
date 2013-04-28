(ns forum.db
  (:use     [monger.operators])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import  [com.mongodb WriteConcern]))

(defn init-db
  [&]
  (print "Attempting to connect to mongoDB")
  (do
    (mg/connect! { :host "localhost" :port 27017 })
    (mg/set-db! (mg/get-db "forum"))
    ;(mg/set-default-write-concern! WriteConcern/SAFE)
    (println "Connected to mongodb")))

(defn login-info
  "Check given user credentials against DB"
  [user]
  (let [query {:name {$regex user $options "i"}}
        doc (mc/find-one-as-map "users" query ["password" "secret"])]
    {:pw (:password doc)
     :secret (:secret doc)}))
