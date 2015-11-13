(ns shorturl.core-test
  (:use midje.sweet)
  (:require [shorturl.core :refer :all]))

(fact "index returns a greeting"
      (index) => "Hallo Wunderdog!")

(facts "create-short-url"
       (fact (create-short-url "http://wunderdog.fi") => "833e0ac")
       (fact (create-short-url "http://goo.gl") => "84704b7"))

(facts "valid-url? accepts only valid URLs"
       (fact (valid-url? "http://wunderdog.fi") => true)
       (fact (valid-url? "https://wunder.dog") => true)
       (fact (valid-url? "gaa") => false)
       (fact (valid-url? "http://") => false))
