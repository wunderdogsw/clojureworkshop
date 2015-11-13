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

### Step 1
Create new compojure project
    lein new compojure shorturl

### Step 2
Create/import project to IDE

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
  5. Project directory right click -> Create REPL
  6. Project directory right click -> Run REPL
