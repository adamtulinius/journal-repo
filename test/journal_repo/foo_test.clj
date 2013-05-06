(ns journal-repo.foo-test
  (:require [clojure.test :refer :all]
            [journal-repo.foo :refer :all]))


(deftest a-test
  (testing "foo"
    (println
      (with [:foo :bar]
        (fn [pid]
          (println "first fn: " pid))
        #(println "second fn: " (str %1 %1)))))
  )
