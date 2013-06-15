(ns forum.views.thread
  (:require [forum.db :as db]
            [forum.defines :as def]
            [forum.blocks.posts :as posts]))

(defn- generate-body
  [query session db-query]
  (let [;page (max (or (:p query) 1) 1)
        ;match (or (:search query) ".*")
        limit (or (-> :display :posts session) def/posts-visible)
        offset 0
        disp-posts (db/find-posts db-query limit offset (:uid session))]
    (posts/post-list disp-posts)))

(defn generate
  [query session]
  {:page def/page-thread
   :title def/page-thread-title
   :body [:div#page
          (generate-body query session {:tid (:t query)})]})
