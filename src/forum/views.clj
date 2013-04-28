(ns forum.views
  (:use [hiccup core page])
  (:require [forum.views.login :as login]
            [forum.session :as session]
            ))

(def base-head
  [:head
   (include-css "/css/forum.css")
   (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js")])

(def base-body
  [:body
   [:div.banner "LOL Bros LOL"]])

;; (defn view-login
;;   "Render login page."
;;   [ajax?]
;;   (if ajax?
;;     false
;;     (let [[head body] (login/render)]
;;       (println "send login page")
;;       {:body (html5
;;               (conj head (base-head))
;;               (conj (base-body) body))
;;        :session {}})))


(def login login/generate)
(defn fake
  [query]
  {:page "forum"
   :title "forum"
   :body "hi"})


(defn construct-head
  [js-files css-files title]
  (let [js (map include-js js-files)
        css (map include-css css-files)]
    (conj base-head [:title title] css js)))

(defn construct-body
  [body]
  (conj base-body body))

(defn render
  [{js :js css :css title :title body :body}]
  (html5
   (construct-head js css title)
   (construct-body body)))



