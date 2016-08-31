(ns avenue.util)


(defn some-pred [pred items]
  "Takes a predicate and coll of items, and returns items
   that match that predicate."
  (some #(when (pred %) %) items))


(defn by-id [id]
  "Gets an element by ID"
  (js/document.getElementById id))
