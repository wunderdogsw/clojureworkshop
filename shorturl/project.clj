(defproject shorturl "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [pandect "0.5.4"]
                 [korma "0.4.2"]
                 [org.postgresql/postgresql "9.4-1205-jdbc42"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler shorturl.handler/app
         :nrepl {:start? true
                 :host "0.0.0.0"
                 :port 8888}}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [midje "1.6.0" :exclusions [org.clojure/clojure]]]
         :plugins [[lein-midje "3.1.3"]
                   [lein-kibit "0.1.2"]
                   [lein-auto "0.1.2"]]}})
