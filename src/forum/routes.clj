(ns forum.routes
  (:use compojure.core
        forum.views)
        ;db.db)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(defroutes main-routes
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "Page not found"))

(def forum
  (-> ;(init-db)
      (handler/site main-routes)))
