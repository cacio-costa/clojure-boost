(ns clojure-boost.core.contratos
  (:require [schema.core :as s]
            [clojure-boost.core.datas :as dt]))


(defn tamanho-minimo [comprimento]
  (s/constrained s/Str #(-> % count (>= comprimento))))


(s/def PosInt (s/constrained Long pos-int?))
(s/def Moeda (s/constrained BigDecimal pos?))
(s/def EstabelecimentoValido (tamanho-minimo 2))
(s/def CategoriaValida (s/enum "Alimentação" "Automóvel" "Casa" "Educação" "Lazer" "Saúde"))
(s/def CartaoValido (s/constrained PosInt #(< % 10000000000000000)))
(s/def DataRetroativa (s/pred #(and (some? %)
                                    (not (dt/maior-que-hoje? %)))))


(s/def Compra {(s/optional-key :id) (s/maybe PosInt)
               :valor               Moeda
               :estabelecimento     EstabelecimentoValido
               :categoria           CategoriaValida
               :cartao              CartaoValido
               :data                DataRetroativa})