# Clojure workshop

## Preparation

### Requirements
Either use the provided virtual machine or install these tools
* Java JDK (at least version 6) http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* Leiningen http://leiningen.org/
* PostgreSQL http://www.postgresql.org/
    * `brew install postgresql`

#### IDEs + extensions
* IntelliJ IDEA + Cursive https://cursiveclojure.com/
* Eclipse + Counterclockwise http://doc.ccw-ide.org/
* Emacs + CIDER https://github.com/clojure-emacs/cider
* Vim + fireplace https://github.com/tpope/vim-fireplace

#### Leiningen
Like Maven but better.

### Clojure documentation
* Official site http://clojure.org/
* Cheatsheet and search for core functions and forms http://jafingerhut.github.io/cheatsheet/grimoire/cheatsheet-tiptip-cdocs-summary.html
* Style guide https://github.com/bbatsov/clojure-style-guide
* A categorised directory of libraries and tools for Clojure http://www.clojure-toolbox.com/



## About Clojure
* A modern Lisp dialect (2007), designed by Rich Hickey
* Uses the Java Virtual Machine as runtime platform
* Promotes a Functional Programming style
* Designed for Concurrency

* Clojure is not a pure functional language (like Haskell)
* Emphasis on immutable data structures: list, vector, set, map...
* Emphasis on recursion rather than looping
* Dynamically typed
* Lazy evaluation

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
* Leiningen is a build system like Maven but better. `lein help` shows possible commands




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

## Tutorial

## Steps 0-2

### Theory - Project creation

* Leiningen does the heavy lifting for you
* It fetches Clojure and other dependencies and you can use it to start a new project
* Project and its dependencies are defined in `project.clj` file (no XML, yay!)
* In `project.clj` you can have different profiles for example to have different dependencies for dev and normal builds


### Step 0 - Clone the repo and go to beginning
    git clone https://<username>@bitbucket.org/wunderdogsw/clojureworkshop.git
    cd clojureworkshop
    git checkout -b tutorial step_0
    rm -fr shorturl


### Step 1 - Create a new compojure project
    lein new compojure shorturl


### Step 2 - Create/import project to an IDE

#### IDEA
* Create new project
    * Select clojure
    - [ ] clojure 1.7 jar?
* Disable structural editing if you are not used to it
    * IDEA: Settings > Editor > General > Smart Keys > Use structural editing
    * Emacs: disable-paredit-mode
* Open project.clj
* Leiningen project not registered -> Add project
* Add midje as dev dependency and midje lein-midje, lein-kibit and lein-auto as dev plugin
```clojure
{:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                      [ring/ring-mock "0.3.0"]
                      [midje "1.6.0" :exclusions [org.clojure/clojure]]]
       :plugins [[lein-midje "3.1.3"]
                 [lein-kibit "0.1.2"]
                 [lein-auto "0.1.2"]]}}
```
* Project directory right click -> Create REPL for shorturl
* Project directory right click -> Run REPL for shorturl
* Play with REPL
* Start a new kibit autotest process in a terminal
```
lein auto kibit
```
* Start a new midje autotest process in a terminal
```
lein midje :autotest
```




## Step 3

### Theory - Running things
* Our example web application stack goes like this:
    * Jetty
    * ring - HTTP server abstraction
    * Compojure - A concise routing library for Ring/Clojure


### Practice - Setup live editing and start server
* Setup remote REPL in `project.clj` by adding following key pair to `:ring`
```clojure
:nrepl {:start? true
        :port 8888}
```
* Start server in terminal
```
lein ring server-headless
```
* Check your service from a browser http://localhost:3000
* Remote to server
    * Edit configurations...
    * Host: localhost
    * Port: 8888
    * Click OK to open new REPL
* In `shorturl.handler` create a function that returns a string and use that function in the default route
    * Check your server response in a browser
* Reload context in REPL (only needed when adding/removing definitions)
```clojure
(use 'shorturl.handler :reload-all)
```
* Change current namespace in REPL
```clojure
(in-ns 'shorturl.handler)
```
* Redefine your function and check your server response again




## Step 4

### Theory - Namespaces
* Using other namespaces
   * `(:use some.namespace)` imports all public functions from that namespace
   * `(:require [some.namespace])` does not import anything by default but you can use
      * `(:require [some.namespace :refer [a-function b-function]])` to import specific functions
      * `(:require [some.namepsace :refer :all])` to import all
   * You can also name them for easier access `(:require [some.namespace :as cool])` and then use its functions in code `(cool/a-function)`


### Practice - Create a new namespace
* Create a file `core.clj` under `src/shorturl`
* Name the namespace at the beginning of the file
```clojure
(ns shorturl.core)
```
* Move your greeting function from handler to core
    * Remember to require the new namespace in `handler` `[shorturl.core :as c]`
    * Fix the function call `(GET "/" [] (c/index))`
* Check that the server still works (also REPL might need a context reload)




## Step 5

### Theory - Testing
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


### Practice - Add midje test for core
* Create a file `core_test.clj` under `test/shorturl`
    * Underscore in filename translates to a dash in namespace name
* Add following dependencies to the test namespace
```clojure
(:use midje.sweet)
(:require [shorturl.core :refer :all])
```

* Create a test
```clojure
(fact "index returns a greeting"
  (index) => "Hallo Wunderdog!")
```
* Check your autotest window




## Step 6

### Theory - Creating a sha and Java interop
* In this tutorial we create the short URL by taking a sha1 of the full URL and cutting it to 7 letters
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


### Practice - Creating a sha1 from a string
* This is a good time to disable `testing "main route"` in `handler-test` if you haven't already done so
* Add `[pandect "0.5.4"]` to project dependecies
* To update dependencies restart server and reconnect
* Restart midje autotest process as well
* Create facts in `core-test`
    * Hint: by convention transforming something to something else is named like this: `input->output`
    * Hashing `http://wunderdog.fi` should return `833e0acc54d46345120b2792de864bc4c623289b`
    * Hashing `http://goo.gl` should return `84704b78419ab3cecdce8251a702be1676e1622d`
* Create a function that fulfills the tests
    * Require `sha1` function from `pandect.algo.sha1` in `core` namespace
    * Apply the function to the bytes of given string
        * Java interop using methods: `(.getBytes str)`




## Step 7

### Theory - Ring middleware wrapping
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


### Practice - Create a POST endpoint
* Disable anti-forgery from default settings in `app` definition
    * You can change things in a map easily with `assoc-in`
    * Restart the server
* Add a POST endpoint to `app-routes`
    * You can name the parameter after `"/new"` as request. It is a map.
    * Request parameters can be extracted with :params. This is a map as well.
    * `let` form is a good match for this kind of a task
    * Extracted URL parameter can then be passed on to your sha function
    * Curl your new endpoint
```
curl --data "url=http://wunderdog.fi" http://localhost:3000/new
```




## Step 8

### Theory - Predicates or functions that return a boolean
* Functions that test for a condition and return a boolean should have name that ends in a question mark
* For example `nil?`, `empty?` `valid?`


### Practice - Validate link with regex
* Create test cases for URL validation function
* Define `re-pattern` in start of core that validates a string is an URL
    * Eg. `"^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"`
* Implement a function that validates given input
* Create test cases in `handler-test` for valid and invalid input
* Fix the endpoint to match new tests




## Step 9

### Theory - Private functions
* Functions can be made private by using `defn-` instead of `defn`
* Private functions cannot be used through imports


### Practice - Return a short URL instead of full sha1
* Create test cases for a new function that returns a 7 character substring of the full value returned by sha1 creation function
* Create the actual function in `core`
* Mark sha1 creation function as private with `defn-` and remove its tests
* Replace the old function with the new one in `handler`




## Step 10

### Theory - Atoms
* Clojure offers a few different kinds of ways to access data
    * *Refs* are for Coordinated Synchronous access to "Many Identities".
    * *Atoms* are for Uncoordinated synchronous access to a single Identity.
    * *Agents* are for Uncoordinated asynchronous access to a single Identity.
    * *Vars* are for thread local isolated identities with a shared default value.

* Coordinated access is used when two Identities need to be changes together, the classic example being moving money from one bank account to another, it needs to either move completely or not at all.
* Uncoordinated access is used when only one Identity needs to update, this is a very common case.
* Synchronous access is where the call expects to wait until all the identities are settled before continuing.
* Asynchronous access is "fire and forget" and let the Identity reach its new state in its own time.

* Here is pretty good explanation of these and futures in Clojure: https://aphyr.com/posts/306-clojure-from-the-ground-up-state

* We will be using atom
```clojure
(def atomic-map (atom {}))
; => #'user/atomic-map

(deref atomic-map)
; => {}

; or with a shorthand
@atomic-map
; => {}

; A value in an atom can be changed with reset!
(reset! atomic-map {:a 1})
; => {:a 1}

; But generally to change a value in an atom you should use swap! that takes the atom and a function as parameters.
; The function is does that value change. Under the hood Clojure makes sure that the changes are synchronized.
(swap! atomic-map (fn [m] assoc m :b 2))
; => {:b 2, :a 1}

; You can use an anonymous function shorthand #(...).
; In #() % is used as the given parameter. You can have more than one parameter and then access them with %1 %2 etc.
(swap! atomic-map #(assoc % :b 3))
; => {:b 3, :a 1}
```


### Practice - Save links to an atom
* Define urls as an empty map atom
```clojure
(def urls (atom {}))
```
* Create tests that check adding new key values to the map
    * Put `against-background` state reset before the test cases
```clojure
(against-background [(before :contents (reset! urls {})
                             :after (reset! urls {}))]
  facts...)
```
* Implement a function that updates the `urls`
    * Atoms are updated with `swap!` that takes the name of the atom and a function that updates its value
    * Hint: use anonymous function with `assoc` as the update function
    * You can check the contents of an atom with deref function or @ shorthand (eg. `@urls`)
* In `handler` update the `/new` call so that it stores the value and then returns it
    * `do` function is used for imperative style one statement after another execution. It also implies side effects happening inside it.




## Step 11

### Theory - Retrieving from a map with String keys
```clojure
(def string-map {"a" 1 "b" 2}})
; => {"a" 1, "b" 2}

(get string-map "a")
; => 1

;or
(string-map "a")
1
```


### Practice - Create retrieval of a stored URL
* Create retreve-url function in `core` and matching tests
    * Hint: To access a value from an atom just deref it and then use it as you normally would `(get @urls input-value)`
    * Get from a map returns `nil` if nothing is found
* Add at least fail test case to `handler-test` and create new get endpoint that returns found URL or 404
* This is what it should look like
```
$ curl --data "url=https://wunder.dog" http://localhost:3000/new
12cee14
$ curl http://localhost:3000/12cee14
https://wunder.dog
$ curl http://localhost:3000/nothere
No URL found with 'test'
```




## Step 12

### Theory - Korma, tasty SQL for Clojure
* Korma is a domain specific language for Clojure that takes the pain out of working with your favorite RDBMS.
* http://sqlkorma.com/
* In this workshop we use only the very basic parts of this library.

```clojure
(defdb prod (postgres {:db "korma"
                       :user "db"
                       :password "dbpass"}))

(defentity address)
(defentity user
  (has-one address))

(select user
  (with address)
  (fields :firstName :lastName :address.state)
  (where {:email "korma@sqlkorma.com"}))
```

* We will use use-fixtures functionality from clojure.test to allow our tests to write to DB and then rollback after tests are run.
* Example code is in the guide.


### Practice - Store data in database
* Make sure your PostgreSQL instance is running
    * If you installed psql with brew you can get the start command by running `brew info postgres`
    * Something like `postgres -D /usr/local/var/postgres &`
* Create database and table in PostgreSQL
    * Database can be created eg. from command line: `createdb shorturl`
    * The script `db/create_user_and_table.postgresql` can be found with tag `step_12_preparation`. Here is its content:
```sql
DROP USER IF EXISTS demo;

CREATE USER demo WITH PASSWORD 'demopass';

DROP SEQUENCE IF EXISTS urls_row_num_seq;

CREATE SEQUENCE urls_row_num_seq CYCLE;

ALTER SEQUENCE urls_row_num_seq OWNER TO demo;

DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
        id int NOT NULL default nextval('urls_row_num_seq'),
        short varchar(32) NOT NULL,
        full_url varchar(2048) NOT NULL);

ALTER TABLE urls OWNER TO demo;
```

* Run the script with `psql -f db/create_user_and_table.postgresql shorturl`

* Create tests and implementation to a new namespace `shorturl.db`
    * Add `[korma "0.4.2"]` and `[org.postgresql/postgresql "9.4-1205-jdbc42"]` to project dependecies
    * In tests you can use the following code to rollback after tests that change the data
```clojure
(defn with-rollback
  "Test fixture for executing a test inside a database transaction
  that rolls back at the end so that database tests can remain isolated"
  [test-fn]
  (korma.db/transaction
    (test-fn)
    (korma.db/rollback)))

(use-fixtures :each with-rollback)
```

* In implementation you'll need at least `defdb` and `postgres` from `korma.db` and `defentity fields insert select where values` from `korma.core`
* Read http://sqlkorma.com/docs for examples




## Step 13

### Theory - Serving static files
* Compojure can serve static files from `resources/public`.
* Redirecting is very straightforward as well.


### Practice - Redirect and serve static files
* Make the short URL address to redirect when address is found. There is a `redirect` function in `ring.util.response` namespace.
* Then create `404.html` file in `resources/public` directory
* You can also create css file under `public` eg. `resources/public/styles/shorturl.css` and then link to it in HTML `<link rel="stylesheet" href="styles/shorturl.css"/>`
* Add `(route/resources "/")` to defroutes to map `resources/public` directory to root in URL
* Then modify `not-found` and failed short URL address to return appropriate HTML content
    * Hint: `slurp` reads different sources and returns the content as a string
    * Hint: You can use `clojure.java.io/resource` to access the static files




## Step 14

### Theory - Generating HTML
* There are a few different ways to produce HTML
   * Static files
   * Hiccup to generate HTML from Clojure lists
```clojure
[:ul {:class "groceries"}
 [:li "Apples"]
 [:li "Bananas"]
 [:li "Pears"]]
```
```html
<ul class="groceries">
  <li>Apples</li>
  <li>Bananas</li>
  <li>Pears</li>
</ul>
```

   * Selmer uses templates with placeholders
```html
<head><title>{{ title }}</title></head>
```
```clojure
(render-file "some.html"
         {:title "Selemer"})
```

   * Enlive uses pure HTML files and then selectors to fill in actual values
```html
<html>
  <head><title>Page Title</title></head>
  <body>
    <h1>Page Title</h1>
  </body>
</html>
```
```clojure
(html/deftemplate post-page "post.html" [values]
  [:title]         (html/content (:title values))
  [:h1]            (html/content (:title values)))

; Then calling with value map
(reduce str (post-page {:title "My title"}))
```
Produces
```clojure
<html>
  <head><title>My title</title></head>
  <body>
    <h1>My title</h1>
  </body>
</html>
```

#### Destructuring
* Anywhere names are bound, you can nest a vector or map to destructure a collection and bind only specific elements of the collection
```clojure
(def dist [p]
  (let [x (first p)
        y (second p)]
    (Math/sqrt (+ (* x x) (* y y)))))

(def dist2 [[x y]]
  (Math/sqrt (+ (* x x) (* y y))))
```

#### List comprehension
```clojure
(defn square-all [numbers]
  (for [num numbers]
    (* num num)))

(square-all [1 2 3])
; => (1 4 9)

(defn square-odd [numbers]
  (for [num numbers
        :when (odd? num)]
    (* num num)))

(square-odd [1 2 3 4 5 6 7 8 9])
; => (1 9 25 49 81)

; produce a seq of all pairs drawn from two vectors
(for [x ['a 'b 'c]
      y [1 2 3]]
  [x y])
;=> ([a 1] [a 2] [a 3] [b 1] [b 2] [b 3] [c 1] [c 2] [c 3])

; turn a map of key value pairs into list of strings
(defn key-values->strings [key-values]
  (for [[key value] key-values]
    (str key "=" value)))

(key-values->strings {"a" 1 "b" 2 "c" 3})
; => ("a=1" "b=2" "c=3")
```

* More examples http://blog.jayfields.com/2010/07/clojure-destructuring.html


### Practice - Dynamic HTML with Hiccup
* Add `[hiccup "1.0.5"]` to project dependecies
* Create a new namespace for html generation and change the `"/"` handler to use a function from there instead of the `c/index`
* First create a form with text input, action to `/new` and a submit button
    * Use functions in `hiccup.form` and `hiccup.page`
    * API can be found here http://weavejester.github.io/hiccup/index.html
* Next create a new function in `db` to retrieve all rows from `urls` and use that as an input to the function created in previous bullet
* With this input create a list of short urls to their respective full urls and make them clickable links
    * Put the list under the form




## Step 15

### Theory - Form validation
* There are nice libraries for form validation like https://github.com/leonardoborges/bouncer
* But we'll do poor man's version with get parameter
* Handy option to `if` is `when` if the else part can default `nil`
```clojure
(when true :yay)
; => :yay

(when false :nay)
; => nil


### Practice - Poor man's form validation
* Change the `/new` route to work with browsers
* On success redirect back to `"/"`
* On error redirect to `"/"` but with a get parameter that is the input
* Change the `"/"` to handle the URL parameter. You can access the parameter with destructuring `{{invalidurl :invalidurl} :params}`
* Add optional error element with some nice message, set the value into the input and add some styles for the error message
* Test can be checking that status is 302 and response headers Location contains the URL parameter




# All done!


# Bonus

## Recursion examples
```clojure

(defn my-map [f coll]
  (if (empty? coll) ; (seq coll) would be more idiomatic
    []
    (cons (f (first coll))
          (my-map f (rest coll)))))

(my-map inc [1 2 3 4])
; => (2 3 4 5)

; recur evaluates the exprs in order, then, in parallel, rebinds the bindings of
; the recursion point to the values of the exprs.
(defn print-seq [s]
  (when (seq s)
    (prn (first s))
    (recur (rest s)))) ; calls print-seq with the result of (rest s), but with rebinds so that the stack doesn't grow

(print-seq [1 2 3])
; => 1
; => 2
; => 3
; => nil
```

## Record
```clojure
;; Record
(defrecord Person [fname lname address])
-> user.Person

(defrecord Address [street city state zip])
-> user.Address

(def stu (Person. "Stu" "Halloway"
           (Address. "200 N Mangum"
                      "Durham"
                      "NC"
                      27701)))
-> #'user/stu

(:lname stu)
-> "Halloway"

(-> stu :address :city)
-> "Durham"

(assoc stu :fname "Stuart")
-> #:user.Person{:fname "Stuart", :lname "Halloway", :address #:user.Address{:street "200 N Mangum", :city "Durham", :state "NC", :zip 27701}}

(update-in stu [:address :zip] inc)
-> #:user.Person{:fname "Stu", :lname "Halloway", :address #:user.Address{:street "200 N Mangum", :city "Durham", :state "NC", :zip 27702}}

;; Destructuring a record

(defrecord Cat [age weight])

(def Jasper (Cat. 2 10))

(defn about-cat [{:keys [age weight]}]
  (str age " yrs old and " weight " lbs"))

(about-cat Jasper)
->"2 yrs old and 10 lbs"
```

## multimethod
```clojure
; functions to create different shapes (output is a map)
(defn rectangle [width height] {:Shape :Rectangle :width width :height height})
(defn circle [radius] {:Shape :Circle :radius radius})

; define some shapes
(def a-rectangle (rect 4 13))
(def a-circle (circle 12))

; defining our multimethod "area"
(defmulti area :Shape)

(defmethod area :Rectangle [rectangle]
    (* (:width rectangle) (:height rectangle)))

(defmethod area :Circle [circle]
    (* (. Math PI) (* (:radius circle) (:radius circle))))

(defmethod area :default [x] :oops)


; try it out
(area a-rectangle)
; => 52

(area a-circle)
; => 452.3893421169302

(area {})
; => :oops
```

## protocol
```clojure
; define a protocol with two abstract functions (bar is multi-arity function)
(defprotocol Proto
  (foo [this])
  (bar [this] [this x]))

; define a type that implements Proto
(deftype Foo [a b c]
  Proto
  (foo [this] a)
  (bar [this] b)
  (bar [this x] (+ c x)))

; define a instance of Foo
(def my-foo (Foo. 1 2 3))

(bar my-foo 42)
=> 45
```

# Sources

https://github.com/adambard/learnxinyminutes-docs/blob/master/clojure.html.markdown

https://soft.vub.ac.be/~tvcutsem/invokedynamic/presentations/Clojure-intro.pdf

http://clojure.org/

https://en.wikipedia.org/wiki/Clojure

http://kendru.github.io/restful-clojure/2014/03/01/building-out-the-web-service-restful-clojure-part-3/

http://stackoverflow.com/questions/9132346/clojure-differences-between-ref-var-agent-atom-with-examples

http://radar.oreilly.com/2014/03/choosing-a-templating-language-in-clojure.html