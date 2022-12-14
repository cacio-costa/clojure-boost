(ns clojure-boost.semana-1
  (:require [clojure-boost.shell.csv :as csv]
            [clojure-boost.core.compra :as compra]
            [clojure.pprint :as pp]))


(println "### TESTA LISTAGEM DE COMPRAS ###")
(pp/pprint (csv/lista-compras))
(println)

(println "### TESTA TOTAL GASTO ###")
(pp/pprint (compra/total-gasto (csv/lista-compras)))
(println)

(println "### TESTA BUSCA COMPRAS NUM MÊS ###")
(pp/pprint (compra/filtra-compras-por-mes 4 (csv/lista-compras)))
(println)

(println "### TESTA TOTAL GASTO NUM MÊS ###")
(pp/pprint (compra/total-gasto-no-mes 4 (csv/lista-compras)))
(println)

(println "### TESTA AGRUPA GASTO POR CATEGORIA ###")
(pp/pprint (compra/agrupa-gastos (csv/lista-compras)))
(println)

(println "### TESTA BUSCA DE COMPRAS POR VALOR ###")
(pp/pprint (compra/filtra-compras-por-valor 800 1000 (csv/lista-compras)))
(println)