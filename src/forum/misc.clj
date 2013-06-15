(ns forum.misc
  (:require [clojure.data.json :as json]
            [forum.db :as db]))

(defn make-link
  [url text]
  [:a.thr-link {:href url} text])

(defn thread-link
  ([title tid] (make-link (str "/thread?t=" tid) title))
  ([title tid page] (make-link (str "/thread?t=" tid "&p=" page) title)))
