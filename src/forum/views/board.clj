(ns forum.views.board
  (:require [forum.db :as db]
            [forum.blocks.threads :as threads]))

(defn generate-body
  [query session]
  (let [page (max (or (:p query) 1) 1)
        match (or (:search query) ".*")
        limit (or (-> :display :threads session) 20)
        offset (* (- page 1) limit)
        disp-threads (db/find-threads match limit offset (:uid session))]
     (threads/thread-list disp-threads)))

(defn generate
  [query session]
  {:page "forum"
   :title "LOL Bros, LOL!"
   :js (list "/js/board.js")
   :body [:div#page
          [:input#search {:type "text"}]
          (generate-body query session)]})

(defn generate-search
  [query session]
  {:body (generate-body query session)})
