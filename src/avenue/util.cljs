(ns avenue.util)


(defn some-pred
  "Takes a predicate and coll of items, and returns items
   that match that predicate."
  [pred items]
  (some #(when (pred %) %) items))


(defn by-id
  "Gets an element by ID"
  [id]
  (js/document.getElementById id))
