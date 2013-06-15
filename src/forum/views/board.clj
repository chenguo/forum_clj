(ns forum.views.board
  (:require [forum.db :as db]
            [forum.defines :as def]
            [forum.blocks.threads :as threads]))

(defn- generate-body
  [query session db-query]
  (let [;page (max (or (:p query) 1) 1)
        limit (or (-> :display :threads session) def/thr-visible)
        ;offset (* (- page 1) limit)
        offset 0
        disp-threads (db/find-threads db-query limit offset (:uid session))]
     (threads/thread-list disp-threads)))

(defn generate
  [query session]
  {:page def/page-forum
   :title def/page-forum-title
   :js (list def/js-board)
   :body [:div#page
          [:input#search {:type "text"}]
          (generate-body query session {})]})

(defn generate-search
  [query session]
  (let [query (db/build-query-regex (:search query))]
    {:body (generate-body query session {:title query})}))
