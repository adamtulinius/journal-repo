(ns journal-repo.core)

(def empty-backend {})

(def backend
  (atom empty-backend))

(defn reset-backend
  ""
  []
  (reset! backend empty-backend))

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


(def basic-object
  {:datastreams {} :refs {}})


(defmacro with
  [pids & f]
  `(doseq [~'*pid* ~pids]
     ~f))

(defn get-object
  ""
  ([pid] (get-object pid nil))
  ([pid path] (get-in (get-backend) (cons pid path))))

(defn update-object
  ""
  [meta pid path f]
  (swap! backend update-in (cons pid path) f))


(defn add-datastream-to
  ""
  ([meta pid datastream]
    (add-datastream-to meta pid datastream {}))
  ([meta pid datastream content]
    (if (get-object pid)
      (update-object
        meta pid [:datastreams datastream]
        (fn [_] content))
      (throw (Exception. "Cannot add datastream to non-existing object.")))))

(defn add-datastream
  ""
  [meta datastream content]
  {:fn add-datastream-to
   :args [meta datastream content]})

(defn delete-datastream-from
  ""
  [meta pid datastream]
  (swap! backend dissoc-in [pid :datastreams] datastream))

(defn delete-datastream
  ""
  [meta datastream]
  {:operation delete-datastream-from
   :meta meta
   :datastream datastream})


(defn run-operation
  ""
  [op pid]
  (apply (:fn op) (cons pid (:args op)))
  (get-object pid))

(defn run-operations
  ""
  [ops pid]
  (loop [operations ops]
    (if operations
      (do
        (run-operation (first ops) pid)
        (recur (rest ops))))))

(defn select-object
  ""
  [pid & ops]
  (run-operations ops pid))

(defn create-object
  ""
  [meta pid & ops]
  ;(if (get-object ))
  (swap! backend assoc pid basic-object)
  (run-operations ops pid)
  (get-object pid))


(defn delete-object
  ""
  [meta pid]
  (swap! backend dissoc pid))


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