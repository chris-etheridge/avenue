(ns avenue.core
  (:require [rum.core :as rum]
            [avenue.util :as util]))


(defonce *routes (atom {}))


(defonce *config (atom {}))


(defonce *current-route (atom nil))


(defn location []
  js/document.location)


(defn path
  ([] (path (location)))
  ([loc] (.-pathname loc)))


(defn add-route [key & {:as args}]
  (swap! *routes assoc key (assoc args :key key)))


(defn current-route []
  (or (util/some-pred (fn [[k route]]
                        (re-matches (:match route) (path))) @*routes)
      (throw (ex-info (str "No route for " (.-href (location)))
                      {:url (.-href (location))}))))


(defn matches-current-route? [url]
  (let [[key route] (current-route)]
    (re-matches (:match route) url)))


(defn route-for-location [loc]
  (util/some-pred (fn [[k route]]
                    (re-matches (:match route) loc)) @*routes))


(defn render-route
  ([] (render-route (path)))
  ([loc]
   (let [[k route] (route-for-location loc)
         mount (:react-mount @*config)]
     (if mount
       (do
         (reset! *current-route route)
         (rum/mount ((:ctor route) :args route) (util/by-id mount)))
       (throw (ex-info "No :react-mount defined! Did you set one up?"
                       {:route route :key k}))))))


(defn go! [url]
  (if (exists? js/history.pushState)
    (do
      (js/history.pushState nil "" url)
      (render-route))
    (set! js/window.location url)))


(defn reload! [url]
  (if (exists? js/history.replaceState)
    (do
      (js/history.replaceState nil "" url)
      (render-route))
    (set! js/window.location url)))


(defn refresh []
  (set! js/window.onpopstate #(render-route))
  (render-route))

