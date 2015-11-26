(ns shorturl.html
  (:require [hiccup.form :refer [form-to label text-field submit-button]]
            [hiccup.page :refer [html5 include-css]]))

(defn- create-form []
  [:div.shorten-form (form-to [:post "/new"]
                              (label :url "URL:")
                              (text-field :url)
                              (submit-button "Shorten!"))])

(defn- create-url-list [url-map]
  [:div.all-urls
   [:ul
    (for [[short url] url-map]
      [:li [:a {:href (str "/" short)} short]
       " -> "
       [:a {:href url} url]])]])

(defn index [url-map]
  (html5
    [:html
      {:lang "en"}
      [:head
        (include-css "styles/shorturl.css")
        [:title "Shorturl"]
      [:body
        [:div
          [:h1 "Welcome to Shorturl"]
          (create-form)
          (create-url-list url-map)]]]]))