(ns journal-repo.loader
  (:require [journal-repo.queue :as queue]))


(def ingest (atom true))

(defn ingest-status "" []
  (deref ingest))

(defn stop-ingest "" []
  (reset! ingest false))

(defn ingest-repeatedly
  "Ingest data until the end of time."
  ([f] (ingest-repeatedly nil))
  ([f initial-checkpoint]
    (loop [checkpoint initial-checkpoint]
      (println "Ingesting from checkpoint" checkpoint)
      (let [next-checkpoint (queue/load-all-from f checkpoint)]
        (if (ingest-status)
          (do
            (Thread/sleep 1000)
            (recur next-checkpoint)))))))
