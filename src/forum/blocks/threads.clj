(ns forum.blocks.threads
  (:require [forum.misc :as misc]))

(defn- view-thread
  [thread]
  [:div.thread {:id (:tid thread)}
   [:div.span6 (misc/thread-link (:title thread) (:tid thread))]
   [:div.span1 (:posts thread)]
   [:div.span1 (:views thread)]
   [:div.span1 (:uid thread)]
   [:div.span1 (:last_uid thread)]])

(defn thread-list
  [threads]
  [:div.threadlist.box.container
   (map view-thread threads)])
