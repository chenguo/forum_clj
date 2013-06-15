(ns forum.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.operators :as mo]
            [monger.query :as mq])
  (:import  [com.mongodb WriteConcern]))

(defn- find-one
  [col query fields]
  (mc/find-one-as-map col query fields))

(defn- find-mult
  [col query fields]
  (mc/find-maps col query fields))

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
  (let [query {:name {mo/$regex (str "^" user "$")  mo/$options "i"}}
        fields ["password" "secret" "uid" "display"]
        doc (find-one "users" query fields)]
    {:pw (:password doc)
     :secret (:secret doc)
     :uid (:uid doc)
     :display (:display doc)}))

(defn find-threads
  "Get a list of threads"
  [query thr-limit offset uid]
  (mq/with-collection "threads"
    (mq/find query)
    (mq/fields [:tid :uid :create_time :title :orig_title :posts :views
                :last_uid :last_post (str "user_info." 2)])
    (mq/sort (sorted-map :last_post -1))
    (mq/limit thr-limit)
    (mq/skip offset)))

(defn find-posts
  "Get a list of posts"
  [query post-limit offset uid]
  (mq/with-collection "posts"
    (mq/find query)
    (mq/sort (sorted-map :time 1))
    (mq/limit post-limit)
    (mq/skip offset)))

(defn build-query-regex
  [query]
  {mo/$regex query})
