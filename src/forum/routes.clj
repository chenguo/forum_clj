(ns forum.routes
  (:use compojure.core
        [hiccup core page]
        [hiccup.middleware :only (wrap-base-url)])
  (:require [clojure.data.json :as json]
            [clojure.walk :as walk]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [forum.db :as db]
            [forum.session :as session]
            [forum.views :as view]))

(defn is-ajax?
  "Check if a request was AJAX"
  [req-headers]
  (println "headers:" req-headers "req-with:" (get req-headers "x-requested-with"))
  (let [req-type (or (get req-headers "x-requested-with") "")]
    (= 0 (compare (.toLowerCase req-type) "xmlhttprequest"))))

(defn resp-ajax
  [content session]
  {:headers {"Content-Type" "application/json"}
   :body (json/json-str (assoc content :body (html (:body content))))
   :session session})

(defn resp-http
  [content session]
  {:headers {"Content-Type" "text/html"}
   :body (view/render content)
   :session session})

(defn get-resp
  "Generate response to incoming request, based on route, query, and whether
   or not the request was AJAX"
  [session ajax? content]
  (println "ajax:" ajax?)
  (println "resp:" content)
  (if ajax?
    (resp-ajax content session)
    (resp-http content session)))

(defn render
  "Process incoming request. Serve login page if no user is logged in, otherwise
   serve requested page"
  [request view-handler]
  ;(println "request: " request)
  (let [session (:session request)
        query (walk/keywordize-keys (:query-params request))
        new-session (session/session-check session query)
        ajax? (is-ajax? (:headers request))
        respond (partial get-resp new-session ajax?)]
    (println "Old session:" session "new session:" new-session)
    (if (:user new-session)
      (respond (view-handler query))
      (respond (view/login)))))

(defroutes main-routes
  (route/resources "/")
  (GET "/" [:as request] (render request view/forum))
  (GET "/login" [:as request] (render view/login))
  (route/not-found "Page not found"))

(def forum
  (do (db/init-db)
      (-> (handler/site main-routes)
          wrap-base-url)))
