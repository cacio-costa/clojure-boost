(ns clojure-boost.core.contratos-test
  (:require [clojure.test :refer :all]
            [schema.core :as s]
            [java-time.api :as jt]
            [clojure-boost.core.contratos :as contratos])
  (:import (clojure.lang ExceptionInfo)))

(deftest compra-schema-test

  (testing "Schema deve aceitar compra com todos os valores válidos"
    (is (map? (s/validate contratos/Compra
                          {:id              1
                           :valor           100M
                           :data            (jt/local-date)
                           :estabelecimento "Restaurante"
                           :categoria       "Alimentação"
                           :cartao          1234123412341234}))))


  (testing "deve rejeitar compra com data NIL"
    (is (thrown? ExceptionInfo (s/validate contratos/Compra
                                           {:id              1
                                            :valor           100M
                                            :data            nil
                                            :estabelecimento "Restaurante"
                                            :categoria       "Alimentação"
                                            :cartao          1234123412341234}))))


  (testing "deve rejeitar compra com valor negativo"
    (is (thrown? ExceptionInfo (s/validate contratos/Compra
                                           {:id              1
                                            :valor           -100M
                                            :data            (jt/local-date)
                                            :estabelecimento "Restaurante"
                                            :categoria       "Alimentação"
                                            :cartao          1234123412341234}))))


  (testing "deve rejeitar compra com estabelecimento em branco"
    (is (thrown? ExceptionInfo (s/validate contratos/Compra
                                           {:id              1
                                            :valor           100M
                                            :data            (jt/local-date)
                                            :estabelecimento ""
                                            :categoria       "Alimentação"
                                            :cartao          1234123412341234}))))


  (testing "deve rejeitar compra com categoria fora das permitidas"
    (is (thrown? ExceptionInfo (s/validate contratos/Compra
                                           {:id              1
                                            :valor           100M
                                            :data            (jt/local-date)
                                            :estabelecimento "Restaurante"
                                            :categoria       "inexistente"
                                            :cartao          1234123412341234}))))


  (testing "deve rejeitar compra cartão inválido"
    (is (thrown? ExceptionInfo (s/validate contratos/Compra
                                           {:id              1
                                            :valor           100M
                                            :data            (jt/local-date)
                                            :estabelecimento "Restaurante"
                                            :categoria       "Alimentação"
                                            :cartao          0}))))
  )
