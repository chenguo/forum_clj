(ns forum.views.login
  (:use [forum.defines :as def]))

(defn- generate-body
  "Forum index body"
  []
  [:div#page
   [:div.login_box
    [:form.login_form.box
     [:label#notification]
     [:fieldset
      [:input.field {:type "text" :placeholder "username" :name "username"}]
      [:br]
      [:input.field {:type "password" :placeholder "password" :name "password"}]
      [:label.text-center
       [:input#remember {:type "checkbox"}]
       "remember me"]
      [:button#signin.btn.btn-primary {:type "button"} "sign in"]]]]])

(defn generate
  "Render login page"
  [query session]
  {:page def/page-login
   :css (list def/css-login)
   :js (list def/js-md5
             def/js-login)
   :title def/page-login-title
   :body (generate-body)})
