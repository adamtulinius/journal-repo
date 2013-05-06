(ns journal-repo.core-test
  (:require [clojure.test :refer :all]
            [journal-repo.core :refer :all]))

(def metadata {:meta "data"})

(defn create-operation
  ""
  [uuid]
  (let [args [3.141592 :giraffe]
        v (atom nil)
        f (fn [& more] (reset! v more))
        op {:fn f :args args}]
    {:operation op
     :validator #(do (println "validating!")
                   (is (= (cons uuid args) (deref v))))}))


;(deftest a-test
;  (testing "backend is empty"
;    (reset-backend)
;    (is (= (get-backend) {})))
;
;  (testing "run operation"
;    (let [uuid (new-uuid)
;          op (create-operation uuid)]
;      (run-operation (:operation op) uuid)
;      ((:validator op))))
;
;  (testing "run several operations"
;    (let [uuid (new-uuid)
;          ops (take 5 (repeatedly #(create-operation uuid)))]
;      (run-operations ops uuid)
;      (loop [operations ops]
;        (if operations
;          (do
;            ((:validator (first operations)))
;            (recur (rest operations)))))))
;
;  (testing "create, get and delete object, leaving backend empty"
;    (reset-backend)
;    (let [uuid (new-uuid)]
;      (create-object metadata uuid)
;      (is (= (get-backend)
;            {uuid basic-object}))
;      (is (= (get-object uuid)
;            basic-object))
;      (is (= (:datastreams (get-object uuid))
;            (:datastreams basic-object)))
;      (delete-object metadata uuid)
;      (is (= (get-backend) {}))))
;
;  (testing "low-level add datastream"
;    (reset-backend)
;    (let [uuid (new-uuid)
;          datastream :pbcore
;          content "pbcore content"]
;      (create-object metadata uuid)
;      (add-datastream-to metadata uuid datastream content)
;      (is (=
;            (get-backend)
;            {uuid
;             (assoc-in basic-object [:datastreams datastream] content)}))))
;
;  (testing "create and add datastream"
;    (reset-backend)
;    (let [uuid (new-uuid)
;          datastream :pbcore
;          content "pbcore content"]
;      (println (get-backend))
;      (create-object metadata uuid
;        (add-datastream metadata datastream content))
;      (println (get-backend))
;      (is (=
;            (get-backend)
;            {uuid
;              (assoc-in basic-object [:datastreams datastream] content)}))
;      ))
;  )
