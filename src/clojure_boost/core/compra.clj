(ns clojure-boost.core.compra
  (:require [clojure-boost.core.datas :as dt]))

(defrecord Compra [id
                   data
                   valor
                   estabelecimento
                   categoria
                   cartao])


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


(defn- data-valida? [{:keys [data]}]
  (and (some? data)
       (not (dt/maior-que-hoje? data))))


(defn- valor-valido? [{:keys [valor]}]
  (and (some? valor)
       (pos? valor)
       (decimal? valor)))


(defn- estabelecimento-valido? [{:keys [estabelecimento]}]
  (and (string? estabelecimento)
       (-> estabelecimento
           count
           (>= 2))))


(defn- categoria-valida? [{:keys [categoria]}]
  (contains? #{"Alimentação" "Automóvel" "Casa" "Educação" "Lazer" "Saúde"}
             categoria))


(defn valida-compra [compra]
  (and (data-valida? compra)
       (valor-valido? compra)
       (estabelecimento-valido? compra)
       (categoria-valida? compra)))