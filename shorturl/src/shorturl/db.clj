(ns shorturl.db
  (:require [korma.db :refer [defdb postgres]]
            [korma.core :refer [defentity fields insert select where values]]))


(defdb possu (postgres {:db "shorturl"
                        :user "demo"
                        :password "demopass"}))

(declare urls)

(defentity urls
  (fields :short :full_url))

(defn find-by-short [short]
  (select urls
    (where {:short short})))

(defn insert-url [short full]
  (insert urls
    (values {:short short
             :full_url full})))

(defn get-all-urls []
  (into {}
        (map (juxt :short :full_url)
             (select urls))))
