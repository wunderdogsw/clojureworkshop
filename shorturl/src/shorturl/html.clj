(ns shorturl.html
  (:require [hiccup.form :refer [form-to label text-field submit-button]]
            [hiccup.page :refer [html5 include-css]]))

(defn- create-form [invalidurl]
  [:div.shorten-form (form-to [:post "/new"]
                              (label :url "URL:")
                              (text-field :url invalidurl)
                              (submit-button "Shorten!"))])

(defn- create-url-list [url-map]
  [:div.all-urls
   [:ul
    (for [[short url] url-map]
      [:li [:a {:href (str "/" short)} short]
       " -> "
       [:a {:href url} url]])]])

(defn index [url-map invalidurl]
  (html5
    [:html
      {:lang "en"}
      [:head
        (include-css "styles/shorturl.css")
        [:title "Shorturl"]
      [:body
        [:div
          [:h1 "Welcome to Shorturl"]
          (when invalidurl [:p.error "URL should start with http:// or https:// and contain at least two parts separated by a dot."])
          (create-form invalidurl)
          (create-url-list url-map)]]]]))