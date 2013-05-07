(ns journal-repo.queue
  (:require [clj-http.client :as http]))


(def journal "http://alhena:7950/JournalServer")

(defn url-for-entry
  ""
  [entry]
  (format "%s/%s" journal entry))

(defn next-id
  ""
  [id]
  (if id (inc id) 0))

(defn get-entry
  ""
  [id]
  (try
    (let [response (http/get (url-for-entry id))]
      {:id id :content (:body response)})
    (catch Exception e nil)))

(defn foo
  [id]
  (let [next-id (if id (inc id) 0)]
    {:id next-id
     :content (get-entry next-id)}))

(defn get-all-from
  ""
  ([] (get-all-from nil))
  ([checkpoint]
    (take-while #(:content %1)
      (drop 1 ; drop the fake entry created 3 lines below
        (iterate
          #(foo (:id %1))
          {:id checkpoint})))))

(defn load-next
  ""
  ([f] (load-next f nil))
  ([f checkpoint]
    (let [next (next-id checkpoint)
          entry (get-entry next)
          id (:id entry)
          content (:content entry)]
      (if (and id content)
        (do
          (f content)
          id)))))

(defn load-all-from
  ""
  ([f] (load-all-from f nil))
  ([f checkpoint]
    (last
      (take-while (partial not= nil)
        (iterate (partial load-next f) checkpoint)))))

(defn put-str
  ""
  [data]
  (let [response (http/put journal {:body data})]
    (url-for-entry (:body response))))

(defn put
  ""
  [data]
  (put-str (str data)))
