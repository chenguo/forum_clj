(ns forum.session
  (:require [forum.db :as db]))

(defn user-check
  "Check if user in session var is valid"
  [user secret pw session]
  (let [{db-secret :secret db-pw :pw} (db/login-info user)]
    (println "user" user "secret" secret "pw" pw "db-secret" db-secret "db-pw" db-pw)
    (if (and db-pw db-secret
             (or (= 0 (compare secret db-secret))
                 (= 0 (compare pw db-pw))))
      (assoc session :user user :secret db-secret)
      (dissoc session :user :secret))))

(defn session-check
  "Check if session is associated with a user"
  [session query]
  (println "session:" session "query:" query)
  (if (and (:user session) (:secret session))
    (user-check (:user session) (:secret session) nil session)
    (user-check (:login-user query) nil (:login-pw query) session)))
