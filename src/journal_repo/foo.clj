(ns journal-repo.foo)


(defn add-datastream [m name content]
  {:add {:datastream {:name name :content content}}})

(defn delete-datastream [m name]
  {:rm {:datastream {:name name}}})

(defn with [pids & fns]
  (doseq [pid pids]
    (doseq [f fns] (f pid))))

(defmacro with1 [pids & f]
  `(doseq [~'pid ~pids]
     (~f)))

; (with2 [:pid1 :pid2] #(println pid))
(defmacro with2 [pids & f]
  `(doseq [~'pid ~pids]
     (~@f)))

(defmacro with3 [pids & fns]
  `(doseq [~'pid ~pids]
     (doseq [~'f ~@fns]
       (~'f "foo"))))

; (with4 [:pid1 :pid2] [#(println %1 pid) #(println "2nd: " %1)])
(defmacro with4 [pids & fns]
  `(doseq [~'pid ~pids]
     (doseq [~'f ~@fns]
       (~'f ~'pid))))

(defmacro with5 [pids & fns]
  `(doseq [~'pid ~pids]
     (doseq [~'f ~fns]
       (~'f ~'pid))))

(defmacro with6 [pids & fns]
  `(doseq [~'pid ~pids]
     (doseq [~'f ~fns]
       (`f ~'pid))))

(defmacro with7 [pids & fns]
  `(doseq [~'pid ~pids
           ~'f ~fns]
     (~@f)))

(defmacro with112 [pids & fns]
  `(doseq [~'pid ~pids]
     (doseq [f fns] (f pid))))

