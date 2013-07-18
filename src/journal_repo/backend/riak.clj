(ns journal-repo.backend.riak
  (:require [journal-repo.util :as util]
            [journal-repo.backend :as backend]
            [clojurewerkz.welle.core :as wc]
            [clojurewerkz.welle.buckets :as wb]
            [clojurewerkz.welle.kv :as kv])
  (:import com.basho.riak.client.http.util.Constants))


(def basic-object
  {:datastreams {} :refs {}})

(def host "http://127.0.0.1:10018/riak")
(def bucket "reklamefilm-all")

(wc/connect! host)
(wb/create bucket)

(defn get-object-list
  ""
  []
  (wb/keys-in bucket))

(defn print-object-list
  ""
  []
  (println (clojure.string/join "\n" (sort (map str (get-object-list))))))

(defn get-object
  ""
  ([pid] (get-object pid nil))
  ([pid path] (get-in (:value (first (kv/fetch bucket (name pid)))) (cons :datastreams path))))

(defn create-object
  ""
  ([meta]
    (let [pid (backend/new-uuid)]
      (create-object meta pid)))
  ([meta pid]
    (kv/store bucket (name pid) basic-object :content-type Constants/CTYPE_JSON_UTF8)
    pid))

;(defn update-object
;  ""
;  [meta pid path f]
;  (swap! backend update-in (cons pid path) f))
;
;
(defn add-datastream
  ""
  ([meta pid datastream]
    (add-datastream meta pid datastream {}))
  ([meta pid datastream content]
    (let [object (get-object pid)]
      (if object
        (let [updated-object (assoc-in object [:datastreams datastream] content)]
          (kv/store bucket (name pid) updated-object :content-type Constants/CTYPE_JSON_UTF8))
        (throw (Exception. "Cannot add datastream to non-existing object."))))
    pid))
;
;
;(defn delete-datastream
;  ""
;  [meta pid datastream]
;  (swap! backend util/dissoc-in [pid :datastreams] datastream)
;  [pid datastream])
;
;
;
;(defn delete-object
;  ""
;  [meta pid]
;  (swap! backend dissoc pid)
;  nil)

(defn get-checkpoint
  ""
  []
  (:checkpoint (:value (first (kv/fetch bucket "checkpoint")))))

(defn set-checkpoint
  ""
  [checkpoint]
  (kv/store bucket "checkpoint" {:checkpoint checkpoint} :content-type Constants/CTYPE_JSON_UTF8))

(defn runner
  ""
  [revision code]
  (load-string code)
  (set-checkpoint revision))
