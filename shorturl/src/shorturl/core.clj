(ns shorturl.core
  (:require [pandect.algo.sha1 :refer [sha1]]
            [shorturl.db :as db]))

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

(defn retrieve-url [short]
  (get @urls short))

(defn retrieve-url-from-db [short]
  (let [results (db/find-by-short short)]
    (when-not (empty? results)
      (:full_url (first results)))))

(defn store-url-to-db [short url]
  (if-not (retrieve-url-from-db short)
    (db/insert-url short url)))

