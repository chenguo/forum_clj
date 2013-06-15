(ns forum.blocks.posts
  (:require [forum.misc :as misc]))

(defn- view-post
  [post]
  [:div.post.box.container {:id (:pid post)}
   (:time post)
   [:br]
   (:content post)])

(defn post-list
  [posts]
  [:div.postlist
   (map view-post posts)])