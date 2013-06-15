(ns forum.user-cache
  (:require [forum.db :as db]))

(def users (atom {}))

(defn- set-user-info
  [id info]
  (swap! users (fn [old-users] (assoc old-users id info))))

(defn- get-user-from-db
  "Get user information from database"
  [id]
  (let [user-info (db/get-user-info id)]
    (set-user-info id user-info)
    user-info))

(defn get-user-info
  [id]
  (or (get @users id)
      (get-user-from-db id)))

(defn get-user-name
  [id]
  (:name (get-user-info id)))

(defn write-user-info
  "Write user information to cache"
  [id info]
  ; if user info cached, update and write to db
  ; if not cached, retrieve, update, cache, and write to db
)
