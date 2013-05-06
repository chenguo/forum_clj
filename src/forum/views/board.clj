(ns forum.views.board
  (:require [forum.db :as db]
            [forum.defines :as def]
            [forum.blocks.threads :as threads]))

(defn generate-body
  [query session]
  (let [page (max (or (:p query) 1) 1)
        match (or (:search query) ".*")
        limit (or (-> :display :threads session) def/thr-per-page)
        offset (* (- page 1) limit)
        disp-threads (db/find-threads match limit offset (:uid session))]
     (threads/thread-list disp-threads)))

(defn generate
  [query session]
  {:page def/page-forum
   :title def/page-forum-title
   :js (list def/js-board)
   :body [:div#page
          [:input#search {:type "text"}]
          (generate-body query session)]})

(defn generate-search
  [query session]
  {:body (generate-body query session)})
