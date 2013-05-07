(ns journal-repo.backend.riak
  (:require [journal-repo.util :as util]
            [journal-repo.backend :as backend]))


(def basic-object
  {:datastreams {} :refs {}})


;(defn get-object
;  ""
;  ([pid] (get-object pid nil))
;  ([pid path] (get-in (get-backend) (cons pid (cons :datastreams path)))))
;
;(defn update-object
;  ""
;  [meta pid path f]
;  (swap! backend update-in (cons pid path) f))
;
;
;(defn add-datastream
;  ""
;  ([meta pid datastream]
;    (add-datastream meta pid datastream {}))
;  ([meta pid datastream content]
;    (if (get-object pid)
;      (update-object
;        meta pid [:datastreams datastream]
;        (fn [_] content))
;      (throw (Exception. "Cannot add datastream to non-existing object.")))
;    pid))
;
;
;(defn delete-datastream
;  ""
;  [meta pid datastream]
;  (swap! backend util/dissoc-in [pid :datastreams] datastream)
;  [pid datastream])
;
;(defn create-object
;  ""
;  ([meta]
;    (let [pid (backend/new-uuid)]
;      (create-object meta pid)))
;  ([meta pid]
;    (swap! backend assoc pid basic-object)
;    pid))
;
;
;(defn delete-object
;  ""
;  [meta pid]
;  (swap! backend dissoc pid)
;  nil)


(defn runner
  ""
  [code]
  (println code))

(defn get-checkpoint
  ""
  []
  0)