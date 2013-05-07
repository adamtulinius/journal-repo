(ns journal-repo.main
  (:gen-class)
  (:require [journal-repo.loader :as loader]
            [journal-repo.backend.atom :as atom]
            [journal-repo.backend.riak :as riak])
  ;(:use [clojure.tools.cli :only [cli]])
  ;(:require [journal-repo.util :as util]
  ;          [journal-repo.backend :as backend]))
  )

(defn -main [& args]
  (case (first args)
    "atom-loader" (loader/ingest-repeatedly atom/runner (atom/get-checkpoint))
    "riak-loader" (loader/ingest-repeatedly riak/runner (riak/get-checkpoint))
    (println "Tough luck, try again.")))