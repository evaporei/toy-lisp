(ns toy-lisp.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn build-error [message] (ex-info message {}))

(defn tokenize
  [input]
  (filter not-empty
   (->
     input
     (str/replace #"\(" " ( ")
     (str/replace #"\)" " ) ")
     str/triml
     (str/split #" "))))

(defn parse-atom
  [atom]
  (try
    {:type "number"
      :value (Double/parseDouble atom)}
    (catch Exception e {:type "symbol"
                         :value atom})))

(declare read-seq)
(defn parse
  [tokens]
  (if (empty? tokens)
    (throw (build-error "Could not get token, string is empty"))
    (let [[token & tokens] tokens] (cond
                                     (= token "(") (read-seq tokens [])
                                     (= token ")") (throw (build-error "Unexpected `)` token"))
                                     :else [(parse-atom token), tokens]))))

(defn read-seq
  [tokens expr-list]
  (if (empty? tokens)
    (throw (build-error "Could not find closing `)`"))
    (let [[next-token & tokens] tokens] (cond
                                          (= next-token ")") [expr-list, tokens]
                                          :else (let [[expr, tokens] (parse (concat [next-token] tokens))]
                                                  (read-seq tokens (concat expr-list [expr])))))))

(defn is-expression-of-type
  [expr type']
  (= (:type expr) type'))

(defn is-symbol [expr] (is-expression-of-type expr "symbol"))
(defn is-number [expr] (is-expression-of-type expr "number"))
(defn is-list [expr] (seq? expr))
(defn is-func [expr] (is-expression-of-type expr "func"))

(defn sum-aux
  [expr1 expr2]
  (let [n1 (:value expr1)
        n2 (:value expr2)]
    (+ n1 n2)))

(defn sum
  [expr-list]
  (if (empty? expr-list)
    (throw (build-error "Could not sum, list expression is empty"))
    (if (not (some is-number expr-list))
      (throw (build-error "Could not sum, not all expressions in list are Numbers"))
      {:type "number" :value (reduce sum-aux expr-list)})))

(def default-env {"+" {:type "func" :value sum}})

(defn eval'
  [env expr]
  (cond
    (is-symbol expr) (get-in env [(:value expr)])
    (is-number expr) expr
    (is-list expr) (if (empty? expr)
                     (throw (build-error "Expected a non empty list"))
                     (let [func (eval' env (first expr))]
                       (cond
                         (is-func func) (let [eval-args (map (partial eval' env) (rest expr))]
                                          ((:value func) eval-args))
                         :else (throw (build-error "First form must be a function")))))
    :else (throw (build-error "Unexistent expression type"))))

(defn repl
  []
  (print "toy-lisp > ")
  (flush)
  (try
    (->>
      read-line
      tokenize
      parse
      first
      eval' default-env
      :value
      println)
    (catch Exception e
      (println "Error:" (:cause (Throwable->map e)))))
  (repl))

(defn -main
  [& args]
  (repl))
