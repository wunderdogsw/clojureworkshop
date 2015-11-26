(ns shorturl.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [shorturl.handler :refer :all]
            [shorturl.db :as db]
            [shorturl.db-test :as dbt]))

(use-fixtures :each dbt/with-rollback)

(defn new-post-request [url]
  (mock/request :post "/new" {:url url}))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Welcome"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))
      (is (.contains (:body response) "Not found"))))

  (testing "input new valid URL"
    (let [response (app (new-post-request "http://wun.dog"))]
      (is (= (:status response) 200))))

  (testing "input new invalid URL"
    (let [response (app (new-post-request "gaa"))]
      (is (= (:status response) 400))
      (is (= (:body response) "Invalid URL"))))

  (testing "redirect"
    (let [_ (db/insert-url "test" "http://goo.gl")
          response (app (mock/request :get "/test"))]
      (is (= (:status response) 302)))))