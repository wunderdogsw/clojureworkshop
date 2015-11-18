# Clojure workshop

## Preparation

### Requirements
TODO
Java JDK (at least version 6) http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
Leiningen http://leiningen.org/

#### IDEs + extensions
* IntelliJ IDEA + Cursive https://cursiveclojure.com/
* Eclipse + Counterclockwise http://doc.ccw-ide.org/
* Emacs + CIDER https://github.com/clojure-emacs/cider
* Vim + fireplace https://github.com/tpope/vim-fireplace

#### Leiningen
Like Maven but better.

### Clojure documentation
* Guide and search for core functions and forms http://conj.io/
* Style guide https://github.com/bbatsov/clojure-style-guide

## Tutorial

### Step 0 - Clone the repo and go to beginning
    git clone https://<username>@bitbucket.org/wunderdogsw/clojureworkshop.git
    git checkout -b tutorial step_0
    rm -fr shorturl

TODO is that correct?

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
```
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
* Start a new midje autotest process in a terminal (yes, midje works without lein-auto)
```
lein midje :autotest
```

### Step 3 - Setup live editing and start server
* Setup remote REPL in `project.clj` by adding following key pair to `:ring´
```
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
```
    (use 'shorturl.handler :reload-all)
```
* Change current namespace in REPL
```
(in-ns 'shorturl.handler)
```
* Redefine your function and check your server response again

### Step 4 - Create a new namespace
* Create a file `core.clj` under `src/shorturl`
* Name the namespace at the beginning of the file
```
(ns shorturl.core)
```
* Move your greeting function from handler to core
    * Remember to require the new namespace in `handler` `[shorturl.core :as c]`
    * Fix the function call `(GET "/" [] (c/index))`
* Check that the server still works (also REPL might need a context reload)

### Step 5 - Add midje test for core
* Create a file `core_test.clj` under `test/shorturl`
    * Underscore in filename translates to a dash in namespace name
* Add following dependencies to the test namespace
```
(:use midje.sweet)
(:require [shorturl.core :refer :all])
```

* Create a test
```
(fact "index returns a greeting"
  (index) => "Hallo Wunderdog!")
```
* Check your autotest window

### Step 6 - Creating a sha1 from a string
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
        * Java interop Lesson 1 - using methods: `(.getBytes str)`

### Step 7 - Create a POST endpoint
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

### Step 8 - Validate link with regex
* Create test cases for URL validation function
* Define `re-pattern` in start of core that validates a string is an URL
    * Eg. `"^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"`
* Implement a function that validates given input
* Create test cases in `handler-test` for valid and invalid input
* Fix the endpoint to match new tests

### Step 9 - Return a short URL instead of full sha1
* Create test cases for a new function that returns a 7 character substring of the full value returned by sha1 creation function
* Create the actual function in `core`
* Mark sha1 creation function as private with `defn-` and remove its tests
* Replace the old function with the new one in `handler`

### Step 10 - Save links to an atom
* Define urls as an empty map atom
```
(def urls (atom {}))
```
* Create tests that check adding new key values to the map
    * Put `against-background` state reset before the test cases
```
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

### Step 11 - Create retrieval of a stored URL
* Create retreve-url function in `core` and matching tests
    * Hint: To get a value from an atom just deref it `(get @urls input-value)`
    * This returns `nil` if nothing is found
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
