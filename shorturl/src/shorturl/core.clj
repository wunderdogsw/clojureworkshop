(ns shorturl.core
  (:require [pandect.algo.sha1 :refer [sha1]]))

(def url-regex
  "Adaptation from http://stackoverflow.com/a/15518889/1790621"
  (re-pattern
    "^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))

(defn index []
  "Hallo Wunderdog!")

(defn link->sha [link]
  (sha1 (.getBytes link)))

(defn valid-url? [input]
  (not (nil? (re-find url-regex input))))
