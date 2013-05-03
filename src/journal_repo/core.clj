(ns journal-repo.core)

(def backend
  (atom {}))

(defn get-backend
  ""
  []
  (deref backend))


(defn new-uuid
  "Generate a random uuid"
  []
  (keyword
    (str
      (java.util.UUID/randomUUID))))

(defn dissoc-in
  ""
  [data path target]
  (update-in data path dissoc target))



(defn get-object
  ""
  ([pid] (get-object pid nil))
  ([pid path] (get-in (get-backend) (cons pid path))))

(defn create-object
  ""
  ([meta pid]
  (swap! backend assoc pid {})))

(defn delete-object
  ""
  [meta pid]
  (swap! backend dissoc pid))

(defn update-object
  ""
  [meta pid path f]
  (swap! backend update-in (cons pid path) f))

(defn add-datastream
  ""
  ([meta pid datastream]
    (add-datastream pid datastream {}))
  ([meta pid datastream content]
    (update-object
      meta pid [:datastreams datastream]
      (fn [_] content))))

(defn delete-datastream
  ""
  [meta pid datastream]
  (swap! backend dissoc-in [pid :datastreams] datastream))


(defn random-journal-entry
  ""
  []
  (let [uuid (new-uuid)
        user (rand-nth ["aft" "abr" "kfc" "ktc" "jrg"])
        time "today"
        metadata {:user user, :time time}
        path [:datastreams :pbcore]
        f '(fn [_] "object bleh bleh")]
    `(~'update-object ~metadata ~uuid ~path ~f)))

(defn random-journal
  ""
  ([] (random-journal 10))
  ([entries]
    (take entries
      (repeatedly random-journal-entry))))

(defn print-journal
  ""
  [journal]
  (println
    (clojure.string/join "\n" (map str journal))))