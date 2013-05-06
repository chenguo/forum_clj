(ns forum.views
  (:use [hiccup core page])
  (:require [forum.defines :as def]
            [forum.views.board :as board]
            [forum.views.login :as login]))

(def base-head [:head])

(def base-css
  (list def/css-common
        def/css-bootstrap))

(def base-js
  (list def/js-ajax
        def/js-bootstrap
        def/js-common))

(def base-body
  [:body
   [:div.banner def/banner]])

(def login login/generate)
(def board board/generate)
(def thread-search board/generate-search)
(def widgets {:sidebar (html [:div.sidebar])
              :chat (html [:div.chat])})

(defn- construct-head
  [js-files css-files title]
  (let [js (map include-js (concat base-js js-files))
        css (map include-css (concat base-css css-files))]
    (conj base-head [:title title] css js)))

(defn- construct-body
  [body]
  ;(println "body" body)
  (conj base-body body))

(defn render
  [{js :js css :css title :title body :body}]
  (html5
   (construct-head js css title)
   (construct-body body)))



