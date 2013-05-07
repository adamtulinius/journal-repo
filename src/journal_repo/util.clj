(ns journal-repo.util)

(defn dissoc-in
  ""
  [data path target]
  (update-in data path dissoc target))

