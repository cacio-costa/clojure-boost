(ns clojure-boost.core.compra-test
  (:require [clojure.test :refer :all]
            [clojure-boost.core.compra :refer :all]
            [clojure-boost.shell.csv :as csv]))

; Reaproveita dados no csv como massa de dados para teste
(defonce compras (csv/lista-compras!))

(deftest total-gasto-test

  (testing "deve somar o valor de todas compras"
    (is (= (total-gasto compras) 3753.44M)))

  (testing "deve calcular total de compras com vetor de uma única compra"
    (is (= (total-gasto [{:valor 100M}]) 100M)))

  (testing "total de compras deve ser zero quando vetor de compras estiver vazio"
    (is (zero? (total-gasto []))))

  (testing "total de compras deve ser zero quando valor for NIL"
    (is (zero? (total-gasto nil)))))


(deftest agrupa-gastos-test

  (testing "deve totalizar gastos por categoria"
    (is (= (agrupa-gastos compras) {"Alimentação" 285.70M
                                    "Saúde"       640.00M
                                    "Lazer"       555.87M
                                    "Automóvel"   1266.87M
                                    "Educação"    255.00M
                                    "Casa"        750.00M})))

  (testing "deve retornar map vazio quando vetor de compras estiver vazio"
    (is (= (agrupa-gastos []) {}))))