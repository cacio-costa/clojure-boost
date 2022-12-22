(ns clojure-boost.shell.persistencia.atomo
  (:require [clojure-boost.core.compra :as compra]))


(def repositorio-de-compras (atom []))


(defn insere-compra! [compra]
  (if (compra/valida-compra compra)
    (swap! repositorio-de-compras compra/insere-compra compra)
    (throw (ex-info "Compra inválida!" {:compra-invalida compra}))))


(defn lista-compras! []
  @repositorio-de-compras)


(defn exclui-compra! [id]
  (swap! repositorio-de-compras compra/exclui-compra id))
