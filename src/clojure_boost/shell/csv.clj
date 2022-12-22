(ns clojure-boost.shell.csv
  (:require [clojure-boost.core.compra :as compra]
            [java-time.api :as jt]))


(defn cartao-str->long [string-do-cartao]
  (-> string-do-cartao
      (clojure.string/replace " " "")
      Long/parseLong))


(def conversoes-de-compra [jt/local-date
                           bigdec
                           identity
                           identity
                           cartao-str->long])


(defn converte-valores-do-registro [registro conversoes]
  (mapv #(%1 %2) conversoes registro))


(defn- atribui-ids [compras]
  (reduce (fn [vetor-final compra]
            (->> (compra/novo-id vetor-final)
                 (assoc compra :id)
                 (conj vetor-final)))
          []
          compras))


(defn lista-compras []
  (->> "compras.csv"
       slurp
       clojure.string/split-lines
       rest
       (map #(clojure.string/split % #","))
       (mapv #(converte-valores-do-registro % conversoes-de-compra))
       (mapv #(apply (partial compra/->Compra nil) %))
       atribui-ids))

