(ns avenue.core
  (:require [rum.core :as rum]
            [avenue.util :as util]))


(defonce *routes (atom {}))


(defonce *config (atom {}))


(defonce *current-route (atom nil))


(defn- location []
  js/document.location)


(defn path
  ([] (path (location)))
  ([loc] (.-pathname loc)))


(defn add-route [key & {:as args}]
  "Adds a route to the routes dictionary.
   Takes a unique `key`, and any arguments the component
   for that route requires."
  (swap! *routes assoc key (assoc args :key key)))


(defn current-route []
  (or (util/some-pred (fn [[k route]]
                        (re-matches (:match route) (path))) @*routes)
      (throw (ex-info (str "No route for " (.-href (location)))
                      {:url (.-href (location))}))))


(defn matches-current-route? [url]
  "Tests whether the given `url` matches a the current `route`."
  (let [[key route] (current-route)]
    (re-matches (:match route) url)))


(defn- route-for-location [loc]
  (util/some-pred (fn [[k route]]
                    (re-matches (:match route) loc)) @*routes))


(defn- render-route
  ([] (render-route (path)))
  ([loc]
   (let [[k route] (route-for-location loc)
         mount (:react-mount @*config)]
     (if mount
       (do
         (reset! *current-route route)
         (rum/mount ((:ctor route) (:args route)) (util/by-id mount)))
       (throw (ex-info "No :react-mount defined! Did you set one up?"
                       {:route route :key k}))))))


(defn go! [url]
  "Routes to the given `url`. Pushes to the `history state`, and
   uses URLs as a fall back."
  (if (exists? js/history.pushState)
    (do
      (js/history.pushState nil "" url)
      (render-route))
    (set! js/window.location url)))


(defn reload! [url]
  "Reloads at the given `url`. Replaces the `history state`, and
   uses URLs as a fall back."
  (if (exists? js/history.replaceState)
    (do
      (js/history.replaceState nil "" url)
      (render-route))
    (set! js/window.location url)))


(defn set-mount-point! [id]
  "Sets the page `mount point` for routes."
  (swap! *config assoc :react-mount id))


(defn refresh []
  "Refreshes all `routes`."
  (set! js/window.onpopstate #(render-route))
  (render-route))

