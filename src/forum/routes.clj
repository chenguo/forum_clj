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
            [forum.misc :as misc]
            [forum.session :as session]
            [forum.views :as view]))

(defn- is-ajax?
  "Check if a request was AJAX"
  [req-headers]
  (let [req-type (or (get req-headers "x-requested-with") "")]
    (= 0 (compare (.toLowerCase req-type) "xmlhttprequest"))))

(defn- add-widgets
  [content widgets?]
  (if (or (= nil widgets?)
          (= 0 (compare "false" widgets?)))
    (assoc content :widgets view/widgets)
    content))

(defn- resp-header
  [ajax?]
  (if ajax?
    {"Content-Type" "application/json"}
    {"Content-Type" "text/html"}))

(defn- page-content
  [query session view-handler]
  (if (contains? session :user)
    (view-handler query session)
    (view/login query session)))

(defn- resp-body
  [query session ajax? view-handler]
  (let [content (page-content query session view-handler)
        widgets? (:widgets query)]
    (println "Widgets" widgets? "ajax" ajax?)
    (if ajax?
      (let [content (add-widgets content widgets?)
            content (assoc content :body (html (:body content)))]
        (json/json-str content))
      (view/render content))))

(defn- render
  "Process incoming request. Serve login page if no user is logged in, otherwise
   serve requested page"
  [request view-handler]
  (println "request: " request)
  (let [session (:session request)
        query (walk/keywordize-keys (:query-params request))
        new-session (session/session-check session query)
        ajax? (is-ajax? (:headers request))
        headers (resp-header ajax?)
        body (resp-body query new-session ajax? view-handler)]
    (println "Old session:" session "new session:" new-session)
    {:headers headers
     :body body
     :session new-session}))

(defroutes main-routes
  (route/resources "/")
  (GET "/" [:as request] (render request view/board))
  (GET "/thread-search" [:as request] (render request view/thread-search))
  (GET "/login" [:as request] (render request view/login))
  ;(GET "/thread-search" [query] (misc/thread-search query))
  (route/not-found "Page not found"))

(def forum
  (do (db/init-db)
      (-> (handler/site main-routes)
          wrap-base-url)))
