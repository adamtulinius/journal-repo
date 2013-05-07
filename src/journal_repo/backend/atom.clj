(ns journal-repo.backend.atom
  (:require [journal-repo.util :as util]
            [journal-repo.backend :as backend]
            [clj-http.client :as http]))


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


(def basic-object
  {:datastreams {} :refs {}})


(defn get-object
  ""
  ([pid] (get-object pid nil))
  ([pid path] (get-in (get-backend) (cons pid (cons :datastreams path)))))

(defn update-object
  ""
  [meta pid path f]
  (swap! backend update-in (cons pid path) f))


(defn add-datastream
  ""
  ([meta pid datastream]
    (add-datastream meta pid datastream {}))
  ([meta pid datastream content]
    (if (get-object pid)
      (update-object
        meta pid [:datastreams datastream]
        (fn [_] content))
      (throw (Exception. "Cannot add datastream to non-existing object.")))
    pid))


(defn delete-datastream
  ""
  [meta pid datastream]
  (swap! backend util/dissoc-in [pid :datastreams] datastream)
  [pid datastream])

(defn create-object
  ""
  ([meta]
    (let [pid (backend/new-uuid)]
      (create-object meta pid)))
  ([meta pid]
    (swap! backend assoc pid basic-object)
    pid))


(defn delete-object
  ""
  [meta pid]
  (swap! backend dissoc pid)
  nil)


(defn runner
  ""
  [code]
  (load-string code))

(defn get-checkpoint
  ""
  []
  0) ; should be nil TODO FIX THE BUG

;(defn new-store
;  "Instantiates a new atom-backed store."
;  []
;  (let [backend (atom {})]
;    {:store backend
;     :get-object get-object
;     :create-object create-object
;     :delete-object delete-object
;     :add-datastream add-datastream
;     :delete-datastream delete-datastream}))