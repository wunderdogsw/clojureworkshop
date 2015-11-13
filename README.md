# CLojure workshop

## Preparation

### Requirements
TODO
Java JDK (at least version 6) http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
Leiningen http://leiningen.org/

#### IDEs + extensions
IntelliJ IDEA + Cursive https://cursiveclojure.com/
Eclipse + Counterclockwise http://doc.ccw-ide.org/
Emacs + CIDER https://github.com/clojure-emacs/cider
Vim + fireplace https://github.com/tpope/vim-fireplace

#### Leiningen

## Tutorial

### Step 0
First clone the repo:

    git clone https://<username>@bitbucket.org/wunderdogsw/clojureworkshop.git

### Step 1 - Create new compojure project
    lein new compojure shorturl

### Step 2 - Create/import project to an IDE

#### IDEA
* Create new project
  1. Select clojure
    - [ ] clojure 1.7 jar?
  2. Open project.clj
  3. Leiningen project not registered -> Add project
  4. Add midje as dev dependency
    {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [midje "1.6.0" :exclusions [org.clojure/clojure]]]
         :plugins [[lein-midje "3.1.3"]]}}
  5. Project directory right click -> Create REPL for shorturl
  6. Project directory right click -> Run REPL for shorturl
  7. Play with REPL

### Step 3 - Setup live editing and start server
* Setup remote REPL in `project.clj` by adding following key pair to `:ring´
    :nrepl {:start? true
            :port 8888}
* Start server in terminal
    lein ring server-headless
* Check your service from a browser http://localhost:3000
* Remote to server
  * Edit configurations...
  * Host: localhost
  * Port: 8888
  * Click OK to open new REPL
* In `shorturl.handler` create a function that returns a string and use that function in the default route
  * Check your server response in a browser
* Reload context in REPL (only needed when adding/removing stuff)
    (use 'shorturl.handler :reload-all)
* Change current namespace in REPL
    (in-ns 'shorturl.handler)
* Redefine your function and check your server response again

### Step 4 - Create a new namespace
* Create a file `core.clj` under `src/shorturl`
* Name the namespace at the beginning of the file
    (ns shorturl.core)
* Move your greeting function from handler to core
  * Remember to require the new namespace in `handler`
    [shorturl.core :as c]
  * Fix the function call
    (GET "/" [] (c/index))
* Check that the server still works (also REPL might need a context reload)

