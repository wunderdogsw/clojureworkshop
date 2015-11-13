(ns shorturl.core
  (:require [pandect.algo.sha1 :refer [sha1]]))

(defn index []
  "Hallo Wunderdog!")

(defn link->sha [link]
  (sha1 (.getBytes link)))