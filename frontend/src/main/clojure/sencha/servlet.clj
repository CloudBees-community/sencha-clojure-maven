(ns sencha.servlet
  (:use ring.util.servlet)
  (:require [sencha.core :as core])
  (:gen-class :extends javax.servlet.http.HttpServlet))

(defservice core/app)