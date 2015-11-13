(ns shorturl.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [shorturl.core :as c]))

(defroutes app-routes
  (GET "/" [] (c/index))
  (POST "/new" request
    (let [params (:params request)
          url (:url params)]
      (if (c/valid-url? url)
        (let [result (c/create-short-url url)]
          (do
            (c/store-url result url)
            result))
        {:status 400 :headers {} :body "Invalid URL"})))
  (GET "/:short" [short]
    (if-let [url (c/retrieve-url short)]
      url
      {:status 404 :headers {} :body (str "No URL found with '" short "'")}))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes
                 (assoc-in site-defaults
                           [:security :anti-forgery]
                           false)))
