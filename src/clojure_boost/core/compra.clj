(ns clojure-boost.core.compra
  (:require [schema.core :as s]
            [clojure-boost.core.datas :as dt]
            [clojure-boost.core.contratos :as contratos]))

(s/defn nova-compra :- contratos/Compra
  [id data valor estabelecimento categoria cartao]
  {:id              id
   :data            data
   :valor           valor
   :estabelecimento estabelecimento
   :categoria       categoria
   :cartao          cartao})


(defn novo-id [entidades]
  (if (empty? entidades)
    1
    (->> entidades
         (map :id)
         (apply max)
         inc)))


(defn total-gasto [compras]
  (->> compras
       (map :valor)
       (reduce +)))


(defn filtra-compras-por-mes [mes compras]
  (filterv #(= mes (dt/mes-da-data (:data %))) compras))


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


(defn insere-compra [todas-as-compras nova-compra]
  (let [id-da-compra (novo-id todas-as-compras)
        compra-com-id (assoc nova-compra :id id-da-compra)]
    (conj todas-as-compras compra-com-id)))


(defn exclui-compra [todas-as-compras id]
  (->> todas-as-compras
       (remove #(= id (:id %)))
       vec))
