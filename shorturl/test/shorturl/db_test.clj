(ns shorturl.db-test
  (:require [shorturl.db :as db]
            [clojure.test :refer :all]
            [korma.db :as kdb]))

(defn with-rollback
  "Test fixture for executing a test inside a database transaction
  that rolls back at the end so that database tests can remain isolated"
  [test-fn]
  (kdb/transaction
    (test-fn)
    (kdb/rollback)))

(use-fixtures :each with-rollback)

(deftest store-and-retrieve-shorturl
  (testing "Create URL row. Tests also find by short URL."
    (let [count-orig (count (db/find-by-short "test"))]
      (db/insert-url "test" "http://goo.gl")
      (is (= (inc count-orig) (count (db/find-by-short "test")))))))