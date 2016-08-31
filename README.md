# avenue

[![Clojars Project](https://img.shields.io/clojars/v/avenue.svg)](https://clojars.org/avenue)

A super simple routing library, written in ClojureScript.

## Overview

This comes later.

### Usage

Using `avenue` is pretty simple.

Add the following to your `project.clj`

`[avenue "X.X.X"]` where `X.X.X` is the current version.

Then, require it in a namespace:

```clj
(ns my-ns.core
  (:require [avenue.core :as avenue]))
```

Now you need to set up `avenue`. First, let's add some routes and set our config:

```clj
(ns ui.routes
  (:require [avenue.core :as avenue]))

(avenue/add-route :kitty
  :match #"/"
  :ctor kitty/index-ctor
  :opts [:kitty "meow"])

(avenue/add-route :doggy
  :match #"/woof"
  :ctor doggy/index-ctor
  :opts ["Dog goes" "woof"])

;; set where we want to mount our pages
(avenue/set-mount-point! "page_mount")
```


Now, we can call / render our routes!

```clj
;; go to a route via a URL or key
(avenue/go! "mygreatapp.com/woof")

(avenue/go! :kitty)

;; reload at a URL or key
(avenue/reload! "mygreatapp.com")

(avenue/reload! :doggy)

;; check if a URL matches a current route
(avenue/matches-current-route? "mygreatapp.com/dog/stuff")

;; get the current route (this returns a route map)
(avenue/current-route)
```

### Caveats / Gotchas

Remember to reload your routes when your app starts or when Figwheel reloads. This can cause some problems with pages not updating if their code has changed.

```clj
(defn ^:export reload! []
  (avenue/refresh!)
  (my-routes/refresh!))
```

### Emojis in commits?

🌱 → new feature

🍂 → cleanup of some sort

🍁 → fixed a bug or error

## License

Copyright © 2014 Chris Etheridge
