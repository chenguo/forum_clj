(ns forum.blocks.threads
  (:require [forum.misc :as misc]
            [forum.user-cache :as users]))

(defn- view-thread
  [thread]
  [:div.thread {:id (:tid thread)}
   [:div.span6 (misc/thread-link (:title thread) (:tid thread))]
   [:div.span1 (:posts thread)]
   [:div.span1 (:views thread)]
   [:div.span1 (users/get-user-name (:uid thread))]
   [:div.span1 (users/get-user-name (:last_uid thread))]])

(defn thread-list
  [threads]
  [:div.threadlist.box.container
   (map view-thread threads)])
