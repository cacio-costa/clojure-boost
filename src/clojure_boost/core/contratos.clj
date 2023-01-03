(ns clojure-boost.core.contratos
  (:require [schema.core :as s]))


(s/def PosInt (s/constrained Long pos-int?))
(s/def Moeda (s/pred #(and (decimal? %) (pos? %))))
(s/def EstabelecimentoValido (s/constrained s/Str #(-> % count (>= 2))))
(s/def CategoriaValida (s/enum "Alimentação" "Automóvel" "Casa" "Educação" "Lazer" "Saúde"))
(s/def CartaoValido (s/constrained PosInt #(< % 10000000000000000)))
(s/def DataRetroativa (s/pred #(re-matches #"[0-9]{4}-[0-9]{2}-[0-9]{2}" %)))


(s/def Compra {(s/optional-key :id) PosInt
               :valor               Moeda
               :estabelecimento     EstabelecimentoValido
               :categoria           CategoriaValida
               :cartao              CartaoValido
               :data                DataRetroativa})