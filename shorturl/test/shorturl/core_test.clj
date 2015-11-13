(ns shorturl.core-test
  (:use midje.sweet)
  (:require [shorturl.core :refer :all]))

(fact "index returns a greeting"
      (index) => "Hallo Wunderdog!")
