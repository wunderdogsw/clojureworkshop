(ns shorturl.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [shorturl.handler :refer :all]))

(defn new-post-request [url]
  (mock/request :post "/new" {:url url}))

(deftest test-app
  ;(testing "main route"
  ;  (let [response (app (mock/request :get "/"))]
  ;    (is (= (:status response) 200))
  ;    (is (= (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "input new valid URL"
    (let [response (app (new-post-request "http://wun.dog"))]
      (is (= (:status response) 200))))

  (testing "input new valid URL"
    (let [response (app (new-post-request "gaa"))]
      (is (= (:status response) 400))
      (is (= (:body response) "Invalid URL"))))

  ;TODO success case?

  (testing "try to retrieve URL with non-existing short string"
    (let [response (app (mock/request :get "/gaa"))]
      (is (= (:status response) 404))
      (is (= (:body response) "No URL found with 'gaa'")))))
