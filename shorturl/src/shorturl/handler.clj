(ns shorturl.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :refer [redirect]]
            [shorturl.core :as c]
            [shorturl.html :as h]
            [clojure.java.io :as io]))

(defn get-static-file-contents [path]
  (slurp (io/resource path)))

(defn not-found-content []
  (get-static-file-contents "public/404.html"))

(def not-found-response
  {:status 404
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body (not-found-content)})

(defroutes app-routes
  (GET "/" [] (h/index (c/get-all-urls)))
  (POST "/new" request
    (let [params (:params request)
          url (:url params)]
      (if (c/valid-url? url)
        (let [result (c/create-short-url url)]
          (c/store-url-to-db result url)
          result)
        {:status 400 :headers {} :body "Invalid URL"})))
  (GET "/:short" [short]
    (if-let [url (c/retrieve-url-from-db short)]
      (redirect url)
      not-found-response))
  (route/resources "/")
  (route/not-found (not-found-content)))

(def app
  (wrap-defaults app-routes
                 (assoc-in site-defaults
                           [:security :anti-forgery]
                           false)))
