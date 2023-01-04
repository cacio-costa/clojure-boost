(ns clojure-boost.core.datas
  (:require [java-time.api :as jt]))


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