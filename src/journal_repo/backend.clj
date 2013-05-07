(ns journal-repo.backend)

(defn new-uuid
  "Generate a random uuid"
  []
  (keyword
    (str
      (java.util.UUID/randomUUID))))

(defprotocol Backend
  "Protocol describing the required functions of a backend"
  (create-object [meta pid] "create an object")
  (get-object [pid] "get an entire object")
  (delete-object [meta pid] "delete an object")
  (add-datastream [meta pid datastream content] "add datastream to object")
  (get-datastream [pid datastream] "get datastream from object")
  (delete-datastream [meta pid datastream]))