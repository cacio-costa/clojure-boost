(ns clojure-boost.core.compra
  (:require [java-time.api :as jt]))

(defn nova-compra
  ([[data valor estabelecimento categoria cartao]]
   (nova-compra data valor estabelecimento categoria cartao))

  ([data valor estabelecimento categoria cartao]
   {:data            data
    :valor           valor
    :estabelecimento estabelecimento
    :categoria       categoria
    :cartao          cartao}))


(defn total-gasto [compras]
  (->> compras
       (map :valor)
       (reduce +)))


(defn mes-da-data [data]
  (-> data
      jt/month
      .getValue))


(defn filtra-compras-por-mes [mes compras]
  (filterv #(= mes (mes-da-data (:data %))) compras))


(def total-gasto-no-mes (comp total-gasto filtra-compras-por-mes))


(defn agrupa-gastos [compras]
  (->> (group-by :categoria compras)
       (reduce (fn [mapa [categoria compras]]
                 (assoc mapa categoria (total-gasto compras)))
               {})))


(defn filtra-compras-por-valor [minimo maximo compras]
  (->> compras
       (filterv (fn [compra]
                  (let [valor (:valor compra)]
                    (and (>= valor minimo)
                         (<= valor maximo)))))))