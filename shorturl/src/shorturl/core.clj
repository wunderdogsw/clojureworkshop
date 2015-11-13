(ns shorturl.core
  (:require [pandect.algo.sha1 :refer [sha1]]))

(def urls (atom {}))

(def url-regex
  "Adaptation from http://stackoverflow.com/a/15518889/1790621"
  (re-pattern
    "^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))

(defn index []
  "Hallo Wunderdog!")

(defn- link->sha [url]
  (sha1 (.getBytes url)))

(defn create-short-url [url]
  (subs (link->sha url) 0 7))

(defn valid-url? [input]
  (not (nil? (re-find url-regex input))))

(defn store-url [short url]
  (swap! urls #(assoc % short url)))