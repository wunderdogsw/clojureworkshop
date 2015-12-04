# Clojure workshop - the theory


## Clojure
* A modern Lisp dialect (2007), designed by Rich Hickey
* Uses the Java Virtual Machine as runtime platform
* Promotes a Functional Programming style
* Designed for Concurrency

* Clojure is not a pure functional language (like Haskell)
* Emphasis on immutable data structures: list, vector, set, map...
* Emphasis on recursion rather than looping

* Clojure provides easy access to the Java frameworks, with optional type hints and type inference, to ensure that calls to Java can avoid reflection.








## About the logistics of code

* Code is organized into namespaces containing related functions
* One namespace equals one file in the file system
* Source is read from top to bottom and you must define or declare value or function before using it
* Names are usually lowercase and words are separated with dash `this-is-an-example`
* If there is a dash in namespace name it translates to low dash in file name eg. `(ns my-namespace)` => `my_namespace.clj`


## Use REPL and Leiningen
* Read Eval Print Loop or REPL is a program to define and run code dynamically
* Great for testing out things and coding with fast response loop
* Leiningen is a build system like Maven but better





## Some code examples
```clojure
; Comments start with semicolons.

; Clojure is written in "forms", which are just
; lists of things inside parentheses, separated by whitespace.
;
; The clojure reader assumes that the first thing is a
; function or macro to call, and the rest are arguments.

; The first call in a file should be ns, to set the namespace
(ns learnclojure)

; More basic examples:

; str will create a string out of all its arguments
(str "Hello" " " "World") ; => "Hello World"

; Math is straightforward
(+ 1 1) ; => 2
(- 2 1) ; => 1
(* 1 2) ; => 2
(/ 2 1) ; => 2
(/ 1 2) ; => 1/2

; Equality is =
(= 1 1) ; => true
(= 2 1) ; => false

; Nesting forms works as you expect
(+ 1 (- 3 2)) ; = 1 + (3 - 2) => 2

; Types
;;;;;;;;;;;;;

; Clojure uses Java's object types for booleans, strings and numbers.
; Use `class` to inspect them.
(class 1) ; Integer literals are java.lang.Long by default
(class 1.); Float literals are java.lang.Double
(class ""); Strings always double-quoted, and are java.lang.String
(class false) ; Booleans are java.lang.Boolean
(class nil); The "null" value is called nil

; If you want to create a literal list of data, use ' to stop it from
; being evaluated
'(+ 1 2) ; => (+ 1 2)
; (shorthand for (quote (+ 1 2)))

; You can eval a quoted list
(eval '(+ 1 2)) ; => 3

; Types
;;;;;;;;;;;;;

; Clojure uses Java's object types for booleans, strings and numbers.
; Use `class` to inspect them.
(class 1) ; Integer literals are java.lang.Long by default
(class 1.); Float literals are java.lang.Double
(class ""); Strings always double-quoted, and are java.lang.String
(class false) ; Booleans are java.lang.Boolean
(class nil); The "null" value is called nil

; If you want to create a literal list of data, use ' to stop it from
; being evaluated
'(+ 1 2) ; => (+ 1 2)
; (shorthand for (quote (+ 1 2)))

; You can eval a quoted list
(eval '(+ 1 2)) ; => 3

; Functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;
(fn [] "Hello World")
; => #<user$eval2832$fn__2833 user$eval2832$fn__2833@d265d28>

((fn [] "Hello World"))
; => "Hello World"

; You can define a value using def
(def x 1)
x ; => 1

; You cand define a function using defn
(defn hello-world [] "Hello World")

; The [] is the list of arguments for the function.
(defn hello [name]
  (str "Hello " name))
(hello "Steve") ; => "Hello Steve"

; You can have multi-variadic functions, too
(defn hello2
  ([] "Hello World")
  ([name] (str "Hello " name)))
(hello2 "Jake") ; => "Hello Jake"
(hello2) ; => "Hello World"

; Functions can pack extra arguments up in a seq for you
(defn count-args [& args]
  (str "You passed " (count args) " args: " args))
(count-args 1 2 3) ; => "You passed 3 args: (1 2 3)"

; You can mix regular and packed arguments
(defn hello-count [name & args]
  (str "Hello " name ", you passed " (count args) " extra args"))
(hello-count "Finn" 1 2 3)
; => "Hello Finn, you passed 3 extra args"

; Function description is given as string after parameters
(defn my-reverse [input]
  "This function reverses given input."
  (reverse input))


; Maps
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Maps can use any hashable type as a key, but usually keywords are best
; Keywords are like strings with some efficiency bonuses
(class :a) ; => clojure.lang.Keyword

(def stringmap {"a" 1, "b" 2, "c" 3})
stringmap  ; => {"a" 1, "b" 2, "c" 3}

(def keymap {:a 1, :b 2, :c 3})
keymap ; => {:a 1, :c 3, :b 2}

; By the way, commas are always treated as whitespace and do nothing.

; Retrieve a value from a map by calling it as a function
(stringmap "a") ; => 1
(keymap :a) ; => 1

; Keywords can be used to retrieve their value from a map, too!
(:b keymap) ; => 2

; Don't try this with strings.
;("a" stringmap)
; => Exception: java.lang.String cannot be cast to clojure.lang.IFn

; Retrieving a non-present key returns nil
(stringmap "d") ; => nil

; Or you can give a default for non-present keys
(stringmap "d" :not-there) ;=> :not-there

; Use assoc to add new keys to hash-maps
(assoc keymap :d 4)
; => {:a 1, :b 2, :c 3, :d 4}

; But remember, clojure types are immutable!
keymap ; => {:a 1, :b 2, :c 3}

; Use dissoc to remove keys
(dissoc keymap :a :b) ; => {:c 3}

; Sets
;;;;;;

(class #{1 2 3}) ; => clojure.lang.PersistentHashSet
(set [1 2 3 1 2 3 3 2 1 3 2 1]) ; => #{1 2 3}


```

## Let's get on with the tutorial!

### Steps 0-2 - Project creation

* Leiningen does the heavy lifting for you
* It fetches Clojure and other dependencies and you can use it to start a new project
* Project and its dependencies are defined in `project.clj` file (no XML, yay!)




### Step 3 - Running things
* Our example web application stack goes like this:
   * Jetty
   * ring - HTTP server abstraction
   * Compojure - A concise routing library for Ring/Clojure




### Step 4 - Namespaces
* Using other namespaces
   * `(:use some.namespace)` imports all public functions from that namespace
   * `(:require [some.namespace])` does not import anything by default but you can use
      * `(:require [some.namespace :refer [a-function b-function]])` to import specific functions
      * `(:require [some.namepsace :refer :all])` to import all
   * You can also name them for easier access `(:require [some.namespace :as cool])` and then use its functions in code `(cool/a-function)`




### Step 5 - Tests
* There are two widely used testing libraries `clojure.test` and `midje.sweet`
   * `clojure.test` uses the familiar xUnit syntax of comparing expected to actual
```clojure
(deftest example
         (testing "test case 1"
                  (let [expected "aa"
                        actual "aa"]
                    (is (= expected actual)))))
```

* Midje provides a migration path from clojure.test to a more flexible, readable, abstract, and gracious style of testing
```clojure
(facts "example"
       (let [expected "aa"
             actual "aa"]
         (fact actual => expected)))
```




### Step 6 - Java interop
* By convention transformation functions are named like `input->output`
* Pretty good style guide can be found from here: https://github.com/bbatsov/clojure-style-guide
* Java interop
```clojure
; Method call
(.toUpperCase "dog")
; => "DOG"

; Access a field
(.-x (java.awt.Point. 1 2))
; => 1

; .. macro expands into a member access (.) of the first member on the first argument, followed by the next member on the result, etc. For instance:
(.. System (getProperties) (get "os.name"))
; => "Mac OS X" 
```

### Step 7 - Ring middleware wrapping
* `wrap-defaults` provides sane defaults for running ring service
* In this tutorial we disable the CSRF check to make the development more easy
* Ring plugins are handlers wrapped around your route definitions
* Each handler does whatever it is designed to do and then pass the call forward
```clojure
; Behold, our middleware! Note that it's common to prefix our middleware name
; with "wrap-", since it surrounds any routes an other middleware "inside"
(defn wrap-log-request [handler]
  (fn [req] ; return handler function
    (println req) ; perform logging
    (handler req))) ; pass the request through to the inner handler

; We can attach our middleware directly to the main application handler. All
; requests/responses will be "filtered" through our logging handler.
(def app
  (-> app-routes
    wrap-log-request))
```

* Refresher on how to access data in a map and couple of new ways
```clojure
(def m {:a {:b 2}})
; => #'user/m

((m :a) :b)
; => 2

(let [inner-map (m :a)]
  (inner-map :b))
; => 2

(get-in m [:a :b])
; => 2
```

## Destructuring
* Anywhere names are bound, you can nest a vector or map to destructure a collection and bind only specific elements of the collection
```clojure
(def dist [p]
  (let [x (first p)
        y (second p)]
    (Math/sqrt (+ (* x x) (* y y)))))

(def dist [[x y]]
  (Math/sqrt (+ (* x x) (* y y))))
```

# Sources
https://github.com/adambard/learnxinyminutes-docs/blob/master/clojure.html.markdown

https://soft.vub.ac.be/~tvcutsem/invokedynamic/presentations/Clojure-intro.pdf

http://clojure.org/

https://en.wikipedia.org/wiki/Clojure

http://kendru.github.io/restful-clojure/2014/03/01/building-out-the-web-service-restful-clojure-part-3/