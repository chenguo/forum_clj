(ns forum.views
  (:use [hiccup core page])
  (:require [forum.views.board :as board]
            [forum.views.login :as login]))

(def base-head [:head])

(def base-css
  (list "/css/common.css"
        "/css/bootstrap.min.css"))

(def base-js
  (list "http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"
        "/js/bootstrap.min.js"
        "/js/common.js"))

(def base-body
  [:body
   [:div.banner "LOL Bros LOL"]])

(def login login/generate)
(def board board/generate)
(def thread-search board/generate-search)

(defn- construct-head
  [js-files css-files title]
  (let [js (map include-js (concat base-js js-files))
        css (map include-css (concat base-css css-files))]
    (conj base-head [:title title] css js)))

(defn- construct-body
  [body]
  (println "body" body)
  (conj base-body body))

(defn render
  [{js :js css :css title :title body :body}]
  (html5
   (construct-head js css title)
   (construct-body body)))



