(ns db.db
  (:require [monger.core :as mg])
  (:import  [com.mongodb WriteConcern]))

(defn init-db
  []
  (do
    ;; (mg/connect!)
    ;; (mg/set-db! (monger.core/get-db "test"))
    ;; (mg/set-default-write-concern! WriteConcern/SAFE)
    (print "Connected to mongodb\n")))
