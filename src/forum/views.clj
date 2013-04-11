(ns forum.views
  (:use [hiccup core page]))

(defn index-title []
  [:title "LOL Bros, LOL!!"])

(defn header-common
  "Generate common header"
  [header-funcs]
  [:head
   (seq header-funcs)
   (include-css "/css/common.css")])

(defn index-header
  "Generate header of index page"
  []
  (header-common
   [(index-title)
    (include-css "/css/index.css")]))

(defn body-common
  "Common code for page body"
  []
  [:div.banner "LOL Bros LOL"])


(defn index-body
  "Forum index body"
  []
  [:body
   (body-common)
   [:div.login_box
    [:form.login_form.container
     [:input.field {:type "text" :size "16" :name "username"
                    :value "username" :maxlength "32"}]
     [:input.field {:type "password" :size "16" :name "password"
                    :value "password" :maxlength "32"}]
     [:input.button {:type "submit" :value "log in"}]
     [:br]]]])

(defn index-page
  "Generate index page"
  []
  (html5
   (index-header)
   (index-body)))
