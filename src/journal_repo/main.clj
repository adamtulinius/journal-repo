(ns journal-repo.main
  (:gen-class)
  (:require [journal-repo.loader :as loader]
            [journal-repo.backend.atom :as atom]
            [journal-repo.backend.riak :as riak]
            [journal-repo.backend.count :as count]))

(defn -main [& args]
  (case (first args)
    "atom-ingest-all" (loader/ingest-repeatedly atom/runner 'journal-repo.backend.atom (atom/get-checkpoint))
    "riak-ingest-all" (loader/ingest-repeatedly riak/runner 'journal-repo.backend.riak (riak/get-checkpoint))
    "count-all" (loader/ingest-repeatedly count/runner 'journal-repo.backend.count (count/get-checkpoint))
    "atom-ingest-10" (loader/ingest-10 atom/runner 'journal-repo.backend.atom (atom/get-checkpoint))
    "riak-ingest-10" (loader/ingest-10 riak/runner 'journal-repo.backend.riak (riak/get-checkpoint))
    "riak-objects" (riak/print-object-list)
    "atom-objects" (atom/print-object-list)
    (println "Tough luck, try again.")))