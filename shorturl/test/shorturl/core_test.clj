(ns shorturl.core-test
  (:use midje.sweet)
  (:require [shorturl.core :refer :all]))

(fact "index returns a greeting"
      (index) => "Hallo Wunderdog!")

(facts "link->sha hashes with sha1"
       (fact (link->sha "http://wunderdog.fi") => "833e0acc54d46345120b2792de864bc4c623289b")
       (fact (link->sha "http://goo.gl") => "84704b78419ab3cecdce8251a702be1676e1622d"))

(facts "valid-url? accepts only valid URLs"
       (fact (valid-url? "http://wunderdog.fi") => true)
       (fact (valid-url? "https://wunder.dog") => true)
       (fact (valid-url? "gaa") => false)
       (fact (valid-url? "http://") => false))
