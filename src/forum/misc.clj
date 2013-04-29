(ns forum.misc
  (:require [clojure.data.json :as json]
            [forum.db :as db]))

(defn thread-search
  [query]
  (let [results (db/thread-search query)]
    (json/json-str results)))

(defn thread-link
  [title tid]
  [:a {:href (str "/posts?thread=" tid)} title])
