(ns forum.views.login)

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
  {:page "login"
   :css (list "/css/login.css")
   :js (list "/js/jquery.md5.js"
             "/js/login.js")
   :title "login"
   :body (generate-body)})
