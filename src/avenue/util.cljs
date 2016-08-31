(ns avenue.util)


(defn some-pred [pred items]
  "Takes a predicate and items, and returns items
   that match that predicate."
  (some #(when (pred %) %) items))


(defn by-id [id]
  (js/document.getElementById id))
