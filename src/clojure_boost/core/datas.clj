(ns clojure-boost.core.datas
  (:require [java-time.api :as jt]))


;(defmulti mes-da-data #())
;(defmulti ano-da-data #())
;(defmulti dia-da-data #())

;(defmulti mes-da-data (fn [data]
;                        (class data)))
;
;(defmethod mes-da-data java.lang.String
;  [data]
;  (-> data
;      (clojure.string/split #"-")
;      second
;      Integer/valueOf))
;
;
;(defmethod mes-da-data java.time.LocalDate
;  [data]
;  (-> data
;      jt/month
;      .getValue))

(defprotocol PegaMes
  (mes-da-data [data]))


(extend-type java.lang.String PegaMes
  (mes-da-data
    [data]
    (-> data
        (clojure.string/split #"-")
        second
        Integer/valueOf)))


(extend-type java.time.LocalDate PegaMes
  (mes-da-data [data]
    (-> data
        jt/month
        .getValue)))


(defn maior-que-hoje? [data]
  (and (some? data)
       (jt/after? data (jt/local-date))))