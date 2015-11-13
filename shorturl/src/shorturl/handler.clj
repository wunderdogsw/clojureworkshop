(ns shorturl.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [shorturl.core :as c]))

(defroutes app-routes
  (GET "/" [] (c/index))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
